package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.Session;
import com.zhuiyi.service.SessionService;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@Slf4j
public class SessionServiceImplTest {

    @Autowired
    private SessionService sessionService;

    private Session sessionTemp = new Session();

    @Before
    public void runBeforeTestMethod() {

        sessionTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        sessionTemp.setStartTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setEndTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        sessionTemp.setDateSign("test");
        sessionTemp.setRoundNum(0);
        sessionTemp.setAppid("11");
        sessionTemp.setAccount("test");
        sessionTemp.setUserIp("test");
        sessionTemp.setCountry("test");
        sessionTemp.setProvince("test");
        sessionTemp.setCity("test");
        sessionTemp.setSenLevel(0);
        sessionTemp.setJudgeType(0);
        sessionTemp.setClickGoodNum(0);
        sessionTemp.setClickBadNum(0);
        sessionTemp.setSource("test");
        sessionTemp.setZrgType("test");
        sessionTemp.setZrgAt(0);
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
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        sessionTemp =  sessionService.save(sessionTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        sessionService.save(sessionTemp,"admin");
        log.info(sessionService.findById(sessionTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        sessionService.delete(sessionTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        sessionService.deleteById(sessionTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + sessionService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        sessionService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(sessionService.findById(sessionTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}