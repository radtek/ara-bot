package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.WholeServiceOverview;
import com.zhuiyi.model.dto.WholeServiceOverviewDTO;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.WholeServiceOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
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
public class WholeServiceOverviewTask extends QuartzJobBean {

    @Autowired
    private WholeServiceOverviewService wholeServiceOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":WholeServiceOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start WholeServiceOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                //获取今天的dateSign
                String toDayString = CustomTimeUtil.getFormatCurrentDate();
                //获取昨天的dateSign
                String yesDayString = CustomTimeUtil.getFormatYesterdayDate();
                //获取7天前的dateSign
                String preServenDayString = CustomTimeUtil.getFormatAnyDate(7L);
                //构造对应的redisKey
                String qpsNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:qps:num");
                //从内存中取前10分钟的dialog日志
                List<Object> tenDialogList = (List<Object>) GlobaSystemConstant.INIT_HASHMAP.get("tenDialogKey");
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

                WholeServiceOverview wholeServiceOverviewFilter = new WholeServiceOverview();
                //获取主渠道数据对象
                CustomObjectUtil.getObjectFilter(wholeServiceOverviewFilter, null, null, null, null, null);

                //查询昨天和前一周的数据
                List<WholeServiceOverview> yesWholeServiceOverviewList = wholeServiceOverviewService.findByAppidAndDateSign(appId, yesDayString);
                List<WholeServiceOverview> sevWholeServiceOverviewList = wholeServiceOverviewService.findByAppidAndDateSign(appId, preServenDayString);
                //根据渠道条件从指定数据集中筛选对应数据对象
                WholeServiceOverview yesWholeServiceOverviewTemp = null;
                if (yesWholeServiceOverviewList != null && yesWholeServiceOverviewList.size() > 0) {
                    yesWholeServiceOverviewTemp = ((List<WholeServiceOverview>) CustomObjectUtil.getObjectByChanel(yesWholeServiceOverviewList, wholeServiceOverviewFilter)).get(0);
                }
                WholeServiceOverview sevWholeServiceOverviewTemp = null;
                if (sevWholeServiceOverviewList != null && sevWholeServiceOverviewList.size() > 0) {
                    sevWholeServiceOverviewTemp = ((List<WholeServiceOverview>) CustomObjectUtil.getObjectByChanel(sevWholeServiceOverviewList, wholeServiceOverviewFilter)).get(0);
                }

