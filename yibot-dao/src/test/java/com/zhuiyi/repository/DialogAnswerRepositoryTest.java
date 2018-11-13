package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogAnswer;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
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
@SpringBootTest(classes = DaoApplication.class)
@Slf4j
public class DialogAnswerRepositoryTest {

    @Autowired
    private DialogAnswerRepository dialogAnswerRepository;

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
    public void save() {
        dialogAnswerTemp = dialogAnswerRepository.save(dialogAnswerTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        dialogAnswerRepository.save(dialogAnswerTemp);
        log.info(dialogAnswerRepository.findById(dialogAnswerTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(dialogAnswerRepository.findById(dialogAnswerTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + dialogAnswerRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        dialogAnswerRepository.deleteById(dialogAnswerTemp.getId());
    }
}