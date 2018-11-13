package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.PartTrendOverview;
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
public class PartTrendOverviewRepositoryTest {

    @Autowired
    private PartTrendOverviewRepository partTrendOverviewRepository;

    private PartTrendOverview partTrendOverviewTemp = new PartTrendOverview();

    @Before
    public void runBeforeTestMethod() {

        partTrendOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        partTrendOverviewTemp.setDateSign("test");
        partTrendOverviewTemp.setAppid("11");
        partTrendOverviewTemp.setIsTotal(0);
        partTrendOverviewTemp.setCid("test");
        partTrendOverviewTemp.setClient("test");
        partTrendOverviewTemp.setEid("test");
        partTrendOverviewTemp.setLables("test");
        partTrendOverviewTemp.setIm("test");
        partTrendOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        partTrendOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        partTrendOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        partTrendOverviewTemp = partTrendOverviewRepository.save(partTrendOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        partTrendOverviewRepository.save(partTrendOverviewTemp);
        log.info(partTrendOverviewRepository.findById(partTrendOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(partTrendOverviewRepository.findById(partTrendOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + partTrendOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        partTrendOverviewRepository.deleteById(partTrendOverviewTemp.getId());
    }
}