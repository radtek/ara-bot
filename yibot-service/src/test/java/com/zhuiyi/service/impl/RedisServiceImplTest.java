package com.zhuiyi.service.impl;

import com.zhuiyi.ServiceApplication;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/30 21:07
 * description: xxx
 * own:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@Slf4j
public class RedisServiceImplTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void save() {

    }

    @Test
    public void save1() {
    }

    @Test
    public void multiSave() {
    }

    @Test
    public void find() {
    }

    @Test
    public void multiFind() {
    }

    @Test
    public void increment() {
        log.info("" + redisService.increment("today.ov.session.num", 1L));
    }

    @Test
    public void add() {

    }

    @Test
    public void add1() {
    }

    @Test
    public void findAll() {
    }
}