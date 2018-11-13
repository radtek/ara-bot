package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.HotAreaFqOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotAreaFqOverview;
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
public class HotAreaFqOverviewServiceImplTest {

    @Autowired
    private HotAreaFqOverviewService hotAreaFqOverviewService;

    private HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();

    @Before
    public void runBeforeTestMethod() {

        hotAreaFqOverviewTemp.setDateSign("test");
        hotAreaFqOverviewTemp.setAppid("11");
        hotAreaFqOverviewTemp.setAreaType(0);
        hotAreaFqOverviewTemp.setAreaName("test");
        hotAreaFqOverviewTemp.setFaqId(0);
        hotAreaFqOverviewTemp.setFaq("test");
        hotAreaFqOverviewTemp.setVisitNum(0);
        hotAreaFqOverviewTemp.setVisitTrend("test");
        hotAreaFqOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotAreaFqOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        hotAreaFqOverviewTemp =  hotAreaFqOverviewService.save(hotAreaFqOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        hotAreaFqOverviewService.save(hotAreaFqOverviewTemp,"admin");
        log.info(hotAreaFqOverviewService.findById(hotAreaFqOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        hotAreaFqOverviewService.delete(hotAreaFqOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        hotAreaFqOverviewService.deleteById(hotAreaFqOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + hotAreaFqOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        hotAreaFqOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(hotAreaFqOverviewService.findById(hotAreaFqOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}