package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.DialogBack;
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
public class DialogBackRepositoryTest {

    @Autowired
    private DialogBackRepository dialogBackRepository;

    private DialogBack dialogBackTemp = new DialogBack();

    @Before
    public void runBeforeTestMethod() {

        dialogBackTemp.setId(new DefaultKeyGenerator().generateKey().longValue());
        dialogBackTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setAppid("11");
        dialogBackTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        dialogBackTemp.setData("test");
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        dialogBackTemp = dialogBackRepository.save(dialogBackTemp);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        dialogBackRepository.save(dialogBackTemp);
        log.info(dialogBackRepository.findById(dialogBackTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
        save();
        log.info(dialogBackRepository.findById(dialogBackTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + dialogBackRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        dialogBackRepository.deleteById(dialogBackTemp.getId());
    }
}