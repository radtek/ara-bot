package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogBack;
import com.zhuiyi.service.DialogBackService;
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
public class DialogBackServiceImplTest {

    @Autowired
    private DialogBackService dialogBackService;

    private DialogBack dialogBackTemp = new DialogBack();

    @Before
    public void runBeforeTestMethod() {

        dialogBackTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setAppid("11");
        dialogBackTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save()throws Exception{
        dialogBackTemp =  dialogBackService.save(dialogBackTemp,"admin");
    }

    @Test
    @Transactional
    @Rollback
    public void update()throws Exception{

        dialogBackService.save(dialogBackTemp,"admin");
        log.info(dialogBackService.findById(dialogBackTemp.getId()).toString());
    }

    @Test
    @Transactional
    @Rollback
    public void delete()throws Exception{
        save();
        dialogBackService.delete(dialogBackTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById()throws Exception{
        save();
        dialogBackService.deleteById(dialogBackTemp.getId());
    }

    @Test
    public void count() {
        log.info("" + dialogBackService.count());
    }

    @Test
    @Transactional
    @Rollback
    public void findAll()throws Exception{
        dialogBackService.findAll().stream().limit(10).forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    @Rollback
    public void findById()throws Exception{
        save();
        Assert.assertNotNull(dialogBackService.findById(dialogBackTemp.getId()));
    }

    @Test
    public void findByParam()throws Exception{

    }
}