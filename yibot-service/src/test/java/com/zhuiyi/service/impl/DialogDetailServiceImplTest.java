package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogDetail;
import com.zhuiyi.service.DialogDetailService;
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
public class DialogDetailServiceImplTest {

    @Autowired
    private DialogDetailService dialogDetailService;

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
    public void save()throws Exception{
        dialogDetailTemp =  dialogDetailService.save(dialogDetailTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        dialogDetailService.save(dialogDetailTemp,"admin");
        log.info(dialogDetailService.findById(dialogDetailTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        dialogDetailService.delete(dialogDetailTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        dialogDetailService.deleteById(dialogDetailTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + dialogDetailService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        dialogDetailService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(dialogDetailService.findById(dialogDetailTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}