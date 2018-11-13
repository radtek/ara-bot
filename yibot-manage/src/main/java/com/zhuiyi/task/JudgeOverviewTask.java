package com.zhuiyi.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.JudgeOverview;
import com.zhuiyi.model.dto.FaqJudgeDTO;
import com.zhuiyi.model.dto.JudgeOverviewDTO;
import com.zhuiyi.service.JudgeOverviewService;
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
public class JudgeOverviewTask extends QuartzJobBean {

    @Autowired
    private JudgeOverviewService judgeOverviewService;
    @Autowired
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，10分钟锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":JudgeOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start JudgeOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
                String dateSign = CustomTimeUtil.getFormatCurrentDate();
                JudgeOverviewDTO judgeOverviewDTO = new JudgeOverviewDTO();
                //默认查询普通FAQ
                String faqKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq");
                //构造对应的redisKey
                String directNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:direct:num");
                String indirectNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:indirect:num");
                String likeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:like:num");
                String dislikeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:dislike:num");
                String clickNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:click:num");
                String directLikeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:direct:like:num");
                String directDislikeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:direct:dislike:num");
                String indirectLikeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:indirect:like:num");
                String indirectDislikeNumKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "ov:indirect:dislike:num");
                String likeRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq:like:rank");
                String dislikeRankKey = CustomObjectUtil.buildKeyString(appId, monthAndToday, null, "faq:dislike:rank");

                //查询当天的提问量数据
                Long currentDialogNum = (Long) Optional.ofNullable(GlobaSystemConstant.INIT_HASHMAP.get("dialogNumKey")).orElse(0L);
                //从redis中查询出当前的计算数据
                long directNum = ((Integer) Optional.ofNullable(redisService.find(directNumKey)).orElse(0)).longValue();
                long indirectNum = ((Integer) Optional.ofNullable(redisService.find(indirectNumKey)).orElse(0)).longValue();
                long likeNum = ((Integer) Optional.ofNullable(redisService.find(likeNumKey)).orElse(0)).longValue();
                long dislikeNum = ((Integer) Optional.ofNullable(redisService.find(dislikeNumKey)).orElse(0)).longValue();
                long clickNum = ((Integer) Optional.ofNullable(redisService.find(clickNumKey)).orElse(0)).longValue();
                long directLikeNum = ((Integer) Optional.ofNullable(redisService.find(directLikeNumKey)).orElse(0)).longValue();
                long directDislikeNum = ((Integer) Optional.ofNullable(redisService.find(directDislikeNumKey)).orElse(0)).longValue();
                long indirectLikeNum = ((Integer) Optional.ofNullable(redisService.find(indirectLikeNumKey)).orElse(0)).longValue();
                long indirectDislikeNum = ((Integer) Optional.ofNullable(redisService.find(indirectDislikeNumKey)).orElse(0)).longValue();

                long judgeCount = likeNum + dislikeNum;
                String judgeRate = currentDialogNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format((float) judgeCount / currentDialogNum) : "0";

                //计算数据
                judgeOverviewDTO.setDate(dateSign);
                judgeOverviewDTO.setAgreeCount(String.valueOf(likeNum));
                judgeOverviewDTO.setAgreeRate(String.valueOf(judgeCount > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format((float) likeNum / judgeCount) : 0));
                judgeOverviewDTO.setDisagreeCount(String.valueOf(dislikeNum));
                judgeOverviewDTO.setDisagreeRate(String.valueOf(judgeCount > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format((float) dislikeNum / judgeCount) : 0));
                judgeOverviewDTO.setViews(String.valueOf(currentDialogNum));
                judgeOverviewDTO.setDirectAnswer(String.valueOf(directNum));
                judgeOverviewDTO.setDirectAnswerAC(String.valueOf(directLikeNum));
                judgeOverviewDTO.setDirectAnswerDC(String.valueOf(directDislikeNum));
                judgeOverviewDTO.setDirectAnswerDC(String.valueOf(directDislikeNum));
                judgeOverviewDTO.setRecommendAnswer(String.valueOf(indirectNum));
                judgeOverviewDTO.setRecommendAnswerViews(String.valueOf(clickNum));
                judgeOverviewDTO.setRecommendAnswerAC(String.valueOf(indirectLikeNum));
                judgeOverviewDTO.setRecommendAnswerDC(String.valueOf(indirectDislikeNum));
                judgeOverviewDTO.setJudgeCount(String.valueOf(judgeCount));
                judgeOverviewDTO.setJudgeRate(judgeRate);

                //计算点赞率top10的FAQ
                List<FaqJudgeDTO> likeList = new ArrayList<>(10);
                redisService.findZSetReverseRangeWithScores(likeRankKey, 0, 9).forEach(x -> {
                    FaqJudgeDTO faqJudgeDTOTemp = new FaqJudgeDTO();
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    faqJudgeDTOTemp.setQuestion(question);
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    faqJudgeDTOTemp.setFaqid(faqinfo[0]);
                    faqJudgeDTOTemp.setFaqtype(CustomObjectUtil.changeFaqtype(faqinfo[1]));
                    faqJudgeDTOTemp.setRate(String.valueOf(directNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format((float) dislikeNum / directNum) : 0));
                    likeList.add(faqJudgeDTOTemp);
                });
                judgeOverviewDTO.setTopAgree(likeList);
                //计算点踩率top10的FAQ
                List<FaqJudgeDTO> dislikeList = new ArrayList<>(10);
                redisService.findZSetReverseRangeWithScores(dislikeRankKey, 0, 9).forEach(x -> {
                    FaqJudgeDTO faqJudgeDTOTemp = new FaqJudgeDTO();
                    String question = (String) redisService.findHash(faqKey, (String) x.getValue());
                    faqJudgeDTOTemp.setQuestion(question);
                    String[] faqinfo = String.valueOf(x.getValue()).split(":");
                    faqJudgeDTOTemp.setFaqid(faqinfo[0]);
                    faqJudgeDTOTemp.setFaqtype(CustomObjectUtil.changeFaqtype(faqinfo[1]));
                    faqJudgeDTOTemp.setRate(String.valueOf(directNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT_FOUR.format((float) dislikeNum / directNum) : 0));
                    dislikeList.add(faqJudgeDTOTemp);
                });
                judgeOverviewDTO.setTopDisagree(dislikeList);

                //开始计算部分指标趋势数据并入库（有延迟）
                List<JudgeOverview> judgeOverviewList = judgeOverviewService.findByAppidAndDateSign(appId, dateSign);
                JudgeOverview judgeOverviewTemp;
                if (null != judgeOverviewList && judgeOverviewList.size() > 0) {
                    JudgeOverview judgeOverviewFilter = new JudgeOverview();
                    //获取主渠道数据对象
                    CustomObjectUtil.getObjectFilter(judgeOverviewFilter, null, null, null, null, null);
                    //根据渠道条件从指定数据集中筛选对应数据对象
                    judgeOverviewTemp = ((List<JudgeOverview>) CustomObjectUtil.getObjectByChanel(judgeOverviewList, judgeOverviewFilter)).get(0);
                } else {
                    judgeOverviewTemp = new JudgeOverview();
                    judgeOverviewTemp.setIsTotal(1);
                    judgeOverviewTemp.setAppid(appId);
                    judgeOverviewTemp.setDateSign(dateSign);
                }
                judgeOverviewTemp.setData(JSONObject.toJSONString(judgeOverviewDTO));
                judgeOverviewService.save(judgeOverviewTemp, GlobaSystemConstant.CUSTOM_SYSTEM_USER);

                log.debug("[SYS][REDUCE][Result: " + judgeOverviewTemp.toString() + "]");
                log.info("[SYS][REDUCE][Appid: {}][End JudgeOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (InternalServiceException e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }
}


