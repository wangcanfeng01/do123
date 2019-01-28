package com.wcf.funny.core.utils;

import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 时间转换工具
 **/
public class FunnyTimeUtils {
    enum Formatter{
        COMMON_TIME_FORMATTER("yyyy-MM-dd hh:mm:ss"),
        SHORT_TIME_FORMATTER("yyyyMMddhhmmss"),
        ;
        private String name;
        Formatter(String name){
            this.name=name;
        }
    }

    /**
     * 日期格式
     */
    private static DateTimeFormatter dtFormatterCommon = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     *@note 返回当前时间字符串
     *@author WCF
     *@time 2018/12/26 23:52
     *@since v1.0
     * @param formatter 时间格式
     *@return java.lang.String
     **/
    public static String nowString(Formatter formatter){
        if(ObjectUtils.isEmpty(formatter)){
            formatter=Formatter.COMMON_TIME_FORMATTER;
        }
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern(formatter.name);
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     *@note 生成默认格式的当前时间字符串
     *@author WCF
     *@time 2018/12/26 23:53
     *@since v1.0
     *@return java.lang.String
     **/
    public static String now(){
        return nowString(null);
    }

    /**
     * 功能描述：  获取当前系统时间的10位数
     *@author wangcanfeng
     *@time 2019/1/13 21:08
     *@since v1.0
     * @param
     *@return int
     **/
    public static int nowUnix(){
        return (int)(System.currentTimeMillis()/1000L);
    }

    /**
     * 功能描述：  根据10位的时间戳获取时间字符串
     *@author wangcanfeng
     *@time 2019/1/28 22:25
     *@since v1.0
     * @param unixTime
     *@return java.lang.String
     **/
    public static String getTimeByUnixTime(Integer unixTime) {
        LocalDateTime time = LocalDateTime.ofEpochSecond(unixTime, 0, ZoneOffset.ofHours(8));
        return dtFormatterCommon.format(time);
    }
}
