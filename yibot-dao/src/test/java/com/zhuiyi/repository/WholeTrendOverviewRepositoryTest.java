package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.WholeTrendOverview;
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
public class WholeTrendOverviewRepositoryTest {

    @Autowired
    private WholeTrendOverviewRepository wholeTrendOverviewRepository;

    private WholeTrendOverview wholeTrendOverviewTemp = new WholeTrendOverview();

    @Before
    public void runBeforeTestMethod() {

        wholeTrendOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        wholeTrendOverviewTemp.setDateSign("2018-07-28");
        wholeTrendOverviewTemp.setAppid("11");
        wholeTrendOverviewTemp.setIsTotal(0);
        wholeTrendOverviewTemp.setCid("test");
        wholeTrendOverviewTemp.setClient("test");
        wholeTrendOverviewTemp.setEid("test");
        wholeTrendOverviewTemp.setLables("test");
        wholeTrendOverviewTemp.setIm("test");
        wholeTrendOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        wholeTrendOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        wholeTrendOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        wholeTrendOverviewTemp = wholeTrendOverviewRepository.save(wholeTrendOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        wholeTrendOverviewRepository.save(wholeTrendOverviewTemp);
        log.info(wholeTrendOverviewRepository.findById(wholeTrendOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(wholeTrendOverviewRepository.findById(wholeTrendOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + wholeTrendOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        wholeTrendOverviewRepository.deleteById(wholeTrendOverviewTemp.getId());
    }

    @Test
    public void findByAppidAndDateSign() {

    }
}