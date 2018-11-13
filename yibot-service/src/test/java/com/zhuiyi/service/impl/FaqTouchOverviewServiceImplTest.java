package com.zhuiyi.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.FaqTouchOverviewService;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.FaqTouchOverview;
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
public class FaqTouchOverviewServiceImplTest {

    @Autowired
    private FaqTouchOverviewService faqTouchOverviewService;

    private FaqTouchOverview faqTouchOverviewTemp = new FaqTouchOverview();

    @Before
    public void runBeforeTestMethod() {

        faqTouchOverviewTemp.setDateSign("test");
        faqTouchOverviewTemp.setAppid("11");
        faqTouchOverviewTemp.setFaqId(0);
        faqTouchOverviewTemp.setFaq("test");
        faqTouchOverviewTemp.setCategoryId("test");
        faqTouchOverviewTemp.setTouchNum(0);
        faqTouchOverviewTemp.setDirectNum(0);
        faqTouchOverviewTemp.setRecommendNum(0);
        faqTouchOverviewTemp.setRecommendAnsNum(0);
        faqTouchOverviewTemp.setClickGood(0);
        faqTouchOverviewTemp.setClickBad(0);
        faqTouchOverviewTemp.setTouchRate("test");
        faqTouchOverviewTemp.setZdZrg(0);
        faqTouchOverviewTemp.setBdZrg(0);
        faqTouchOverviewTemp.setTotalZrg(0);
        faqTouchOverviewTemp.setClickGoodRate("test");
        faqTouchOverviewTemp.setClickBadRate("test");
        faqTouchOverviewTemp.setBizType("test");
        faqTouchOverviewTemp.setTotalClick(0);
        faqTouchOverviewTemp.setZrgRate("test");
        faqTouchOverviewTemp.setLabels("test");
        faqTouchOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        faqTouchOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        faqTouchOverviewTemp =  faqTouchOverviewService.save(faqTouchOverviewTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        faqTouchOverviewService.save(faqTouchOverviewTemp,"admin");
        log.info(faqTouchOverviewService.findById(faqTouchOverviewTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        faqTouchOverviewService.delete(faqTouchOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        faqTouchOverviewService.deleteById(faqTouchOverviewTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + faqTouchOverviewService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        faqTouchOverviewService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(faqTouchOverviewService.findById(faqTouchOverviewTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}