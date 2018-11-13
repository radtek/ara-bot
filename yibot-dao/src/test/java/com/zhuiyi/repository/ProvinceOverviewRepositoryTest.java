package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.ProvinceOverview;
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
public class ProvinceOverviewRepositoryTest {

    @Autowired
    private ProvinceOverviewRepository provinceOverviewRepository;

    private ProvinceOverview provinceOverviewTemp = new ProvinceOverview();

    @Before
    public void runBeforeTestMethod() {

        provinceOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        provinceOverviewTemp.setDateSign("test");
        provinceOverviewTemp.setAppid("11");
        provinceOverviewTemp.setIsTotal(0);
        provinceOverviewTemp.setCid("test");
        provinceOverviewTemp.setClient("test");
        provinceOverviewTemp.setEid("test");
        provinceOverviewTemp.setLables("test");
        provinceOverviewTemp.setIm("test");
        provinceOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        provinceOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        provinceOverviewTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        provinceOverviewTemp = provinceOverviewRepository.save(provinceOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        provinceOverviewRepository.save(provinceOverviewTemp);
        log.info(provinceOverviewRepository.findById(provinceOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(provinceOverviewRepository.findById(provinceOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + provinceOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        provinceOverviewRepository.deleteById(provinceOverviewTemp.getId());
    }
}