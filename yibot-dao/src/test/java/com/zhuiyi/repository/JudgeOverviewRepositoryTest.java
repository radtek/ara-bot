package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.JudgeOverview;
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
public class JudgeOverviewRepositoryTest {

    @Autowired
    private JudgeOverviewRepository judgeOverviewRepository;

    private JudgeOverview judgeOverviewTemp = new JudgeOverview();

    @Before
    public void runBeforeTestMethod() {

        judgeOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
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
    public void save() {
        judgeOverviewTemp = judgeOverviewRepository.save(judgeOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        judgeOverviewRepository.save(judgeOverviewTemp);
        log.info(judgeOverviewRepository.findById(judgeOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(judgeOverviewRepository.findById(judgeOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + judgeOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        judgeOverviewRepository.deleteById(judgeOverviewTemp.getId());
    }
}