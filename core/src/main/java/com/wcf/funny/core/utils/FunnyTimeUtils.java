package com.wcf.funny.core.utils;

import com.wcf.funny.core.exception.TimeException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 时间转换工具
 **/
public class FunnyTimeUtils {
    enum Formatter {
        COMMON_TIME_FORMATTER("yyyy-MM-dd HH:mm:ss"),
        SHORT_TIME_FORMATTER("yyyyMMddhhmmss"),;
        private String name;

        Formatter(String name) {
            this.name = name;
        }
    }


    /**
     * 功能描述：  根据转换类型，获取日期转换工具
     *
     * @param formatter
     * @return java.time.format.DateTimeFormatter
     * @author wangcanfeng
     * @time 2019/2/4 12:43
     * @since v1.0
     **/
    private static DateTimeFormatter getFormatter(Formatter formatter) {
        return DateTimeFormatter.ofPattern(formatter.name);
    }


    /**
     * @param formatter 时间格式
     * @return java.lang.String
     * @note 返回当前时间字符串
     * @author WCF
     * @time 2018/12/26 23:52
     * @since v1.0
     **/
    public static String nowString(Formatter formatter) {
        if (ObjectUtils.isEmpty(formatter)) {
            formatter = Formatter.COMMON_TIME_FORMATTER;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter.name);
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * @return java.lang.String
     * @note 生成默认格式的当前时间字符串
     * @author WCF
     * @time 2018/12/26 23:53
     * @since v1.0
     **/
    public static String now() {
        return nowString(null);
    }

    /**
     * 功能描述：  获取当前系统时间的10位数
     *
     * @param
     * @return int
     * @author wangcanfeng
     * @time 2019/1/13 21:08
     * @since v1.0
     **/
    public static int nowUnix() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * 功能描述：  根据10位的时间戳获取时间字符串
     *
     * @param unixTime
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/28 22:25
     * @since v1.0
     **/
    public static String getTimeByUnixTime(Integer unixTime) {
        if(ObjectUtils.isEmpty(unixTime)){
            return "";
        }
        LocalDateTime time = LocalDateTime.ofEpochSecond(unixTime, 0, ZoneOffset.ofHours(8));
        return getFormatter(Formatter.COMMON_TIME_FORMATTER).format(time);
    }

     /**
     * 功能描述: 根据毫秒时间获取时间字符串
     * @param millsTime
     * @return:java.lang.String
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 11:31
     */
    public static String getTimeByMillsTime(Long millsTime){
        if(ObjectUtils.isEmpty(millsTime)){
            return "";
        }
        LocalDateTime time= Instant.ofEpochMilli(millsTime).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return getFormatter(Formatter.COMMON_TIME_FORMATTER).format(time);
    }
    
    /**
     * 功能描述：  将localdateTime对象转成unix
     *
     * @param dateTime
     * @return int
     * @author wangcanfeng
     * @time 2019/2/4 12:49
     * @since v1.0
     **/
    public static int toUnixTime(LocalDateTime dateTime) {
        long epochSecond = dateTime.toInstant(ZoneOffset.ofHours(8)).getEpochSecond();
        return (int) (epochSecond);
    }

    /**
     * 功能描述：  将时间字符串转成unix
     *
     * @param time
     * @return int
     * @author wangcanfeng
     * @time 2019/2/4 12:50
     * @since v1.0
     **/
    public static int getUnixTime(String time) {
        return toUnixTime(getLocalDateTime(time));
    }
    
      /**
     * 功能描述: 将localDateTime转成毫秒
     *
     * @param dateTime
     * @return:long
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 11:05
     */
    public static long toMillsTime(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 功能描述: 将时间字符串转成毫秒
     *
     * @param time
     * @return:long
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 11:06
     */
    public static long getMillsTime(String time) {
        return toMillsTime(getLocalDateTime(time));
    }

    /**
     * @param time
     * @return java.time.LocalDateTime
     * @note 将字符串转成localDate
     * @author WCF
     * @time 2018/6/10 20:31
     * @since v1.0
     **/
    public static LocalDateTime getLocalDateTime(String time) {
        try {
            return LocalDateTime.parse(time, getFormatter(Formatter.COMMON_TIME_FORMATTER));
        } catch (Exception e) {
            throw new TimeException(CommonCode.TIME_FORMAT_ERROR, e);
        }
    }
}
