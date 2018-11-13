package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.ProvinceOverview;
import com.zhuiyi.model.dto.FaqTouchDTO;
import com.zhuiyi.model.dto.ProvinceOverviewDTO;
import com.zhuiyi.service.ProvinceOverviewService;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProvinceOverviewTask extends QuartzJobBean {

    @Autowired
    private ProvinceOverviewService provinceOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":ProvinceOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start ProvinceOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();

                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                //构造对应的redisKey
                String provinceRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "province:rank");
                String faqKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq");
                String provinceFaqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "province:faq:rank");
                //查询当天的提问量数据
                Long currentDialogNum = (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L);

                List<ProvinceOverviewDTO> provinceOverviewDTOList = new ArrayList<>();
                //计算FAQ省份分组数据排行榜
                redisService.findZSetReverseRangeWithScores(provinceRankKey, 0, -1).forEach(x -> {
                    ProvinceOverviewDTO provinceOverviewDTOTemp = new ProvinceOverviewDTO("", 0L, new ArrayList<>());
                    provinceOverviewDTOTemp.setProvice((String) x.getValue());
                    provinceOverviewDTOTemp.setTotal(Math.round(x.getScore()));
                    redisService.findZSetReverseRangeWithScores(provinceFaqRankKey + ":" + x.getValue(), 0, 4).forEach(y -> {
                        String question = (String) redisService.findHash(faqKey, y.getValue() + ":" + GlobaSystemConstant.FAQ_TYPE.FAQ.getElementName());
                        double touchRate = currentDialogNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format(y.getScore() * 100 / currentDialogNum)) : 0D;
                        provinceOverviewDTOTemp.getHotFaq().add(new FaqTouchDTO(question, null, Long.parseLong((String) y.getValue()), null, touchRate, null));
                    });
                    provinceOverviewDTOList.add(provinceOverviewDTOTemp);
                });

                //开始计算部分指标趋势数据并入库（有延迟）
                List<ProvinceOverview> provinceOverviewList = provinceOverviewService.findByAppidAndDateSign(appId, dateSign);
                ProvinceOverview provinceOverviewTemp;
                if (null != provinceOverviewList && provinceOverviewList.size() > 0) {
                    ProvinceOverview provinceOverviewFilter = new ProvinceOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(provinceOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    provinceOverviewTemp = ((List<ProvinceOverview>) CustomObjectUtil.getObjectByChanel(provinceOverviewList, provinceOverviewFilter)).get(0);
                } else {
                    provinceOverviewTemp = new ProvinceOverview();
                    provinceOverviewTemp.setIsTotal(1);
                    provinceOverviewTemp.setAppid(appId);
                    provinceOverviewTemp.setDateSign(dateSign);
                }
                provinceOverviewTemp.setData(JSONObject.toJSONString(provinceOverviewDTOList));

                provinceOverviewService.save(provinceOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                log.debug("[SYS][REDUCE][Result: " + provinceOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End ProvinceOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }
}


