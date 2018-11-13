package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.HotFaqOverview;
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
public class HotFaqOverviewRepositoryTest {

    @Autowired
    private HotFaqOverviewRepository hotFaqOverviewRepository;

    private HotFaqOverview hotFaqOverviewTemp = new HotFaqOverview();

    @Before
    public void runBeforeTestMethod() {

        hotFaqOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        hotFaqOverviewTemp.setDateSign("test");
        hotFaqOverviewTemp.setAppid("11");
        hotFaqOverviewTemp.setFaqId(0);
        hotFaqOverviewTemp.setFaq("test");
        hotFaqOverviewTemp.setVisitNum(0);
        hotFaqOverviewTemp.setVisitTrend("test");
        hotFaqOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotFaqOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        hotFaqOverviewTemp = hotFaqOverviewRepository.save(hotFaqOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        hotFaqOverviewRepository.save(hotFaqOverviewTemp);
        log.info(hotFaqOverviewRepository.findById(hotFaqOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(hotFaqOverviewRepository.findById(hotFaqOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + hotFaqOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        hotFaqOverviewRepository.deleteById(hotFaqOverviewTemp.getId());
    }
}