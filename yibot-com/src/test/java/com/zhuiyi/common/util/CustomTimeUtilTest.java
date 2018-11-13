package com.zhuiyi.common.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/1 14:49
 * description: xxx
 * own:
 */
public class CustomTimeUtilTest {

    @Test
    public void getNowTimeForDate() {
    }

    @Test
    public void getYestdayForDate() {
    }

    @Test
    public void getTodayEndTimeForDate() {
    }

    @Test
    public void getFormatCurrentDateTime() {
    }

    @Test
    public void getFormatCurrentDate() {
    }

    @Test
    public void getFormateSimMonth() {
    }

    @Test
    public void getFormateSimDate() {
    }

    @Test
    public void getTenMinute() {
        System.out.println(CustomTimeUtil.getTenMinute(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void getSimDateSignBetween() {
    }

    @Test
    public void getMonthAndDay() {
        System.out.println(CustomTimeUtil.getMonthAndDay(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
    }
}