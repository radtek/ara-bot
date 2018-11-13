package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ZrgOverview;
import com.zhuiyi.model.dto.FaqZrgDTO;
import com.zhuiyi.model.dto.ZrgOverviewDTO;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.ZrgOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class ZrgOverviewTask extends QuartzJobBean {

    @Autowired
    private ZrgOverviewService zrgOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":ZrgOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start ZrgOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                String monthAndYesterday = CustomTimeUtil.getYesMonthAndDay();
                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                ZrgOverviewDTO zrgOverviewDTO = new ZrgOverviewDTO();
                //构造对应的redisKey
                String faqKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq");
                String sessionNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:session:num");
                String zrgNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:num");
                String zrgActiveNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:active:num");
                String zrgPassiveNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:passive:num");
                String zrgAtZeroTurnNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:at:zero:num");
                String zrgAtOneTurnNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:at:one:num");
                String zrgAtTwoTurnNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:zrg:at:two:num");
                String todZrgRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq:zrg:rank");
                String yesZrgRankKey = CustomObjectUtil.buildKeyString(appId, monthAndYesterday, null, "faq:zrg:rank");
                String faqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq:rank");

                //从redis中查询出当前的计算数据
                long sessionNum = ((Integer) Optional.ofNullable(redisService.find(sessionNumKey)).orElse(0)).longValue();
                long zrgNum = ((Integer) Optional.ofNullable(redisService.find(zrgNumKey)).orElse(0)).longValue();
                long zrgActiveNum = ((Integer) Optional.ofNullable(redisService.find(zrgActiveNumKey)).orElse(0)).longValue();
                long zrgPassiveNum = ((Integer) Optional.ofNullable(redisService.find(zrgPassiveNumKey)).orElse(0)).longValue();
                long zrgAtZeroTurnNum = ((Integer) Optional.ofNullable(redisService.find(zrgAtZeroTurnNumKey)).orElse(0)).longValue();
                long zrgAtOneTurnNum = ((Integer) Optional.ofNullable(redisService.find(zrgAtOneTurnNumKey)).orElse(0)).longValue();
                long zrgAtTwoTurnNum = ((Integer) Optional.ofNullable(redisService.find(zrgAtTwoTurnNumKey)).orElse(0)).longValue();

                //计算数据
                zrgOverviewDTO.setDate(dateSign);
                zrgOverviewDTO.setZrgSession(String.valueOf(zrgNum));
                zrgOverviewDTO.setZrgActive(String.valueOf(zrgActiveNum));
                zrgOverviewDTO.setZrgPassive(String.valueOf(zrgPassiveNum));
                zrgOverviewDTO.setZrgRate(sessionNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) zrgNum / sessionNum) : "0.0");
                String zrgAtZeroTurnRate = zrgNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) zrgAtZeroTurnNum / zrgNum) : "0.0";
                zrgOverviewDTO.setZeroTurn(zrgAtZeroTurnRate);
                String zrgAtOneTurnRate = zrgNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) zrgAtOneTurnNum / zrgNum) : "0.0";
                zrgOverviewDTO.setOneTurn(zrgAtOneTurnRate);
                String zrgAtTwoTurnRate = zrgNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) zrgAtTwoTurnNum / zrgNum) : "0.0";
                zrgOverviewDTO.setTwoTurn(zrgAtTwoTurnRate);
                String zrgAtThreeTurnRate = zrgNum > 0 ? String.valueOf(1 - Double.valueOf(zrgAtZeroTurnRate) - Double.valueOf(zrgAtOneTurnRate) - Double.valueOf(zrgAtTwoTurnRate)) : "0.0";
                zrgOverviewDTO.setThreeTurn(zrgAtThreeTurnRate);

                //计算转人工率top10的FAQ
                List<FaqZrgDTO> zrgTopFaqList = new ArrayList<>(10);
                redisService.findZSetReverseRangeWithScores(todZrgRankKey, 0, 9).forEach(x -> {
                    FaqZrgDTO faqZrgDTOTemp = new FaqZrgDTO();
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    faqZrgDTOTemp.setQuestion(question);
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    faqZrgDTOTemp.setFaqid(faqinfo[0]);
                    faqZrgDTOTemp.setFaqtype(CustomObjectUtil.changeFaqtype(faqinfo[1]));
                    faqZrgDTOTemp.setCount(String.valueOf(x.getScore()));
                    zrgTopFaqList.add(faqZrgDTOTemp);
                });
                zrgOverviewDTO.setZrgTopFaq(zrgTopFaqList);

                //计算转人工热点上升top10的FAQ
                List<FaqZrgDTO> compareTopList = new ArrayList<>(10);
                redisService.findZSetReverseRangeWithScores(todZrgRankKey, 0, -1).forEach(x -> {
                    double yesFaqZrgNum = redisService.findZSetScore(yesZrgRankKey, x.getValue());
                    FaqZrgDTO faqZrgDTOTemp = new FaqZrgDTO();
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    faqZrgDTOTemp.setQuestion(question);
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    faqZrgDTOTemp.setFaqid(faqinfo[0]);
                    faqZrgDTOTemp.setFaqtype(CustomObjectUtil.changeFaqtype(faqinfo[1]));
                    faqZrgDTOTemp.setRate(yesFaqZrgNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format(x.getScore() - yesFaqZrgNum / yesFaqZrgNum) : String.valueOf(x.getScore() - yesFaqZrgNum));
                    compareTopList.add(faqZrgDTOTemp);
                });
                Comparator<FaqZrgDTO> comparator = Comparator.comparing(FaqZrgDTO::getRate);
                zrgOverviewDTO.setCompareTop(compareTopList.parallelStream().sorted(comparator.reversed()).limit(10L).collect(Collectors.toList()));

                //计算转人工未解决率top10的FAQ
                List<FaqZrgDTO> unSolveTopList = new ArrayList<>(10);
                redisService.findZSetReverseRangeWithScores(todZrgRankKey, 0, 9).forEach(x -> {
                    double faqNum = redisService.findZSetScore(faqRankKey, x.getValue());
                    FaqZrgDTO faqZrgDTOTemp = new FaqZrgDTO();
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    faqZrgDTOTemp.setQuestion(question);
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    faqZrgDTOTemp.setFaqid(faqinfo[0]);
                    faqZrgDTOTemp.setFaqtype(CustomObjectUtil.changeFaqtype(faqinfo[1]));
                    faqZrgDTOTemp.setRate(faqNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format(x.getScore() / faqNum) : "0.0");
                    unSolveTopList.add(faqZrgDTOTemp);
                });
                zrgOverviewDTO.setUnSolveTop(unSolveTopList);

                //开始计算部分指标趋势数据并入库（有延迟）
                List<ZrgOverview> zrgOverviewList = zrgOverviewService.findByAppidAndDateSign(appId, dateSign);
                ZrgOverview zrgOverviewTemp;
                if (null != zrgOverviewList && zrgOverviewList.size() > 0) {
                    ZrgOverview zrgOverviewFilter = new ZrgOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(zrgOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    zrgOverviewTemp = ((List<ZrgOverview>) CustomObjectUtil.getObjectByChanel(zrgOverviewList, zrgOverviewFilter)).get(0);
                } else {
                    zrgOverviewTemp = new ZrgOverview();
                    zrgOverviewTemp.setIsTotal(1);
                    zrgOverviewTemp.setAppid(appId);
                    zrgOverviewTemp.setDateSign(dateSign);
                }
                zrgOverviewTemp.setData(JSONObject.toJSONString(zrgOverviewDTO));

                zrgOverviewService.save(zrgOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                log.debug("[SYS][REDUCE][Result: " + zrgOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End ZrgOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }
}


