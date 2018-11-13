package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.PartTrendOverview;
import com.zhuiyi.model.dto.PartTrendInfoDTO;
import com.zhuiyi.service.PartTrendOverviewService;
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
public class PartTrendOverviewTask extends QuartzJobBean {

    @Autowired
    private PartTrendOverviewService partTrendOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":PartTrendOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start PartTrendOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                String preTenMinute = CustomTimeUtil.getFormatAnyTenMinute(10L);
                String preTweMinute = CustomTimeUtil.getFormatAnyTenMinute(20L);

                //从Redis中取前10分钟的dialog日志
                List<Object> tenDialogList = (List<Object>) GlobaSystemConstant.INIT_HASHMAP.get("tenDialogKey");

                //实时进行计算平均耗时
                double costAvg = 0D;
                if (tenDialogList != null && tenDialogList.size() > 0) {
                    costAvg = tenDialogList.parallelStream().collect(Collectors.averagingInt(x -> ((Dialog) x).getAdaptorCost()));
                }

                //查询前十分钟计算的数据
                List<PartTrendOverview> partTrendOverviewList = partTrendOverviewService.findByAppidAndDateSign(appId, dateSign);

                PartTrendOverview partTrendOverviewFilter = new PartTrendOverview();
                //获取主渠道数据对象
                CustomObjectUtil.getObjectFilter(partTrendOverviewFilter, null, null, null, null, null);

                //根据渠道条件从指定数据集中筛选对应数据对象
                PartTrendOverview partTrendOverviewTemp;
                if (partTrendOverviewList != null && partTrendOverviewList.size() > 0) {
                    partTrendOverviewTemp = ((List<PartTrendOverview>) CustomObjectUtil.getObjectByChanel(partTrendOverviewList, partTrendOverviewFilter)).get(0);
                } else {
                    partTrendOverviewTemp = new PartTrendOverview();
                    partTrendOverviewTemp.setIsTotal(1);
                    partTrendOverviewTemp.setAppid(appId);
                    partTrendOverviewTemp.setDateSign(dateSign);
                }

                //开始计算整体趋势数据并入库（有延迟）
                doWholeServiceOverviewREDUCE(preTenMinute, preTweMinute,
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("sessionNumKey")).orElse(0L),
                        costAvg,
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("zrgNumKey")).orElse(0L),
                        partTrendOverviewTemp);

                partTrendOverviewService.save(partTrendOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);

                log.debug("[SYS][REDUCE][Result: " + partTrendOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End PartTrendOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }

    private void doWholeServiceOverviewREDUCE(String preTenMinute, String preTweMinute, long currentDialogNum, long currentSessionNum, Double costAvg, long currentZrgNum, PartTrendOverview partTrendOverview) {
        List<PartTrendInfoDTO> partTrendInfoDTOList = new ArrayList<>();
        PartTrendInfoDTO partTrendInfoDTOTemp = new PartTrendInfoDTO();
        partTrendInfoDTOTemp.setTime(preTenMinute);
        if (null != partTrendOverview.getData() && partTrendOverview.getData().length() > 2 && currentDialogNum > 0) {
            List<PartTrendInfoDTO> listTemp = JSONObject.parseArray(partTrendOverview.getData(), PartTrendInfoDTO.class);
            partTrendInfoDTOList.addAll(listTemp);
            listTemp.stream().filter(x -> x.getTime().equals(preTweMinute)).forEach(x -> {
                partTrendInfoDTOTemp.setAskAvg(currentDialogNum - x.getAskAvg());
                partTrendInfoDTOTemp.setCostAvg(costAvg);
                partTrendInfoDTOTemp.setSessionCount(currentSessionNum - x.getSessionCount());
                partTrendInfoDTOTemp.setZrgCount(currentZrgNum - x.getZrgCount());
                partTrendInfoDTOTemp.setZrgRate(partTrendInfoDTOTemp.getSessionCount() > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format(
                        (double) partTrendInfoDTOTemp.getZrgCount() * 100 / partTrendInfoDTOTemp.getSessionCount())) : 0D);
            });
            //如果统计频率少于10分钟，需要删除旧的，多于10分钟，此逻辑也不影响
            Long count = listTemp.stream().filter(x -> x.getTime().equals(preTenMinute)).count();
            if (count > 0) {
                listTemp.stream().filter(x -> x.getTime().equals(preTenMinute)).forEach(x -> partTrendInfoDTOList.remove(x));
            }
        } else {
            partTrendInfoDTOTemp.setAskAvg(currentDialogNum);
            partTrendInfoDTOTemp.setCostAvg(costAvg);
            partTrendInfoDTOTemp.setSessionCount(currentSessionNum);
            partTrendInfoDTOTemp.setZrgCount(currentZrgNum);
            partTrendInfoDTOTemp.setZrgRate(currentZrgNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentZrgNum * 100 / currentSessionNum)) : 0D);
        }
        if (partTrendInfoDTOTemp.getAskAvg() != null) {
            partTrendInfoDTOList.add(partTrendInfoDTOTemp);
        }
        partTrendOverview.setData(JSONObject.toJSONString(partTrendInfoDTOList));
    }
}


