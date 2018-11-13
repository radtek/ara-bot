package com.zhuiyi.task;

import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.RedisLock;
import com.zhuiyi.exception.InternalServiceException;
import com.zhuiyi.model.FaqTouchOverview;
import com.zhuiyi.service.FaqTouchOverviewService;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
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

@SuppressWarnings({"RedundantThrows", "unchecked", "ConstantConditions"})
@Component
@Slf4j
public class FaqTouchOverviewTask extends QuartzJobBean {

    @Autowired
    private FaqTouchOverviewService faqTouchOverviewService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EntityManager entityManager;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前计算的业务标识
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String appId = (String) dataMap.get("appid");
        //开始获取任务执行锁，1个小时锁自动过期
        Optional<RedisLock> redisLockOptional = redisService.getLock(appId + ":FaqTouchOverviewTask", GlobaSystemConstant.LOCK_TIMEOUT_MSECS, GlobaSystemConstant.LOCK_EXPIRE_MSECS * 6);
        try {
            if (redisLockOptional.isPresent()) {
                log.info("[SYS][REDUCE][Appid: {}][Start FaqTouchOverviewTask][Time: {}]", appId, CustomTimeUtil.getFormatCurrentDateTime());
                long start = System.currentTimeMillis();
                String simMonth = CustomTimeUtil.getFormateCurrentSimMonth();
                String dateSign = CustomTimeUtil.getFormatYesterdayDate();
//                String dateSign = CustomTimeUtil.getFormatCurrentDate();

                List<FaqTouchOverview> faqTouchOverviewList = getFaqTouchOverviewDataByDayInSQL(appId, dateSign, simMonth);

                faqTouchOverviewList.forEach(x -> {
                    try {
                        faqTouchOverviewService.save(x, GlobaSystemConstant.CUSTOM_SYSTEM_USER);
                    } catch (InternalServiceException e) {
                        e.printStackTrace();
                    }
                });

                log.info("[SYS][REDUCE][Appid: {}][End FaqTouchOverviewTask][Time: {}][COST: {} ms]", appId, CustomTimeUtil.getFormatCurrentDateTime(), System.currentTimeMillis() - start);
            }
        } catch (Exception e) {
            log.error("[SYS][REDUCE][Result: task is not finish]");
            e.printStackTrace();
        } finally {
            redisLockOptional.ifPresent(RedisLock::unlock);
        }
    }

    private FaqTouchOverview getFaqTouchOverviewDataByDayInRedis() {
        return null;
    }

    private List<FaqTouchOverview> getFaqTouchOverviewDataByDayInSQL(String appId, String dateSign, String simMonth) {
        List<FaqTouchOverview> faqTouchOverviewListTemp = new ArrayList<>();

        String sql = "select count(1) as total from t_dialog a join t_dialog_detail b on a.id = b.id where a.appid = '" + appId + "' " +
                "and a.date_month = '" + simMonth + "' and b.date_month = '" + simMonth + "'" +
                "and a.source = '-' and a.cid = 'user'  and a.date_sign = '" + dateSign + "' and a.is_reject <> '1' and a.faq_num = 1 and a.retcode = '0'";

        List<Object> totalList = entityManager.createNativeQuery(sql).getResultList();

        long total = totalList.size() > 0 ? Long.valueOf(totalList.get(0).toString()) : 0L;

        //直接从数据库中捞取一天的数据进行分析
        String sql1 = "select b.direct_faq_id, b.direct_faq, a.biz_type, count(b.direct_faq_id) as touch_num,  sum(a.is_click_good) as click_good," +
                "sum(a.is_click_bad) as click_bad,sum(if (a.answer_type = '0', 1,0)) as direct_num, sum(if(a.answer_type = '2', 1,0)) as recommend_click,  " +
                "sum(case when a.is_zrg = '11_on' or a.is_zrg = '11_off' then 1 else 0 end )  as zd_zrg, " +
                "sum(case when a.is_zrg = '12_on' or a.is_zrg = '12_off' then 1 else 0 end )  as bd_zrg " +
                "from t_dialog a join t_dialog_detail b on a.id = b.id where a.appid = '" + appId + "' and a.date_month = '" + simMonth + "' and b.date_month = '" + simMonth + "' " +
                "and a.source = '-' and a.cid = 'user' and a.date_sign = '" + dateSign + "' and a.is_reject <> '1' and a.faq_num = 1 and a.retcode = '0' group by b.direct_faq_id order by b.direct_faq_id";
        List<Object> objList = entityManager.createNativeQuery(sql1).getResultList();

        if (objList.size() > 0) {
            objList.forEach(x -> {
                FaqTouchOverview faqTouchOverviewTemp = new FaqTouchOverview();
                Object[] cells = (Object[]) x;
                faqTouchOverviewTemp.setAppid(appId);
                faqTouchOverviewTemp.setDateSign(dateSign);
                faqTouchOverviewTemp.setFaqId(Integer.parseInt((String) cells[0]));
                faqTouchOverviewTemp.setFaq((String) cells[1]);
                faqTouchOverviewTemp.setBizType(cells[2].toString());
                //目前默认为-
                faqTouchOverviewTemp.setCategoryId(GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);
                int touchNum = ((BigInteger) cells[3]).intValue();
                faqTouchOverviewTemp.setTouchNum(touchNum);
                String touchRate = total > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) touchNum / total) : "0";
                faqTouchOverviewTemp.setTouchRate(touchRate);
                faqTouchOverviewTemp.setClickGood(((BigDecimal) cells[4]).intValue());
                String clickGoodRate = touchNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) faqTouchOverviewTemp.getClickGood() / touchNum) : "0";
                faqTouchOverviewTemp.setClickGoodRate(clickGoodRate);
                faqTouchOverviewTemp.setClickBad(((BigDecimal) cells[5]).intValue());
                String clickBadRate = touchNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) faqTouchOverviewTemp.getClickBad() / touchNum) : "0";
                faqTouchOverviewTemp.setClickBadRate(clickBadRate);
                faqTouchOverviewTemp.setDirectNum(((BigDecimal) cells[6]).intValue());
                faqTouchOverviewTemp.setRecommendNum(((BigDecimal) cells[7]).intValue());
                //目前默认为0
                faqTouchOverviewTemp.setRecommendAnsNum(0);
                faqTouchOverviewTemp.setZdZrg(((BigDecimal) cells[8]).intValue());
                faqTouchOverviewTemp.setBdZrg(((BigDecimal) cells[9]).intValue());
                faqTouchOverviewTemp.setTotalZrg(faqTouchOverviewTemp.getZdZrg() + faqTouchOverviewTemp.getBdZrg());
                faqTouchOverviewTemp.setZrgRate(touchNum > 0 ? GlobaSystemConstant.DECIMAL_FORMAT.format((float) faqTouchOverviewTemp.getTotalZrg() / touchNum) : "0");
                faqTouchOverviewListTemp.add(faqTouchOverviewTemp);
            });
        }
        return faqTouchOverviewListTemp;
    }
}


