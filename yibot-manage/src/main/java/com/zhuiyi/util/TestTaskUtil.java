package com.zhuiyi.util;

import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.common.util.UUIDUtils;
import com.zhuiyi.model.Dialog;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.model.Session;
import com.zhuiyi.service.DialogDetailService;
import com.zhuiyi.service.DialogService;
import com.zhuiyi.service.RedisService;
import com.zhuiyi.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/24 12:20
 * description: xxx
 * own:
 */
@Component
@Slf4j
public class TestTaskUtil {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private DialogService dialogService;
    @Autowired
    private DialogDetailService dialogDetailService;

    //todo 发布时注意注释掉Scheduled

    /**
     * 每小时的10分执行该方法
     */
//    @Scheduled(cron = "0/5 * * * * *")
    public void cronDialog() throws Exception {
        Dialog dialogTemp = addDialog();
        dialogService.save(dialogTemp, "ylc");
        DialogDetail dialogDetailTemp = addDialogDetail(dialogTemp);
        dialogDetailService.save(dialogDetailTemp, "ylc");
    }

    /**
     * 每小时的10分执行该方法
     */
//    @Scheduled(cron = "0/15 * * * * *")
    public void cronFeedback() throws Exception {
        Dialog dialogTemp = addDialog();
        dialogService.update(dialogTemp, "ylc");
        Session sessionTemp = addSession();
        sessionService.update(sessionTemp, "ylc");
    }

    /**
     * 每小时的10分执行该方法
     */
//    @Scheduled(cron = "0/10 * * * * *")
    public void cronSession() throws Exception {
        Session sessionTemp = addSession();
        sessionService.save(sessionTemp, "ylc");
    }

