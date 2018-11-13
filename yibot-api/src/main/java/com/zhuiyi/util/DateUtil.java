package com.zhuiyi.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.TimeZone;

import com.zhuiyi.constant.GlobConts;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DateUtil {
	private DateUtil() {
	}

	/**
	 * @param source
	 * @param partern
	 * @return
	 */
	public static Date string2Date(String source , String partern) {
		SimpleDateFormat sdf = new SimpleDateFormat(partern);
		try {
			return sdf.parse(source);
		} catch (Exception e) {
			log.error("ParseException invoked {}", e);
		}
		return null;
	}
	
    /**
    * Date转换为LocalDateTime
    */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), getDefaultZoneId());
    }

    /**
    * LocalDateTime转换为Date
    */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(getDefaultZonedDateTime(time).toInstant());
    }
    
    /**
    * getSysMilli:获取指定日期的毫秒. <br/>
    */
    public static Long getSysMilli() {
    	return getMilliByTime(LocalDateTime.now());
    }
    
    /**
    * getMilliByTime: 获取指定日期的毫秒 <br/>
    */
    public static Long getMilliByTime(LocalDateTime time) {
        return getDefaultZonedDateTime(time).toInstant().toEpochMilli();
    }

    /**
    * getSecondsByTime:获取指定日期的秒. <br/>
    */
    public static Long getSecondsByTime(LocalDateTime time) {
    	
        return getDefaultZonedDateTime(time).toInstant().getEpochSecond();
    }
    
    public static ZonedDateTime getDefaultZonedDateTime(LocalDateTime time) {
    	return time.atZone(getDefaultZoneId());
    }
    
    public static ZoneId getDefaultZoneId() {
    	return TimeZone.getTimeZone("GMT+8:00").toZoneId();
    }

    /**
    * formatTime:获取指定时间的指定格式
    */
    public static String formatTime(LocalDateTime time,String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
    * formatNow:获取当前时间的指定格式
    */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
    * plus:日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
    */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
    * minu:日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * betweenTwoTime: 获取两个日期的差  field参数为ChronoUnit.*
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
        	return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
        	return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
    * getDayStart:获取一天的开始时间，2017,7,22 00:00
    */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
    * getDayEnd:获取一天的结束时间，2017,7,22 23:59:59.999999999
    */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }
	

	
	public static void main(String[] args) {
		log.info("date is {}",string2Date("1997-12-24","yyyy-MM-dd"));
		log.info("zone id is {}",getDefaultZoneId());
		log.info("now id is {}", LocalDateTime.now());
		log.info("now id is {}", convertLDTToDate(LocalDateTime.now()));
		
//		log.info("date is {}",DateUtil.string2Date("2018-04-30 23:59:53.172", GlobConts.DATE_PARTERN_LONG_NONO));

		log.info("now id is {}", DateUtil.formatNow( GlobConts.DATE_PARTERN_LONG_NONO));
		
	}

}
