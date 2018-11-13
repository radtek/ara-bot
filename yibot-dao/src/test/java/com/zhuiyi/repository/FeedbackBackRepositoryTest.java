package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.FeedbackBack;
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
 * date: 2018/07/18
 * description:
 * own: zhuiyi
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoApplication.class)
@Slf4j
public class FeedbackBackRepositoryTest {

    @Autowired
    private FeedbackBackRepository feedbackBackRepository;

    private FeedbackBack feedbackBackTemp = new FeedbackBack();

    @Before
    public void runBeforeTestMethod() {

        feedbackBackTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
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
    public void save() {
        feedbackBackTemp = feedbackBackRepository.save(feedbackBackTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        feedbackBackRepository.save(feedbackBackTemp);
        log.info(feedbackBackRepository.findById(feedbackBackTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(feedbackBackRepository.findById(feedbackBackTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + feedbackBackRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        feedbackBackRepository.deleteById(feedbackBackTemp.getId());
    }
}