package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.WholeTrendOverview;
import com.zhuiyi.model.dto.WholeTrendOverviewDTO;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.WholeTrendOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/30 10:44
 * description:
 * own: zhuiyi
 */

@SuppressWarnings({"RedundantThrows", "unchecked", "ConstantConditions"})
@Component
@Slf4j
public class WholeTrendOverviewTask extends QuartzJobBean {

    @Autowired
    private WholeTrendOverviewService wholeTrendOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":WholeTrendOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start WholeTrendOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                //构造对应的redisKey
                String qpsNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:qps:num");
                //从内存中取前10分钟的dialog日志
                List<Object> tenDialogList = (List<Object>) GlobaSystemConstant.INIT_HASHMAP.get("tenDialogKey");
//                String appPoolKey = CustomObjectUtil.buildKeyString(dialogTemp.getAppid(), monthAndToday, null, GlobaSystemConstant.KEY_APP_NAME_STRING);
                //计算qps最大值数据
                Long qpsNum = 0L;
                if (tenDialogList.size() > 0) {
                    qpsNum = tenDialogList.parallelStream().map(x -> ((Dialog) x).getExactTime()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().parallelStream().mapToLong(Map.Entry::getValue).max().getAsLong();
                }
                //查询当前的qps数据
                Long currentQpsNum = (Long) GlobaSystemConstant.INIT_HASHMAP.get("qpsNumKey");
                //若新的数据比旧数据大，则进行替换
                if (qpsNum >= currentQpsNum) {
                    currentQpsNum = qpsNum;
                    redisService.saveAndExist(qpsNumKey, currentQpsNum, 26L, TimeUnit.HOURS);
                }

                //开始计算整体趋势数据并入库（有延迟）
                WholeTrendOverviewDTO wholeTrendOverviewDTO = doWholeTrendOverviewREDUCE(
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("sessionNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("costNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("userNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("zrgNumKey")).orElse(0L),
                        currentQpsNum,
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("directNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("respondNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("likeNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dislikeNumKey")).orElse(0L));

                List<WholeTrendOverview> wholeTrendOverviewList = wholeTrendOverviewService.findByAppidAndDateSign(appId, CustomTimeUtil.getFormatCurrentDate());
                WholeTrendOverview wholeTrendOverviewTemp;
                boolean updateFlag = true;
                if (null != wholeTrendOverviewList && wholeTrendOverviewList.size() > 0) {
                    WholeTrendOverview wholeTrendOverviewFilter = new WholeTrendOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(wholeTrendOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    wholeTrendOverviewTemp = ((List<WholeTrendOverview>) CustomObjectUtil.getObjectByChanel(wholeTrendOverviewList, wholeTrendOverviewFilter)).get(0);
                } else {
                    updateFlag = false;
                    wholeTrendOverviewTemp = new WholeTrendOverview();
                    wholeTrendOverviewTemp.setIsTotal(1);
                    wholeTrendOverviewTemp.setAppid(appId);
                    wholeTrendOverviewTemp.setDateSign(CustomTimeUtil.getFormatCurrentDate());
                }
                wholeTrendOverviewTemp.setData(JSONObject.toJSONString(wholeTrendOverviewDTO));

                //判定是更新还是保存
                if (updateFlag) {
                    wholeTrendOverviewService.update(wholeTrendOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                } else {
                    wholeTrendOverviewService.save(wholeTrendOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                }
                log.debug("[SYS][REDUCE][Result: " + wholeTrendOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End WholeTrendOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }

    private WholeTrendOverviewDTO doWholeTrendOverviewREDUCE(long currentDialogNum, long currentSessionNum, long currentCostNum, long currentUserNum, long currentZrgNum,
                                                             long currentQpsNum, long currentDirectNum, long currentRespondNum, long currentLikeNum, long currentDislikeNum) {
        WholeTrendOverviewDTO wholeTrendOverviewDTOTemp = new WholeTrendOverviewDTO();
        if (currentDialogNum > 0) {
            wholeTrendOverviewDTOTemp.setTotal(currentDialogNum);
            wholeTrendOverviewDTOTemp.setSession(currentSessionNum);
            wholeTrendOverviewDTOTemp.setCost(currentCostNum / currentDialogNum);
            wholeTrendOverviewDTOTemp.setUser(currentUserNum);
            wholeTrendOverviewDTOTemp.setZrgRate(currentSessionNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentZrgNum * 100 / currentSessionNum)) : 0D);
            wholeTrendOverviewDTOTemp.setQps(currentQpsNum);
            wholeTrendOverviewDTOTemp.setDirect(currentDirectNum);
            wholeTrendOverviewDTOTemp.setHitRate(Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentDirectNum * 100 / currentDialogNum)));
            wholeTrendOverviewDTOTemp.setRecommend(currentDialogNum - currentDirectNum);
            wholeTrendOverviewDTOTemp.setRespond(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentRespondNum * 100 / currentDialogNum));
            wholeTrendOverviewDTOTemp.setArtificial(currentZrgNum);
            wholeTrendOverviewDTOTemp.setLike(currentLikeNum);
            wholeTrendOverviewDTOTemp.setDisLike(currentDislikeNum);
            long sumJudge = currentLikeNum + currentDislikeNum;
            wholeTrendOverviewDTOTemp.setLikeRate(sumJudge > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentLikeNum * 100 / sumJudge)) : 0D);
            wholeTrendOverviewDTOTemp.setDisLikeRate(sumJudge > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentDislikeNum * 100 / sumJudge)) : 0D);
        }
        return wholeTrendOverviewDTOTemp;
    }
}


