package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.TouchOverview;
import com.zhuiyi.model.dto.FaqTouchDTO;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.TouchOverviewService;
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
public class TouchOverviewTask extends QuartzJobBean {

    @Autowired
    private TouchOverviewService touchOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":TouchOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start TouchOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                //构造对应的redisKey
                String faqKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq");
                String faqRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq:rank");
                //查询当天的提问量数据
                Long currentDialogNum = (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L);

                List<FaqTouchDTO> faqTouchDTOList = new ArrayList<>();
                //计算FAQ数据排行榜
                redisService.findZSetReverseRangeWithScores(faqRankKey, 0, 4).forEach(x -> {
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    double touchRate = currentDialogNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format(x.getScore() * 100 / currentDialogNum)) : 0D;
                    faqTouchDTOList.add(new FaqTouchDTO(question, String.valueOf(x.getScore()), Long.parseLong(faqinfo[0]), Long.parseLong(faqinfo[1]), touchRate, -10000L));
                });

                //开始计算部分指标趋势数据并入库（有延迟）
                List<TouchOverview> touchOverviewList = touchOverviewService.findByAppidAndDateSign(appId, dateSign);
                TouchOverview touchOverviewTemp;
                if (null != touchOverviewList && touchOverviewList.size() > 0) {
                    TouchOverview touchOverviewFilter = new TouchOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(touchOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    touchOverviewTemp = ((List<TouchOverview>) CustomObjectUtil.getObjectByChanel(touchOverviewList, touchOverviewFilter)).get(0);
                } else {
                    touchOverviewTemp = new TouchOverview();
                    touchOverviewTemp.setIsTotal(1);
                    touchOverviewTemp.setAppid(appId);
                    touchOverviewTemp.setDateSign(dateSign);
                }
                touchOverviewTemp.setData(JSONObject.toJSONString(faqTouchDTOList));

                touchOverviewService.save(touchOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                log.debug("[SYS][REDUCE][Result: " + touchOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End TouchOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }
}


