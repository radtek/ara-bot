package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.CityOverview;
import com.zhuiyi.service.CityOverviewService;
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
public class CityOverviewServiceImplTest {

    @Autowired
    private CityOverviewService cityOverviewService;

    private CityOverview cityOverviewTemp = new CityOverview();

    @Before
    public void runBeforeTestMethod() {

        cityOverviewTemp.setDateSign("test");
        cityOverviewTemp.setAppid("11");
        cityOverviewTemp.setIsTotal(0);
        cityOverviewTemp.setCid("test");
        cityOverviewTemp.setClient("test");
        cityOverviewTemp.setEid("test");
        cityOverviewTemp.setLables("test");
        cityOverviewTemp.setIm("test");
        cityOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        cityOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        cityOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        cityOverviewTemp =  cityOverviewService.save(cityOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        cityOverviewService.save(cityOverviewTemp,"admin");
        log.info(cityOverviewService.findById(cityOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        cityOverviewService.delete(cityOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        cityOverviewService.deleteById(cityOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + cityOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        cityOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(cityOverviewService.findById(cityOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}