package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.FeedbackBack;
import com.zhuiyi.service.FeedbackBackService;
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
public class FeedbackBackServiceImplTest {

    @Autowired
    private FeedbackBackService feedbackBackService;

    private FeedbackBack feedbackBackTemp = new FeedbackBack();

    @Before
    public void runBeforeTestMethod() {
        feedbackBackTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        feedbackBackTemp.setAppid("11");
        feedbackBackTemp.setIsRedo(0);
        feedbackBackTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        feedbackBackTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        feedbackBackTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        feedbackBackTemp =  feedbackBackService.save(feedbackBackTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        feedbackBackService.save(feedbackBackTemp,"admin");
        log.info(feedbackBackService.findById(feedbackBackTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        feedbackBackService.delete(feedbackBackTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        feedbackBackService.deleteById(feedbackBackTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + feedbackBackService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        feedbackBackService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(feedbackBackService.findById(feedbackBackTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}