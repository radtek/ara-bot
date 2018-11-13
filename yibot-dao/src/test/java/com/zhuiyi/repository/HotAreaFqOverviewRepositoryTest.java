package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotAreaFqOverview;
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
public class HotAreaFqOverviewRepositoryTest {

    @Autowired
    private HotAreaFqOverviewRepository hotAreaFqOverviewRepository;

    private HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();

    @Before
    public void runBeforeTestMethod() {

        hotAreaFqOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        hotAreaFqOverviewTemp.setDateSign("test");
        hotAreaFqOverviewTemp.setAppid("11");
        hotAreaFqOverviewTemp.setAreaType(0);
        hotAreaFqOverviewTemp.setAreaName("test");
        hotAreaFqOverviewTemp.setFaqId(0);
        hotAreaFqOverviewTemp.setFaq("test");
        hotAreaFqOverviewTemp.setVisitNum(0);
        hotAreaFqOverviewTemp.setVisitTrend("test");
        hotAreaFqOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotAreaFqOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        hotAreaFqOverviewTemp = hotAreaFqOverviewRepository.save(hotAreaFqOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        hotAreaFqOverviewRepository.save(hotAreaFqOverviewTemp);
        log.info(hotAreaFqOverviewRepository.findById(hotAreaFqOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(hotAreaFqOverviewRepository.findById(hotAreaFqOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + hotAreaFqOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        hotAreaFqOverviewRepository.deleteById(hotAreaFqOverviewTemp.getId());
    }
}