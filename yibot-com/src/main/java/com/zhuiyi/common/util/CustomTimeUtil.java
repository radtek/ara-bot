package com.zhuiyi.common.util;

import com.zhuiyi.common.constant.GlobaSystemConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/18
 * description:
 * own: zhuiyi
 */
@SuppressWarnings("unused")
public class CustomTimeUtil {

    public static final DateTimeFormatter STD_DATE_TIME_FORMATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter STD_DATE_FORMATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SIM_MONTH_FORMATE = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter SIM_DATE_FORMATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SIM_MONTH_DAY_FORMATE = DateTimeFormatter.ofPattern("MM/dd");
    public static final DateTimeFormatter SIM_U_MONTH_DAY_FORMATE = DateTimeFormatter.ofPattern("MMdd");
    public static final DateTimeFormatter SIM_HOURE_MIN_FORMATE = DateTimeFormatter.ofPattern("HHmm");
    public static final DateTimeFormatter STD_HOURE_MIN_FORMATE = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 获取Date格式的当前时间
     *
     * @return Date 获取Date格式的当前时间
     */
    public static Date getNowTimeForDate() {
        return Date.from(LocalDateTime.now().atZone(getChinaTimeZoneId()).toInstant());
    }

    /**
     * 获取Date格式的昨天时间
     *
     * @return Date 获取Date格式的当前时间
     */
    public static Date getYestdayForDate() {
        return Date.from(LocalDateTime.now().minusDays(1L).atZone(getChinaTimeZoneId()).toInstant());
    }

    /**
     * 获取Date格式的当天的凌晨时间
     *
     * @return Date 获取Date格式的当天的凌晨时间
     */
    public static Date getTodayEndTimeForDate() {
        LocalDate nowDate = LocalDate.now().plusDays(1L);
        return Date.from(LocalDateTime.of(nowDate.getYear(), nowDate.getMonth(), nowDate.getDayOfMonth(), 00, 00, 00).atZone(getChinaTimeZoneId()).toInstant());
    }

