package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.WholeServiceOverview;
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
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoApplication.class)
@Slf4j
public class WholeServiceOverviewRepositoryTest {

    @Autowired
    private WholeServiceOverviewRepository wholeServiceOverviewRepository;

    private WholeServiceOverview wholeServiceOverviewTemp = new WholeServiceOverview();

    @Before
    public void runBeforeTestMethod() {

        wholeServiceOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        wholeServiceOverviewTemp.setAppid("11");
        wholeServiceOverviewTemp.setDateSign("test");
        wholeServiceOverviewTemp.setIsTotal(0);
        wholeServiceOverviewTemp.setCid("test");
        wholeServiceOverviewTemp.setClient("test");
        wholeServiceOverviewTemp.setEid("test");
        wholeServiceOverviewTemp.setLables("test");
        wholeServiceOverviewTemp.setIm("test");
        wholeServiceOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        wholeServiceOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        wholeServiceOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        wholeServiceOverviewTemp = wholeServiceOverviewRepository.save(wholeServiceOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        wholeServiceOverviewRepository.save(wholeServiceOverviewTemp);
        log.info(wholeServiceOverviewRepository.findById(wholeServiceOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(wholeServiceOverviewRepository.findById(wholeServiceOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + wholeServiceOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        wholeServiceOverviewRepository.deleteById(wholeServiceOverviewTemp.getId());
    }
}