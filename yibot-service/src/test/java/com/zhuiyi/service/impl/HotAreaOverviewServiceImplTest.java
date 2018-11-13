package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.HotAreaOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotAreaOverview;
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
public class HotAreaOverviewServiceImplTest {

    @Autowired
    private HotAreaOverviewService hotAreaOverviewService;

    private HotAreaOverview hotAreaOverviewTemp = new HotAreaOverview();

    @Before
    public void runBeforeTestMethod() {

        hotAreaOverviewTemp.setDateSign("test");
        hotAreaOverviewTemp.setAppid("11");
        hotAreaOverviewTemp.setAreaType(0);
        hotAreaOverviewTemp.setAreaName("test");
        hotAreaOverviewTemp.setVisitNum(0);
        hotAreaOverviewTemp.setVisitTrend("test");
        hotAreaOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotAreaOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        hotAreaOverviewTemp =  hotAreaOverviewService.save(hotAreaOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        hotAreaOverviewService.save(hotAreaOverviewTemp,"admin");
        log.info(hotAreaOverviewService.findById(hotAreaOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        hotAreaOverviewService.delete(hotAreaOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        hotAreaOverviewService.deleteById(hotAreaOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + hotAreaOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        hotAreaOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(hotAreaOverviewService.findById(hotAreaOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}