    /**
     * 获取指定日期的的凌晨时间 yyyy-MM-dd HH:mm:ss
     *
     * @param date      指定的时间
     * @param minusDays 递减的天数 0 为当天
     * @return Date 获取Date格式的指定日期的凌晨时间
     */
    public static Date getDayStartTimeByDate(Date date, Long minusDays) {
        LocalDateTime dateTime = date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().plusDays(minusDays);
        return Date.from(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 00, 00, 00).atZone(getChinaTimeZoneId()).toInstant());
    }

    /**
     * 按照指定的格式获取今天的日期和时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化的日期和时间
     */
    public static String getFormatCurrentDateTime() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).format(CustomTimeUtil.STD_DATE_TIME_FORMATE);
    }

    /**
     * 按照指定的格式获取今天的日期 yyyy-MM-dd
     *
     * @return 格式化的日期
     */
    public static String getFormatCurrentDate() {
        return LocalDate.now().format(CustomTimeUtil.STD_DATE_FORMATE);
    }

    /**
     * 按照指定的格式获取昨天的日期 yyyy-MM-dd
     *
     * @return 格式化的日期
     */
    public static String getFormatYesterdayDate() {
        return LocalDate.now().minusDays(1L).format(CustomTimeUtil.STD_DATE_FORMATE);
    }

    /**
     * 按照指定的格式获取与今天相差指定天数的日期 yyyy-MM-dd
     *
     * @param minusDays 递减的天数
     * @return 格式化的日期
     */
    public static String getFormatAnyDate(Long minusDays) {
        return LocalDate.now().minusDays(minusDays).format(CustomTimeUtil.STD_DATE_FORMATE);
    }

    /**
     * 按照指定的格式获取与指定日期相差指定天数的日期 yyyy-MM-dd
     *
     * @param date 指定的日期串
     * @param minusDays 递减的天数
     * @return 格式化的日期
     */
    public static String getFormatAnyDate(String date,Long minusDays) {
        return LocalDate.parse(date, SIM_DATE_FORMATE).minusDays(minusDays).format(CustomTimeUtil.STD_DATE_FORMATE);
    }

    /**
     * 按照指定的格式获取当前简单年月数据 yyyyMM
     *
     * @return 格式化的简单年月数据
     */
    public static String getFormateCurrentSimMonth() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).format(CustomTimeUtil.SIM_MONTH_FORMATE);
    }

    /**
     * 按照指定的格式获取简单年月数据 yyyyMM
     *
     * @param date 当前的时间
     * @return 格式化的简单年月数据
     */
    public static String getFormateSimMonth(Date date) {
        return date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().format(CustomTimeUtil.SIM_MONTH_FORMATE);
    }

    /**
     * 按照指定的格式获取简单日期数据 yyyyMMdd
     *
     * @param date 当前的时间
     * @return 格式化的简单日期数据
     */
    public static String getFormateSimDate(Date date) {
        return date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().format(CustomTimeUtil.SIM_DATE_FORMATE);
    }

    /**
     * 按照指定的格式获取当前所属的十分钟时间 HHmm
     *
     * @param date 时间
     * @return 格式化的十分钟数据
     */
    public static String getTenMinute(Date date) {
        return date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().format(CustomTimeUtil.SIM_HOURE_MIN_FORMATE).substring(0, 3).concat("0");
    }

    /**
     * 按照指定的格式获取当前时间的前十分钟时间 HHmm
     *
     * @return 格式化的十分钟数据
     */
    public static String getNowTenMinuteBefore() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).minusMinutes(10L).format(CustomTimeUtil.SIM_HOURE_MIN_FORMATE).substring(0, 3).concat("0");
    }

    /**
     * 按照指定的格式获取与当前时间相差指定分钟数的时间 HH:mm
     *
     * @param minusMinutes 递减的分钟数
     * @return 格式化的时间
     */
    public static String getFormatAnyTenMinute(Long minusMinutes) {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).minusMinutes(minusMinutes).format(CustomTimeUtil.STD_HOURE_MIN_FORMATE).substring(0, 4).concat("0");
    }

    /**
     * 按照指定的格式获取指定日期所属的月份和天数据
     *
     * @param date      时间
     * @param formatter 时间格式
     * @return 格式化的时间数据串
     */
    public static String getMonthAndDayString(Date date, DateTimeFormatter formatter) {
        return date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().format(formatter);
    }

    /**
     * 按照指定的格式获取指定日期所属的月份和天数据 MMdd
     *
     * @param date 时间
     * @return 格式化的月份和天数据
     */
    public static String getMonthAndDay(Date date) {
        return date.toInstant().atZone(getChinaTimeZoneId()).toLocalDateTime().format(CustomTimeUtil.SIM_U_MONTH_DAY_FORMATE);
    }

    /**
     * 按照指定的格式获取当前所属的月份和天数据 MMdd
     *
     * @return 格式化的月份和天数据
     */
    public static String getNowMonthAndDay() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).format(CustomTimeUtil.SIM_U_MONTH_DAY_FORMATE);
    }

    /**
     * 按照指定的格式获取昨天所属的月份和天数据 MMdd
     *
     * @return 格式化的月份和天数据
     */
    public static String getYesMonthAndDay() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).minusDays(1L).format(CustomTimeUtil.SIM_U_MONTH_DAY_FORMATE);
    }

    /**
     * 按照指定的格式获取前天所属的月份和天数据 MMdd
     *
     * @return 格式化的月份和天数据
     */
    public static String getBeforYesMonthAndDay() {
        return LocalDateTime.now().atZone(getChinaTimeZoneId()).minusDays(2L).format(CustomTimeUtil.SIM_U_MONTH_DAY_FORMATE);
    }

    /**
     * 按照固定格式获取两个日期之间的日期列表 MM/dd
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param formatter 指定日期格式
     * @return List<String> 处理后获取的日期列表
     */
    public static List<String> getSimDateSignBetween(String startDate, String endDate, DateTimeFormatter formatter) {
        List<String> dateList = new ArrayList<>();
        LocalDate startLocalDate = LocalDate.parse(startDate, STD_DATE_TIME_FORMATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, STD_DATE_TIME_FORMATE);
        do {
            dateList.add(startLocalDate.format(formatter));
            startLocalDate = startLocalDate.plusDays(1L);
        } while (startLocalDate.isBefore(endLocalDate) || startLocalDate.isEqual(endLocalDate));
        return dateList;
    }

    /**
     * 按照固定格式对传入的日期串进行转换 yyyy-MM-dd
     *
     * @param dateSrc 开始日期
     * @return String 处理后获取的日期串
     */
    public static String changeDateFormate(String dateSrc) {
        StringBuilder dateTemp = new StringBuilder();
        dateTemp.append(dateSrc, 0, 4);
        dateTemp.append(GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);
        dateTemp.append(dateSrc, 4, 6);
        dateTemp.append(GlobaSystemConstant.KEY_FIELD_DEFAULT_VALUE);
        dateTemp.append(dateSrc, 6, 8);
        return dateTemp.toString();
    }

    /**
     * FUN 此方法描述的是：获取东八区时间
     *
     * @author klauszhou@wezhuiyi.com
     * @version 2018年8月14日 上午11:40:11
     */
    public static ZoneId getChinaTimeZoneId() {
        TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
        return tz.toZoneId();
    }
}
