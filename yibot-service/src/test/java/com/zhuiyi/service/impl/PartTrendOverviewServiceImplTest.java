package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.PartTrendOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.PartTrendOverview;
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
public class PartTrendOverviewServiceImplTest {

    @Autowired
    private PartTrendOverviewService partTrendOverviewService;

    private PartTrendOverview partTrendOverviewTemp = new PartTrendOverview();

    @Before
    public void runBeforeTestMethod() {

        partTrendOverviewTemp.setDateSign("test");
        partTrendOverviewTemp.setAppid("11");
        partTrendOverviewTemp.setIsTotal(0);
        partTrendOverviewTemp.setCid("test");
        partTrendOverviewTemp.setClient("test");
        partTrendOverviewTemp.setEid("test");
        partTrendOverviewTemp.setLables("test");
        partTrendOverviewTemp.setIm("test");
        partTrendOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        partTrendOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        partTrendOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        partTrendOverviewTemp =  partTrendOverviewService.save(partTrendOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        partTrendOverviewService.save(partTrendOverviewTemp,"admin");
        log.info(partTrendOverviewService.findById(partTrendOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        partTrendOverviewService.delete(partTrendOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        partTrendOverviewService.deleteById(partTrendOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + partTrendOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        partTrendOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(partTrendOverviewService.findById(partTrendOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}