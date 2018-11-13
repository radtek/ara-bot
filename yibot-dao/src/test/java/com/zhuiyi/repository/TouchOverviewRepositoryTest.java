package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.TouchOverview;
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
public class TouchOverviewRepositoryTest {

    @Autowired
    private TouchOverviewRepository touchOverviewRepository;

    private TouchOverview touchOverviewTemp = new TouchOverview();

    @Before
    public void runBeforeTestMethod() {

        touchOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        touchOverviewTemp.setDateSign("test");
        touchOverviewTemp.setAppid("11");
        touchOverviewTemp.setIsTotal(0);
        touchOverviewTemp.setCid("test");
        touchOverviewTemp.setClient("test");
        touchOverviewTemp.setEid("test");
        touchOverviewTemp.setLables("test");
        touchOverviewTemp.setIm("test");
        touchOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        touchOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        touchOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        touchOverviewTemp = touchOverviewRepository.save(touchOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        touchOverviewRepository.save(touchOverviewTemp);
        log.info(touchOverviewRepository.findById(touchOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(touchOverviewRepository.findById(touchOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + touchOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        touchOverviewRepository.deleteById(touchOverviewTemp.getId());
    }
}