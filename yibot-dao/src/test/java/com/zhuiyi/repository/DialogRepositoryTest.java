package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.Dialog;
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
public class DialogRepositoryTest {

    @Autowired
    private DialogRepository dialogRepository;

    private Dialog dialogTemp = new Dialog();

    @Before
    public void runBeforeTestMethod() {

        dialogTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        dialogTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        dialogTemp.setDateSign("test");
        dialogTemp.setSessionId("test");
        dialogTemp.setSearchId("test");
        dialogTemp.setAppid("11");
        dialogTemp.setAnswerType("test");
        dialogTemp.setFaqNum(0);
        dialogTemp.setRetcode(0);
        dialogTemp.setTenMin("test");
        dialogTemp.setSource("test");
        dialogTemp.setAccount("test");
        dialogTemp.setCountry("test");
        dialogTemp.setProvince("test");
        dialogTemp.setCity("test");
        dialogTemp.setIsReject(0);
        dialogTemp.setIsClickGood(0);
        dialogTemp.setIsClickBad(0);
        dialogTemp.setIsZrg("test");
        dialogTemp.setAdaptorCost(1);
        dialogTemp.setClientip("test");
        dialogTemp.setBizType(0);
        dialogTemp.setBusinessName("test");
        dialogTemp.setIsAssistant(0);
        dialogTemp.setChannel("test");
        dialogTemp.setCid("test");
        dialogTemp.setEid("test");
        dialogTemp.setLables("test");
        dialogTemp.setIm("test");
        dialogTemp.setClient("test");
        dialogTemp.setTags("test");
        dialogTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setReserved1("test");
        dialogTemp.setReserved2("test");
        dialogTemp.setReserved3("test");
        dialogTemp.setReserved4("test");
        dialogTemp.setReserved5("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        dialogTemp = dialogRepository.save(dialogTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        dialogRepository.save(dialogTemp);
        log.info(dialogRepository.findById(dialogTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(dialogRepository.findById(dialogTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + dialogRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        dialogRepository.deleteById(dialogTemp.getId());
    }
}