                //开始计算整体趋势数据并入库（有延迟）
                WholeServiceOverviewDTO wholeServiceOverviewDTO = doWholeServiceOverviewREDUCE(toDayString,
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("sessionNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("costNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("userNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("zrgNumKey")).orElse(0L),
                        currentQpsNum,
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("directNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("respondNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("likeNumKey")).orElse(0L),
                        (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dislikeNumKey")).orElse(0L),
                        yesWholeServiceOverviewTemp, sevWholeServiceOverviewTemp);

                List<WholeServiceOverview> todWholeServiceOverviewList = wholeServiceOverviewService.findByAppidAndDateSign(appId, toDayString);

                WholeServiceOverview todWholeServiceOverviewTemp;
                boolean updateFlag = true;
                if (null != todWholeServiceOverviewList && todWholeServiceOverviewList.size() > 0) {
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    todWholeServiceOverviewTemp = ((List<WholeServiceOverview>) CustomObjectUtil.getObjectByChanel(todWholeServiceOverviewList, wholeServiceOverviewFilter)).get(0);
                } else {
                    updateFlag = false;
                    todWholeServiceOverviewTemp = new WholeServiceOverview();
                    todWholeServiceOverviewTemp.setIsTotal(1);
                    todWholeServiceOverviewTemp.setAppid(appId);
                    todWholeServiceOverviewTemp.setDateSign(toDayString);
                }
                todWholeServiceOverviewTemp.setData(JSONObject.toJSONString(wholeServiceOverviewDTO));

                //判定是更新还是保存
                if (updateFlag) {
                    wholeServiceOverviewService.update(todWholeServiceOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                } else {
                    wholeServiceOverviewService.save(todWholeServiceOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                }
                log.debug("[SYS][REDUCE][Result: " + todWholeServiceOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End WholeServiceOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }

    private WholeServiceOverviewDTO doWholeServiceOverviewREDUCE(String toDayString, long currentDialogNum, long currentSessionNum, long currentCostNum, long currentUserNum, long currentZrgNum,
                                                                 long currentQpsNum, long currentDirectNum, long currentRespondNum, long currentLikeNum, long currentDislikeNum,
                                                                 WholeServiceOverview yesWholeServiceOverview, WholeServiceOverview sevWholeServiceOverview) {
        WholeServiceOverviewDTO wholeServiceOverviewDTOTemp = new WholeServiceOverviewDTO();
        if (currentDialogNum > 0) {
            HashMap<String, Object> mapTemp = new HashMap<>(3);
            //计算日期
            wholeServiceOverviewDTOTemp.setData_sign(toDayString);
            //计算提问总量
            mapTemp.put("value", currentDialogNum);
            mapTemp.put("tongRatio", getRatio("total", yesWholeServiceOverview, currentDialogNum));
            mapTemp.put("ringRatio", getRatio("total", sevWholeServiceOverview, currentDialogNum));
            wholeServiceOverviewDTOTemp.setTotal(mapTemp);
            //计算峰值
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentQpsNum);
            mapTemp.put("tongRatio", getRatio("qps", yesWholeServiceOverview, currentQpsNum));
            mapTemp.put("ringRatio", getRatio("qps", sevWholeServiceOverview, currentQpsNum));
            wholeServiceOverviewDTOTemp.setQps(mapTemp);
            //计算耗时
            mapTemp = new HashMap<>(3);
            Long cost = currentCostNum / currentDialogNum;
            mapTemp.put("value", cost);
            mapTemp.put("tongRatio", getRatio("cost", yesWholeServiceOverview, cost));
            mapTemp.put("ringRatio", getRatio("cost", sevWholeServiceOverview, cost));
            wholeServiceOverviewDTOTemp.setCost(mapTemp);
            //计算会话总数
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentSessionNum);
            mapTemp.put("tongRatio", getRatio("session", yesWholeServiceOverview, currentSessionNum));
            mapTemp.put("ringRatio", getRatio("session", sevWholeServiceOverview, currentSessionNum));
            wholeServiceOverviewDTOTemp.setSession(mapTemp);
            //计算服务响应率
            mapTemp = new HashMap<>(3);
            Double respond = (double) currentRespondNum * 100 / currentDialogNum;
            mapTemp.put("value", respond);
            mapTemp.put("tongRatio", getRatio("respond", yesWholeServiceOverview, respond));
            mapTemp.put("ringRatio", getRatio("respond", sevWholeServiceOverview, respond));
            wholeServiceOverviewDTOTemp.setRespond(mapTemp);
            //计算直接回答数量
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentDirectNum);
            mapTemp.put("tongRatio", getRatio("direct", yesWholeServiceOverview, currentDirectNum));
            mapTemp.put("ringRatio", getRatio("direct", sevWholeServiceOverview, currentDirectNum));
            wholeServiceOverviewDTOTemp.setDirect(mapTemp);
            //计算用户量
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentUserNum);
            mapTemp.put("tongRatio", getRatio("user", yesWholeServiceOverview, currentUserNum));
            mapTemp.put("ringRatio", getRatio("user", sevWholeServiceOverview, currentUserNum));
            wholeServiceOverviewDTOTemp.setUser(mapTemp);
            //计算命中率
            mapTemp = new HashMap<>(3);
            Double hitRate = Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentDirectNum * 100 / currentDialogNum));
            mapTemp.put("value", hitRate);
            mapTemp.put("tongRatio", getRatio("hitRate", yesWholeServiceOverview, hitRate));
            mapTemp.put("ringRatio", getRatio("hitRate", sevWholeServiceOverview, hitRate));
            wholeServiceOverviewDTOTemp.setHitRate(mapTemp);
            //计算点赞率
            mapTemp = new HashMap<>(3);
            long sumJudge = currentLikeNum + currentDislikeNum;
            Double likeRate = sumJudge > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentLikeNum * 100 / sumJudge)) : 0D;
            mapTemp.put("value", likeRate);
            mapTemp.put("tongRatio", getRatio("likeRate", yesWholeServiceOverview, likeRate));
            mapTemp.put("ringRatio", getRatio("likeRate", sevWholeServiceOverview, likeRate));
            wholeServiceOverviewDTOTemp.setLikeRate(mapTemp);
            //计算点赞数量
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentLikeNum);
            mapTemp.put("tongRatio", getRatio("like", yesWholeServiceOverview, currentLikeNum));
            mapTemp.put("ringRatio", getRatio("like", sevWholeServiceOverview, currentLikeNum));
            wholeServiceOverviewDTOTemp.setLike(mapTemp);
            //计算点踩率
            mapTemp = new HashMap<>(3);
            Double disLikeRate = sumJudge > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentDislikeNum * 100 / sumJudge)) : 0D;
            mapTemp.put("value", disLikeRate);
            mapTemp.put("tongRatio", getRatio("disLikeRate", yesWholeServiceOverview, disLikeRate));
            mapTemp.put("ringRatio", getRatio("disLikeRate", sevWholeServiceOverview, disLikeRate));
            wholeServiceOverviewDTOTemp.setDisLikeRate(mapTemp);
            //计算点踩数量
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentDislikeNum);
            mapTemp.put("tongRatio", getRatio("like", yesWholeServiceOverview, currentDislikeNum));
            mapTemp.put("ringRatio", getRatio("like", sevWholeServiceOverview, currentDislikeNum));
            wholeServiceOverviewDTOTemp.setDisLike(mapTemp);
            //计算转人工数量
            mapTemp = new HashMap<>(3);
            mapTemp.put("value", currentZrgNum);
            mapTemp.put("tongRatio", getRatio("artificial", yesWholeServiceOverview, currentZrgNum));
            mapTemp.put("ringRatio", getRatio("artificial", sevWholeServiceOverview, currentZrgNum));
            wholeServiceOverviewDTOTemp.setArtificial(mapTemp);
            //计算转人工率
            mapTemp = new HashMap<>(3);
            Double zrgRate = currentSessionNum > 0 ? Double.valueOf(GlobaSystemConstant.DECIMAL_FORMAT.format((double) currentZrgNum * 100 / currentSessionNum)) : 0D;
            mapTemp.put("value", zrgRate);
            mapTemp.put("tongRatio", getRatio("zrgRate", yesWholeServiceOverview, zrgRate));
            mapTemp.put("ringRatio", getRatio("zrgRate", sevWholeServiceOverview, zrgRate));
            wholeServiceOverviewDTOTemp.setZrgRate(mapTemp);
            //计算间接回答数量
            mapTemp = new HashMap<>(3);
            Long recommend = currentDialogNum - currentDirectNum;
            mapTemp.put("value", recommend);
            mapTemp.put("tongRatio", getRatio("recommend", yesWholeServiceOverview, recommend));
            mapTemp.put("ringRatio", getRatio("recommend", sevWholeServiceOverview, recommend));
            wholeServiceOverviewDTOTemp.setRecommend(mapTemp);
        }
        return wholeServiceOverviewDTOTemp;
    }

    private Double getRatio(String fileName, WholeServiceOverview wholeServiceOverview, double curValue) {
        Double result = 0D;
        Double hisValue;
        if (wholeServiceOverview != null) {
            WholeServiceOverviewDTO wholeServiceOverviewDTOTemp = JSONObject.parseObject(wholeServiceOverview.getData(), WholeServiceOverviewDTO.class);
            if (wholeServiceOverviewDTOTemp != null) {
                switch (fileName) {
                    case "total":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getTotal().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "qps":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getQps().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "cost":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getCost().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "session":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getSession().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "respond":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getRespond().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "direct":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getDirect().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "user":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getUser().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "hitRate":
                        hisValue = ((BigDecimal) wholeServiceOverviewDTOTemp.getHitRate().get("value")).doubleValue();
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "likeRate":
                        hisValue = ((BigDecimal) wholeServiceOverviewDTOTemp.getLikeRate().get("value")).doubleValue();
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "like":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getLike().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "disLikeRate":
                        hisValue = ((BigDecimal) wholeServiceOverviewDTOTemp.getDisLikeRate().get("value")).doubleValue();
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "disLike":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getDisLike().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "artificial":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getArtificial().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "zrgRate":
                        hisValue = ((BigDecimal) wholeServiceOverviewDTOTemp.getZrgRate().get("value")).doubleValue();
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    case "recommend":
                        hisValue = Double.parseDouble(String.valueOf(wholeServiceOverviewDTOTemp.getRecommend().get("value")));
                        result = hisValue > 0 ? Double.valueOf(curValue - hisValue / hisValue) : 0D;
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }
}