    public Session addSession() {
        Date now = CustomTimeUtil.getNowTimeForDate();
        Session sessionTemp = new Session();
        int num = new Random().nextInt(6);
        int otherNum = new Random().nextInt(1000);

        sessionTemp.setId(UUIDUtils.getUUID());
        sessionTemp.setStartTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setEndTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(now));
        sessionTemp.setDateSign(CustomTimeUtil.getMonthAndDayString(now,CustomTimeUtil.STD_DATE_FORMATE));
        sessionTemp.setRoundNum(0);
        if (num % 2 == 0) {
            sessionTemp.setAppid("11");
        } else {
            sessionTemp.setAppid("21");
        }
        sessionTemp.setAccount("test");
        sessionTemp.setUserIp("test");
        sessionTemp.setCountry("test");
        sessionTemp.setProvince("test");
        sessionTemp.setCity("test");
        sessionTemp.setSenLevel(0);
        sessionTemp.setJudgeType(0);
        sessionTemp.setClickGoodNum(0);
        sessionTemp.setClickBadNum(0);
        sessionTemp.setSource("-");
        if (num % 2 == 0) {
            if (num == 4) {
               sessionTemp.setZrgType("12_on");
               sessionTemp.setZrgAt(0);
            } else {
               sessionTemp.setZrgType("11_on");
               sessionTemp.setZrgAt(1);
            }
        } else {
           sessionTemp.setZrgType("0_on");
           sessionTemp.setZrgAt(2);
        }
        sessionTemp.setBusinessName("test");
        sessionTemp.setCheckTag("test");
        sessionTemp.setHasHanxuan(0);
        sessionTemp.setHasReject(0);
        sessionTemp.setHasAns1(0);
        sessionTemp.setHasAns3(0);
        sessionTemp.setHasAssistant(0);
        sessionTemp.setHasAdopted(0);
        sessionTemp.setHasSecondedit(0);
        sessionTemp.setHasAbandoned(0);
        sessionTemp.setChannel("test");
        sessionTemp.setCid("test");
        sessionTemp.setEid("test");
        sessionTemp.setLables("test");
        sessionTemp.setIm("test");
        sessionTemp.setClient("test");
        sessionTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setReserved1("test");
        sessionTemp.setReserved2("test");
        sessionTemp.setReserved3("test");
        sessionTemp.setReserved4("test");
        sessionTemp.setReserved5("test");
        return sessionTemp;
    }

    public Dialog addDialog() {
        Dialog dialogTemp = new Dialog();
        Date now = CustomTimeUtil.getNowTimeForDate();
        int num = new Random().nextInt(6);
        int otherNum = new Random().nextInt(1000);

        dialogTemp.setId(UUIDUtils.getUUID());
        dialogTemp.setExactTime(now);
        dialogTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(now));
        dialogTemp.setDateSign(CustomTimeUtil.getMonthAndDayString(now,CustomTimeUtil.STD_DATE_FORMATE));
        dialogTemp.setSessionId("test");
        dialogTemp.setSearchId("test");
        if (num % 2 == 0) {
            dialogTemp.setAnswerType("0");
            dialogTemp.setAppid("11");
        } else {
            dialogTemp.setAnswerType("1");
            dialogTemp.setAppid("21");
        }
        if (num % 2 == 0) {
            dialogTemp.setFaqNum(1);
        } else {
            dialogTemp.setFaqNum(3);
        }
        dialogTemp.setRetcode(0);
        dialogTemp.setTenMin(CustomTimeUtil.getTenMinute(now));
        dialogTemp.setSource("-");
        dialogTemp.setAccount("test" + num);
        dialogTemp.setCountry("country" + num);
        dialogTemp.setProvince("province" + num);
        dialogTemp.setCity("city" + num);
        if (num % 2 == 0) {
            dialogTemp.setIsReject(0);
        } else {
            dialogTemp.setIsReject(1);
        }
        if (num % 2 == 0) {
            dialogTemp.setIsClickGood(1);
        } else {
            dialogTemp.setIsClickGood(0);
        }
        if (num % 2 == 0) {
            dialogTemp.setIsClickBad(1);
        } else {
            dialogTemp.setIsClickBad(0);
        }
        if (num % 2 == 0) {
            if (num == 4) {
                dialogTemp.setIsZrg("12_on");
            } else {
                dialogTemp.setIsZrg("11_on");
            }
        } else {
            dialogTemp.setIsZrg("0_on");
        }
        dialogTemp.setAdaptorCost(num);
        dialogTemp.setClientip("192.168.0.199");
        dialogTemp.setBizType(0);
        dialogTemp.setBusinessName("test");
        dialogTemp.setIsAssistant(0);
        dialogTemp.setChannel("Channel");
        dialogTemp.setCid("user");
        dialogTemp.setEid("");
        dialogTemp.setLables("Lables");
        dialogTemp.setIm("Im");
        dialogTemp.setClient("Client");
        dialogTemp.setTags("Tags");
        dialogTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setReserved1("test");
        dialogTemp.setReserved2("test");
        dialogTemp.setReserved3("test");
        dialogTemp.setReserved4("test");
        dialogTemp.setReserved5("test");
        if (num % 2 == 0) {
            dialogTemp.setDirectFaqId("" + (num + otherNum));
            dialogTemp.setDirectFaq("faq" + (num + otherNum));
            dialogTemp.setIndirectFaqId("" + (num + otherNum));
            dialogTemp.setIndirectFaq("faq" + (num + otherNum));
        } else {
            dialogTemp.setDirectFaqId("-");
            dialogTemp.setDirectFaq("-");
            dialogTemp.setIndirectFaqId("" + (num + otherNum));
            dialogTemp.setIndirectFaq("faq" + (num + otherNum));
        }
        return dialogTemp;
    }

    public DialogDetail addDialogDetail(Dialog dialog) {
        DialogDetail dialogDetailTemp = new DialogDetail();
        Date now = CustomTimeUtil.getNowTimeForDate();
        int num = new Random().nextInt(6);
        int otherNum = new Random().nextInt(1000);

        dialogDetailTemp.setId(dialog.getId());
        dialogDetailTemp.setAppid(dialog.getAppid());
        dialogDetailTemp.setDateMonth(dialog.getDateMonth());
        dialogDetailTemp.setExactTime(now);
        dialogDetailTemp.setRecvTime(CustomTimeUtil.getNowTimeForDate());
        dialogDetailTemp.setRetmsg("test");
        dialogDetailTemp.setDirectFaqId(dialog.getDirectFaqId());
        dialogDetailTemp.setIndirectFaqId(dialog.getIndirectFaqId());
        dialogDetailTemp.setDirectFaq(dialog.getDirectFaq());
        dialogDetailTemp.setIndirectFaq(dialog.getIndirectFaq());
        dialogDetailTemp.setUserIp("127.0.0.2");
        dialogDetailTemp.setQuery("test some thing");
        dialogDetailTemp.setBizPortal("test");
        dialogDetailTemp.setBizRetCode(0);
        dialogDetailTemp.setBizDocid("test");
        dialogDetailTemp.setBizRetStr("test");
        dialogDetailTemp.setCcdCost(0);
        dialogDetailTemp.setXforward("127.0.0.1");
        dialogDetailTemp.setBizCost(0);
        dialogDetailTemp.setAnswerIndex("test");
        dialogDetailTemp.setPlace("test");
        dialogDetailTemp.setOther("test");
        dialogDetailTemp.setSenLevel(20);
        dialogDetailTemp.setSenWords("test");
        dialogDetailTemp.setConfidence("test");
        return dialogDetailTemp;
    }
}
