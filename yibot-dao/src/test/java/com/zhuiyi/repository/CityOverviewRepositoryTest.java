package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.CityOverview;
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
public class CityOverviewRepositoryTest {

    @Autowired
    private CityOverviewRepository cityOverviewRepository;

    private CityOverview cityOverviewTemp = new CityOverview();

    @Before
    public void runBeforeTestMethod() {

        cityOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        cityOverviewTemp.setDateSign("test");
        cityOverviewTemp.setAppid("21");
        cityOverviewTemp.setIsTotal(0);
        cityOverviewTemp.setCid("test");
        cityOverviewTemp.setClient("test");
        cityOverviewTemp.setEid("test");
        cityOverviewTemp.setLables("test");
        cityOverviewTemp.setIm("test");
        cityOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        cityOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        cityOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        cityOverviewTemp = cityOverviewRepository.save(cityOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        cityOverviewRepository.save(cityOverviewTemp);
        log.info(cityOverviewRepository.findById(cityOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(cityOverviewRepository.findById(cityOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + cityOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        cityOverviewRepository.deleteById(cityOverviewTemp.getId());
    }
}