package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.HotFaqOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotFaqOverview;
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
public class HotFaqOverviewServiceImplTest {

    @Autowired
    private HotFaqOverviewService hotFaqOverviewService;

    private HotFaqOverview hotFaqOverviewTemp = new HotFaqOverview();

    @Before
    public void runBeforeTestMethod() {

        hotFaqOverviewTemp.setDateSign("test");
        hotFaqOverviewTemp.setAppid("11");
        hotFaqOverviewTemp.setFaqId(0);
        hotFaqOverviewTemp.setFaq("test");
        hotFaqOverviewTemp.setVisitNum(0);
        hotFaqOverviewTemp.setVisitTrend("test");
        hotFaqOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotFaqOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        hotFaqOverviewTemp =  hotFaqOverviewService.save(hotFaqOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        hotFaqOverviewService.save(hotFaqOverviewTemp,"admin");
        log.info(hotFaqOverviewService.findById(hotFaqOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        hotFaqOverviewService.delete(hotFaqOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        hotFaqOverviewService.deleteById(hotFaqOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + hotFaqOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        hotFaqOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(hotFaqOverviewService.findById(hotFaqOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}