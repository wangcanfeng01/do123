package com.wcf.funny.core.utils;

import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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
    public static String nowString(){
        return nowString(null);
    }

}
