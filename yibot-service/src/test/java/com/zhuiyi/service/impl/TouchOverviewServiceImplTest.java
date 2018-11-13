package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.TouchOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.TouchOverview;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/26
 * description:
 * own: zhuiyi
 */

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@Slf4j
public class TouchOverviewServiceImplTest {

    @Autowired
    private TouchOverviewService touchOverviewService;

    private TouchOverview touchOverviewTemp = new TouchOverview();

    @Before
    public void runBeforeTestMethod() {

        touchOverviewTemp.setDateSign("test");
        touchOverviewTemp.setAppid("11");
        touchOverviewTemp.setIsTotal(0);
        touchOverviewTemp.setCid("test");
        touchOverviewTemp.setClient("test");
        touchOverviewTemp.setEid("test");
        touchOverviewTemp.setLables("test");
        touchOverviewTemp.setIm("test");
        touchOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        touchOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        touchOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        touchOverviewTemp =  touchOverviewService.save(touchOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        touchOverviewService.save(touchOverviewTemp,"admin");
        log.info(touchOverviewService.findById(touchOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        touchOverviewService.delete(touchOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        touchOverviewService.deleteById(touchOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + touchOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        touchOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(touchOverviewService.findById(touchOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}