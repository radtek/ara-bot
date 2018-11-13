package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.CityOverview;
import com.zhuiyi.model.dto.CityOverviewDTO;
import com.zhuiyi.model.dto.FaqInfoDTO;
import com.zhuiyi.model.dto.FaqTopDTO;
import com.zhuiyi.model.dto.FaqTouchDTO;
import com.zhuiyi.service.CityOverviewService;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
public class CityOverviewTask extends QuartzJobBean {

    @Autowired
    private CityOverviewService cityOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":CityOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start CityOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                CityOverviewDTO cityOverviewDTO = new CityOverviewDTO();
                //构造对应的redisKey
                String provinceRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "province:rank");
                String cityRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "city:rank");
                //默认查询普通FAQ
                String faqKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq");
                String cityFaqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "city:faq:rank");
                //查询当天的提问量数据
                Long currentDialogNum = (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L);
                cityOverviewDTO.setTotal_request(currentDialogNum);

                //计算FAQ省份分组数据排行榜
                List<ZSetOperations.TypedTuple<Object>> provinceRank = redisService.findZSetReverseRangeWithScores(provinceRankKey, 0, -1);
                if (provinceRank.size() > 0) {
                    cityOverviewDTO.setP_max(new FaqInfoDTO((String) provinceRank.get(0).getValue(), Math.round(provinceRank.get(0).getScore())));
                    cityOverviewDTO.setP_min(new FaqInfoDTO((String) provinceRank.get(provinceRank.size() - 1).getValue(), Math.round(provinceRank.get(provinceRank.size() - 1).getScore())));
                    cityOverviewDTO.setP_avg(Math.round(provinceRank.parallelStream().mapToDouble(ZSetOperations.TypedTuple::getScore).average().orElse(0D)));
                }

                //计算FAQ城市分组数据排行榜
                List<ZSetOperations.TypedTuple<Object>> cityRank = redisService.findZSetReverseRangeWithScores(cityRankKey, 0, -1);
                if (cityRank.size() > 0) {
                    cityOverviewDTO.setC_max(new FaqInfoDTO((String) cityRank.get(0).getValue(), Math.round(cityRank.get(0).getScore())));
                    cityOverviewDTO.setC_min(new FaqInfoDTO((String) cityRank.get(cityRank.size() - 1).getValue(), Math.round(cityRank.get(cityRank.size() - 1).getScore())));
                }

                List<FaqTopDTO> faqTopDTOList = new ArrayList<>(5);
                cityRank.stream().limit(5L).forEach(x -> {
                    FaqTopDTO faqTopDTOTemp = new FaqTopDTO("", 0L, new ArrayList<>());
                    faqTopDTOTemp.setKey((String) x.getValue());
                    faqTopDTOTemp.setValue(Math.round(x.getScore()));
                    redisService.findZSetReverseRangeWithScores(cityFaqRankKey + ":" + x.getValue(), 0, 4).forEach(y -> {
                        String question = (String) redisService.findHash(faqKey, y.getValue() + ":" + GlobaSystemConstant.FAQ_TYPE.FAQ.getElementName());
                        double touchRate = currentDialogNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format(y.getScore() * 100 / currentDialogNum)) : 0D;
                        faqTopDTOTemp.getHotFaq().add(new FaqTouchDTO(question, null, Long.parseLong((String) y.getValue()), null, touchRate, null));
                    });
                    faqTopDTOList.add(faqTopDTOTemp);
                });

                cityOverviewDTO.setC_data(faqTopDTOList);

                //开始计算部分指标趋势数据并入库（有延迟）
                List<CityOverview> cityOverviewList = cityOverviewService.findByAppidAndDateSign(appId, dateSign);
                CityOverview cityOverviewTemp;
                if (null != cityOverviewList && cityOverviewList.size() > 0) {
                    CityOverview cityOverviewFilter = new CityOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(cityOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    cityOverviewTemp = ((List<CityOverview>) CustomObjectUtil.getObjectByChanel(cityOverviewList, cityOverviewFilter)).get(0);
                } else {
                    cityOverviewTemp = new CityOverview();
                    cityOverviewTemp.setIsTotal(1);
                    cityOverviewTemp.setAppid(appId);
                    cityOverviewTemp.setDateSign(dateSign);
                }
                cityOverviewTemp.setData(JSONObject.toJSONString(cityOverviewDTO));
                cityOverviewService.save(cityOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                log.debug("[SYS][REDUCE][Result: " + cityOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End CityOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }
}


