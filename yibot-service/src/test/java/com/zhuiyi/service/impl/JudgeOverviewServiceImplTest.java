package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.JudgeOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.JudgeOverview;
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
public class JudgeOverviewServiceImplTest {

    @Autowired
    private JudgeOverviewService judgeOverviewService;

    private JudgeOverview judgeOverviewTemp = new JudgeOverview();

    @Before
    public void runBeforeTestMethod() {

        judgeOverviewTemp.setDateSign("test");
        judgeOverviewTemp.setAppid("11");
        judgeOverviewTemp.setIsTotal(0);
        judgeOverviewTemp.setCid("test");
        judgeOverviewTemp.setClient("test");
        judgeOverviewTemp.setEid("test");
        judgeOverviewTemp.setLables("test");
        judgeOverviewTemp.setIm("test");
        judgeOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        judgeOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        judgeOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        judgeOverviewTemp =  judgeOverviewService.save(judgeOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        judgeOverviewService.save(judgeOverviewTemp,"admin");
        log.info(judgeOverviewService.findById(judgeOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        judgeOverviewService.delete(judgeOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        judgeOverviewService.deleteById(judgeOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + judgeOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        judgeOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(judgeOverviewService.findById(judgeOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}