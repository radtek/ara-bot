package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogAnswer;
import com.zhuiyi.service.DialogAnswerService;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
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
 * date: 2018/08/14
 * description:
 * own: zhuiyi
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@Slf4j
public class DialogAnswerServiceImplTest {

    @Autowired
    private DialogAnswerService dialogAnswerService;

    private DialogAnswer dialogAnswerTemp = new DialogAnswer();

    @Before
    public void runBeforeTestMethod() {

        dialogAnswerTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        dialogAnswerTemp.setAppid("11");
        dialogAnswerTemp.setAnswer("test");
        dialogAnswerTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogAnswerTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        dialogAnswerTemp =  dialogAnswerService.save(dialogAnswerTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        dialogAnswerService.save(dialogAnswerTemp,"admin");
        log.info(dialogAnswerService.findById(dialogAnswerTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        dialogAnswerService.delete(dialogAnswerTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        dialogAnswerService.deleteById(dialogAnswerTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + dialogAnswerService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        dialogAnswerService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(dialogAnswerService.findById(dialogAnswerTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}