package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.FaqTouchOverview;
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
public class FaqTouchOverviewRepositoryTest {

    @Autowired
    private FaqTouchOverviewRepository faqTouchOverviewRepository;

    private FaqTouchOverview faqTouchOverviewTemp = new FaqTouchOverview();

    @Before
    public void runBeforeTestMethod() {

        faqTouchOverviewTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        faqTouchOverviewTemp.setDateSign("test");
        faqTouchOverviewTemp.setAppid("11");
        faqTouchOverviewTemp.setFaqId(0);
        faqTouchOverviewTemp.setFaq("test");
        faqTouchOverviewTemp.setCategoryId("test");
        faqTouchOverviewTemp.setTouchNum(0);
        faqTouchOverviewTemp.setDirectNum(0);
        faqTouchOverviewTemp.setRecommendNum(0);
        faqTouchOverviewTemp.setRecommendAnsNum(0);
        faqTouchOverviewTemp.setClickGood(0);
        faqTouchOverviewTemp.setClickBad(0);
        faqTouchOverviewTemp.setTouchRate("test");
        faqTouchOverviewTemp.setZdZrg(0);
        faqTouchOverviewTemp.setBdZrg(0);
        faqTouchOverviewTemp.setTotalZrg(0);
        faqTouchOverviewTemp.setClickGoodRate("test");
        faqTouchOverviewTemp.setClickBadRate("test");
        faqTouchOverviewTemp.setBizType("test");
        faqTouchOverviewTemp.setTotalClick(0);
        faqTouchOverviewTemp.setZrgRate("test");
        faqTouchOverviewTemp.setLabels("test");
        faqTouchOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        faqTouchOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        faqTouchOverviewTemp = faqTouchOverviewRepository.save(faqTouchOverviewTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        faqTouchOverviewRepository.save(faqTouchOverviewTemp);
        log.info(faqTouchOverviewRepository.findById(faqTouchOverviewTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(faqTouchOverviewRepository.findById(faqTouchOverviewTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + faqTouchOverviewRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        faqTouchOverviewRepository.deleteById(faqTouchOverviewTemp.getId());
    }
}