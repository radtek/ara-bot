package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.ZrgOverview;
import com.zhuiyi.service.ZrgOverviewService;
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
public class ZrgOverviewServiceImplTest {

    @Autowired
    private ZrgOverviewService zrgOverviewService;

    private ZrgOverview zrgOverviewTemp = new ZrgOverview();

    @Before
    public void runBeforeTestMethod() {

        zrgOverviewTemp.setDateSign("test");
        zrgOverviewTemp.setAppid("11");
        zrgOverviewTemp.setIsTotal(0);
        zrgOverviewTemp.setCid("test");
        zrgOverviewTemp.setClient("test");
        zrgOverviewTemp.setEid("test");
        zrgOverviewTemp.setLables("test");
        zrgOverviewTemp.setIm("test");
        zrgOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        zrgOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        zrgOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        zrgOverviewTemp =  zrgOverviewService.save(zrgOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        zrgOverviewService.save(zrgOverviewTemp,"admin");
        log.info(zrgOverviewService.findById(zrgOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        zrgOverviewService.delete(zrgOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        zrgOverviewService.deleteById(zrgOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + zrgOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        zrgOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(zrgOverviewService.findById(zrgOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}