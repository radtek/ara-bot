package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogDetail;
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
public class DialogDetailRepositoryTest {

    @Autowired
    private DialogDetailRepository dialogDetailRepository;

    private DialogDetail dialogDetailTemp = new DialogDetail();

    @Before
    public void runBeforeTestMethod() {

        dialogDetailTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        dialogDetailTemp.setAppid("11");
        dialogDetailTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        dialogDetailTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogDetailTemp.setRecvTime(CustomTimeUtil.getNowTimeForDate());
        dialogDetailTemp.setRetmsg("test");
        dialogDetailTemp.setDirectFaqId("test");
        dialogDetailTemp.setIndirectFaqId("test");
        dialogDetailTemp.setDirectFaq("test");
        dialogDetailTemp.setIndirectFaq("test");
        dialogDetailTemp.setUserIp("test");
        dialogDetailTemp.setQuery("test");
        dialogDetailTemp.setBizPortal("test");
        dialogDetailTemp.setBizRetCode(0);
        dialogDetailTemp.setBizDocid("test");
        dialogDetailTemp.setBizRetStr("test");
        dialogDetailTemp.setCcdCost(0);
        dialogDetailTemp.setXforward("test");
        dialogDetailTemp.setBizCost(0);
        dialogDetailTemp.setAnswerIndex("test");
        dialogDetailTemp.setPlace("test");
        dialogDetailTemp.setOther("test");
        dialogDetailTemp.setSenLevel(0);
        dialogDetailTemp.setSenWords("test");
        dialogDetailTemp.setConfidence("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        dialogDetailTemp = dialogDetailRepository.save(dialogDetailTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        dialogDetailRepository.save(dialogDetailTemp);
        log.info(dialogDetailRepository.findById(dialogDetailTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(dialogDetailRepository.findById(dialogDetailTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + dialogDetailRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        dialogDetailRepository.deleteById(dialogDetailTemp.getId());
    }
}