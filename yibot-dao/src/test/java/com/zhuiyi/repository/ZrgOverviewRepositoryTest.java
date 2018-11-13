package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.ZrgOverview;
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
public class ZrgOverviewRepositoryTest {

    @Autowired
    private ZrgOverviewRepository zrgOverviewRepository;

    private ZrgOverview zrgOverviewTemp = new ZrgOverview();

    @Before
    public void runBeforeTestMethod() {

        zrgOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        zrgOverviewTemp.setDateSign("test");
        zrgOverviewTemp.setAppid("11");
        zrgOverviewTemp.setIsTotal(0);
        zrgOverviewTemp.setCid("test");
        zrgOverviewTemp.setClient("test");
        zrgOverviewTemp.setEid("test");
        zrgOverviewTemp.setLables("test");
        zrgOverviewTemp.setIm("test");
        zrgOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        zrgOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        zrgOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        zrgOverviewTemp = zrgOverviewRepository.save(zrgOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        zrgOverviewRepository.save(zrgOverviewTemp);
        log.info(zrgOverviewRepository.findById(zrgOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(zrgOverviewRepository.findById(zrgOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + zrgOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        zrgOverviewRepository.deleteById(zrgOverviewTemp.getId());
    }
}