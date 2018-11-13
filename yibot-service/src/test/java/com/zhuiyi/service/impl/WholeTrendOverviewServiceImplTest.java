package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.WholeTrendOverview;
import com.zhuiyi.service.WholeTrendOverviewService;
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
public class WholeTrendOverviewServiceImplTest {

    @Autowired
    private WholeTrendOverviewService wholeTrendOverviewService;

    private WholeTrendOverview wholeTrendOverviewTemp = new WholeTrendOverview();

    @Before
    public void runBeforeTestMethod() {

        wholeTrendOverviewTemp.setDateSign("test");
        wholeTrendOverviewTemp.setAppid("11");
        wholeTrendOverviewTemp.setIsTotal(0);
        wholeTrendOverviewTemp.setCid("test");
        wholeTrendOverviewTemp.setClient("test");
        wholeTrendOverviewTemp.setEid("test");
        wholeTrendOverviewTemp.setLables("test");
        wholeTrendOverviewTemp.setIm("test");
        wholeTrendOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        wholeTrendOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        wholeTrendOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        wholeTrendOverviewTemp =  wholeTrendOverviewService.save(wholeTrendOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        wholeTrendOverviewService.save(wholeTrendOverviewTemp,"admin");
        log.info(wholeTrendOverviewService.findById(wholeTrendOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        wholeTrendOverviewService.delete(wholeTrendOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        wholeTrendOverviewService.deleteById(wholeTrendOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + wholeTrendOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        wholeTrendOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(wholeTrendOverviewService.findById(wholeTrendOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}