package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.Session;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
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
 * date: 2018/07/18
 * description:
 * own: zhuiyi
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoApplication.class)
@Slf4j
public class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    private Session sessionTemp = new Session();

    @Test
    public void testfindByIdAndAppidAndDateMonth() {
    	sessionRepository.findByIdAndAppidAndDateMonth("", "", "");
    }

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
    public void save() {
        sessionTemp = sessionRepository.save(sessionTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        sessionRepository.save(sessionTemp);
        log.info(sessionRepository.findById(sessionTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(sessionRepository.findById(sessionTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + sessionRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        sessionRepository.deleteById(sessionTemp.getId());
    }
}