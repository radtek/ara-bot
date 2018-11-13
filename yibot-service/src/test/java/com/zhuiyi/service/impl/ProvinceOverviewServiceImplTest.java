package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.ProvinceOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.ProvinceOverview;
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
public class ProvinceOverviewServiceImplTest {

    @Autowired
    private ProvinceOverviewService provinceOverviewService;

    private ProvinceOverview provinceOverviewTemp = new ProvinceOverview();

    @Before
    public void runBeforeTestMethod() {

        provinceOverviewTemp.setDateSign("test");
        provinceOverviewTemp.setAppid("11");
        provinceOverviewTemp.setIsTotal(0);
        provinceOverviewTemp.setCid("test");
        provinceOverviewTemp.setClient("test");
        provinceOverviewTemp.setEid("test");
        provinceOverviewTemp.setLables("test");
        provinceOverviewTemp.setIm("test");
        provinceOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        provinceOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        provinceOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        provinceOverviewTemp =  provinceOverviewService.save(provinceOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        provinceOverviewService.save(provinceOverviewTemp,"admin");
        log.info(provinceOverviewService.findById(provinceOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        provinceOverviewService.delete(provinceOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        provinceOverviewService.deleteById(provinceOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + provinceOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        provinceOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(provinceOverviewService.findById(provinceOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}