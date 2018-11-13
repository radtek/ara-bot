package com.zhuiyi.task;

import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.HotAreaFqOverview;
import com.zhuiyi.model.HotAreaOverview;
import com.zhuiyi.model.HotFaqOverview;
import com.zhuiyi.service.HotAreaFqOverviewService;
import com.zhuiyi.service.HotAreaOverviewService;
import com.zhuiyi.service.HotFaqOverviewService;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/30 10:44
 * description:
 * own: zhuiyi
 */

@SuppressWarnings({"RedundantThrows", "unchecked", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
@Slf4j
public class HotOverviewTask extends QuartzJobBean {

    @Autowired
    private HotFaqOverviewService hotFaqOverviewService;
    @Autowired
    private HotAreaOverviewService hotAreaOverviewService;
    @Autowired
    private HotAreaFqOverviewService hotAreaFqOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，1个小时锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":HotOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS * 6);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start HotOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String yesDataSign = CustomTimeUtil.getFormatYesterdayDate();
                String monthAndYesterday = CustomTimeUtil.getYesMonthAndDay();
//                String monthAndYesterday = CustomTimeUtil.getNowMonthAndDay();
                String befDateSign = CustomTimeUtil.getFormatAnyDate(2L);
                //构造对应的redisKey
                String yesFaqKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "faq");
                String yesFaqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "faq:rank");
                String yesProvinceRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "province:rank");
                String yesCityRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "city:rank");
                String yesProvinceFaqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "province:faq:rank");
                String yesCityFaqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "city:faq:rank");

                //查询前天的全国统计数据
                HotAreaFqOverview befHotAreaFqOverviewChina = hotAreaFqOverviewService.findByAppidAndDateSignAndAreaNameAndFaqId(appId, befDateSign,
                        GlobaSystemConstant.COUNTRY_NAME.CHINA.getElementName(), 0);

                //查询当天和昨天的提问总量数据
                int currentDialogNum = ((Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L)).intValue();

                //声明热点地域FAQ对象
                HotAreaFqOverview hotAreaFqOverview = new HotAreaFqOverview();
                hotAreaFqOverview.setAppid(appId);
                hotAreaFqOverview.setDateSign(yesDataSign);
                //单独计算全国的数据
                hotAreaFqOverview.setAreaType(GlobaSystemConstant.AREA_TYPE.CHINA.getElementName());
                hotAreaFqOverview.setAreaName(GlobaSystemConstant.COUNTRY_NAME.CHINA.getElementName());
                hotAreaFqOverview.setFaqId(0);
                hotAreaFqOverview.setFaq(GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);
                hotAreaFqOverview.setVisitNum(currentDialogNum);
                if (befHotAreaFqOverviewChina != null) {
                    hotAreaFqOverview.setVisitTrend(String.valueOf((currentDialogNum - befHotAreaFqOverviewChina.getVisitNum()) / (befHotAreaFqOverviewChina.getVisitNum() > 0 ? befHotAreaFqOverviewChina.getVisitNum() : 1L)));
                } else {
                    hotAreaFqOverview.setVisitTrend(String.valueOf(currentDialogNum));
                }
                try {
                    hotAreaFqOverviewService.save(hotAreaFqOverview, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                } catch (InternalServiceException e) {
                    e.printStackTrace();
                    log.error("[SYS][REDUCE][Result: task is not finish]");
                }

                //计算各省的数据
                redisService.findZSetReverseRangeWithScores(yesProvinceRankKey, 0, -1).forEach(x -> {
                    //声明热点地域FAQ对象
                    HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();
                    hotAreaFqOverviewTemp.setAppid(appId);
                    hotAreaFqOverviewTemp.setDateSign(yesDataSign);
                    hotAreaFqOverviewTemp.setAreaType(GlobaSystemConstant.AREA_TYPE.PROVINCE.getElementName());
                    hotAreaFqOverviewTemp.setAreaName((String) x.getValue());
                    hotAreaFqOverviewTemp.setFaqId(0);
                    hotAreaFqOverviewTemp.setFaq(GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);

                    //声明热点地域对象
                    HotAreaOverview hotAreaOverviewTemp = new HotAreaOverview();
                    hotAreaOverviewTemp.setAppid(appId);
                    hotAreaOverviewTemp.setDateSign(yesDataSign);
                    hotAreaOverviewTemp.setAreaType(GlobaSystemConstant.AREA_TYPE.PROVINCE.getElementName());
                    hotAreaOverviewTemp.setAreaName((String) x.getValue());

                    int visitNum = (int) Math.round(x.getScore());
                    hotAreaFqOverviewTemp.setVisitNum(visitNum);
                    hotAreaOverviewTemp.setVisitNum(visitNum);

                    HotAreaFqOverview befHotAreaFqOverview = hotAreaFqOverviewService.findByAppidAndDateSignAndAreaNameAndFaqId(appId, befDateSign, (String) x.getValue(), 0);
                    if (befHotAreaFqOverview != null) {
                        String visitTrend = String.valueOf((visitNum - befHotAreaFqOverview.getVisitNum()) / (befHotAreaFqOverview.getVisitNum() > 0 ? befHotAreaFqOverview.getVisitNum() : 1L));
                        hotAreaFqOverviewTemp.setVisitTrend(visitTrend);
                        hotAreaOverviewTemp.setVisitTrend(visitTrend);
                    } else {
                        hotAreaFqOverviewTemp.setVisitTrend(String.valueOf(visitNum));
                        hotAreaOverviewTemp.setVisitTrend(String.valueOf(visitNum));
                    }
                    try {
                        hotAreaFqOverviewService.save(hotAreaFqOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                        hotAreaOverviewService.save(hotAreaOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                        log.error("[SYS][REDUCE][Result: task is not finish]");
                    }
                });

                //计算全国的FAQ数据
                redisService.findZSetReverseRangeWithScores(yesFaqRankKey, 0, -1).forEach(x -> {
                    //声明热点地域FAQ对象
                    HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();
                    hotAreaFqOverviewTemp.setAppid(appId);
                    hotAreaFqOverviewTemp.setDateSign(yesDataSign);
                    hotAreaFqOverviewTemp.setAreaType(GlobaSystemConstant.AREA_TYPE.CHINA.getElementName());
                    hotAreaFqOverviewTemp.setAreaName(GlobaSystemConstant.COUNTRY_NAME.CHINA.getElementName());
                    int faqID = Integer.parseInt(((String) x.getValue()).split(":")[0]);
                    hotAreaFqOverviewTemp.setFaqId(faqID);
                    String question = (String) redisService.findHash(yesFaqKey, (String)x.getValue());
                    hotAreaFqOverviewTemp.setFaq(question);
                    int visitNum = (int) Math.round(x.getScore());
                    hotAreaFqOverviewTemp.setVisitNum(visitNum);
                    HotAreaFqOverview befHotAreaFqOverview = hotAreaFqOverviewService.findByAppidAndDateSignAndAreaNameAndFaqId(appId, befDateSign, GlobaSystemConstant.COUNTRY_NAME.CHINA.getElementName(), faqID);
                    if (befHotAreaFqOverview != null) {
                        hotAreaFqOverviewTemp.setVisitTrend(String.valueOf((visitNum - befHotAreaFqOverview.getVisitNum()) / (befHotAreaFqOverview.getVisitNum() > 0 ? befHotAreaFqOverview.getVisitNum() : 1L)));
                    } else {
                        hotAreaFqOverviewTemp.setVisitTrend(String.valueOf(visitNum));
                    }
                    try {
                        hotAreaFqOverviewService.save(hotAreaFqOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                        log.error("[SYS][REDUCE][Result: task is not finish]");
                    }
                });

                //计算各省的FAQ数据
                redisService.findZSetReverseRangeWithScores(yesProvinceRankKey, 0, -1).forEach(x -> redisService.findZSetReverseRangeWithScores(yesProvinceFaqRankKey + ":" + x.getValue(), 0, -1).forEach(y -> {
                    //声明热点地域FAQ对象
                    HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();
                    hotAreaFqOverviewTemp.setAppid(appId);
                    hotAreaFqOverviewTemp.setDateSign(yesDataSign);
                    hotAreaFqOverviewTemp.setAreaType(GlobaSystemConstant.AREA_TYPE.PROVINCE.getElementName());
                    hotAreaFqOverviewTemp.setAreaName((String) x.getValue());
                    hotAreaFqOverviewTemp.setFaqId(Integer.parseInt((String) y.getValue()));
                    String question = (String) redisService.findHash(yesFaqKey, y.getValue() + ":" + GlobaSystemConstant.FAQ_TYPE.FAQ.getElementName());
                    hotAreaFqOverviewTemp.setFaq(question);
                    int visitNum = (int) Math.round(y.getScore());
                    hotAreaFqOverviewTemp.setVisitNum(visitNum);
                    HotAreaFqOverview befHotAreaFqOverview = hotAreaFqOverviewService.findByAppidAndDateSignAndAreaNameAndFaqId(appId, befDateSign, (String) x.getValue(), Integer.parseInt((String) y.getValue()));
                    if (null == befHotAreaFqOverview) {
                        befHotAreaFqOverview = new HotAreaFqOverview();
                        befHotAreaFqOverview.setVisitNum(0);
                    }
                    String visitTrend = String.valueOf((hotAreaFqOverviewTemp.getVisitNum() - befHotAreaFqOverview.getVisitNum()) / (befHotAreaFqOverview.getVisitNum() > 0 ? befHotAreaFqOverview.getVisitNum() : 1));
                    hotAreaFqOverviewTemp.setVisitTrend(visitTrend);
                    try {
                        hotAreaFqOverviewService.save(hotAreaFqOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                        log.error("[SYS][REDUCE][Result: task is not finish]");
                    }
                }));

                //计算各城市的数据
                redisService.findZSetReverseRangeWithScores(yesCityRankKey, 0, -1).forEach(x -> {
                    //声明热点地域对象
                    HotAreaOverview hotAreaOverviewTemp = new HotAreaOverview();
                    hotAreaOverviewTemp.setAppid(appId);
                    hotAreaOverviewTemp.setDateSign(yesDataSign);
                    hotAreaOverviewTemp.setAreaType(GlobaSystemConstant.AREA_TYPE.CITY.getElementName());
                    hotAreaOverviewTemp.setAreaName((String) x.getValue());
                    int visitNum = (int) Math.round(x.getScore());
                    hotAreaOverviewTemp.setVisitNum(visitNum);
                    HotAreaOverview befHotAreaOverviewTemp = hotAreaOverviewService.findByAppidAndDateSignAndAreaName(appId, befDateSign, (String) x.getValue());
                    if (befHotAreaOverviewTemp != null) {
                        hotAreaOverviewTemp.setVisitTrend(String.valueOf((hotAreaOverviewTemp.getVisitNum() - befHotAreaOverviewTemp.getVisitNum()) / (befHotAreaOverviewTemp.getVisitNum() > 0 ? befHotAreaOverviewTemp.getVisitNum() : 1L)));
                    } else {
                        hotAreaOverviewTemp.setVisitTrend(String.valueOf(visitNum));
                    }
                    try {
                        hotAreaOverviewService.save(hotAreaOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                        log.error("[SYS][REDUCE][Result: task is not finish]");
                    }
                });

                //计算热门FAQ的数据
                redisService.findZSetReverseRangeWithScores(yesFaqRankKey, 0, -1).forEach(x -> {
                    //声明热点FAQ对象
                    HotFaqOverview hotFaqOverviewTemp = new HotFaqOverview();
                    hotFaqOverviewTemp.setAppid(appId);
                    hotFaqOverviewTemp.setDateSign(yesDataSign);
                    int faqID = Integer.parseInt(((String) x.getValue()).split(":")[0]);
                    hotFaqOverviewTemp.setFaqId(faqID);
                    String question = (String) redisService.findHash(yesFaqKey, (String) x.getValue());
                    HotFaqOverview befHotFaqOverview = hotFaqOverviewService.findByAppidAndDateSignAndFaqId(appId, befDateSign, faqID);
                    if (null == befHotFaqOverview) {
                        befHotFaqOverview = new HotFaqOverview();
                        befHotFaqOverview.setVisitNum(0);
                    }
                    hotFaqOverviewTemp.setFaq(question);
                    hotFaqOverviewTemp.setVisitNum((int) Math.round(x.getScore()));
                    hotFaqOverviewTemp.setVisitTrend(String.valueOf((hotFaqOverviewTemp.getVisitNum() - befHotFaqOverview.getVisitNum()) / (befHotFaqOverview.getVisitNum() > 0 ? befHotFaqOverview.getVisitNum() : 1)));
                    try {
                        hotFaqOverviewService.save(hotFaqOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                        log.error("[SYS][REDUCE][Result: task is not finish]");
                    }
                });

                log.info("[SYS][REDUCE][Appid: {}][End HotOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }

    }
}


