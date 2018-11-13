package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotAreaOverview;
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
public class HotAreaOverviewRepositoryTest {

    @Autowired
    private HotAreaOverviewRepository hotAreaOverviewRepository;

    private HotAreaOverview hotAreaOverviewTemp = new HotAreaOverview();

    @Before
    public void runBeforeTestMethod() {

        hotAreaOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        hotAreaOverviewTemp.setDateSign("test");
        hotAreaOverviewTemp.setAppid("11");
        hotAreaOverviewTemp.setAreaType(0);
        hotAreaOverviewTemp.setAreaName("test");
        hotAreaOverviewTemp.setVisitNum(0);
        hotAreaOverviewTemp.setVisitTrend("test");
        hotAreaOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotAreaOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        hotAreaOverviewTemp = hotAreaOverviewRepository.save(hotAreaOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        hotAreaOverviewRepository.save(hotAreaOverviewTemp);
        log.info(hotAreaOverviewRepository.findById(hotAreaOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(hotAreaOverviewRepository.findById(hotAreaOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + hotAreaOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        hotAreaOverviewRepository.deleteById(hotAreaOverviewTemp.getId());
    }
}