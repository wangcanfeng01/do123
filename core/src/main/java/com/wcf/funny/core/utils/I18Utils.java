package com.wcf.funny.core.utils;

import com.wcf.funny.core.constant.InfoEnum;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.exception.errorcode.CoreCode;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;

import java.util.Locale;
import java.util.Objects;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 多语言转换工具
 **/
public class I18Utils {

    private static MessageSource source;

    /**
     * 功能描述：  使用多语言方法时，必须要先注入一个多语言资源，否则无法获取多语言信息
     *
     * @param messageSource
     * @author wangcanfeng
     * @time 2019/3/4 22:22
     * @since v1.0
     **/
    public static void setMessageSource(MessageSource messageSource) {
        source = messageSource;
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作类型值
     *
     * @param code
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 21:00
     * @since v1.0
     **/
    public static String getOpsType(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return "";
        }
        return getLogMessage(code, LogConstant.ACTION_TYPE_PREFIX);
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作对象值
     *
     * @param code
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 21:00
     * @since v1.0
     **/
    public static String getOpsObject(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return "";
        }
        return getLogMessage(code, LogConstant.ACTION_OBJECT_PREFIX);
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作结果值
     *
     * @param code
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 21:00
     * @since v1.0
     **/
    public static String getOpsResult(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return "";
        }
        return getLogMessage(code, LogConstant.ACTION_RESULT_PREFIX);
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作内容值
     *
     * @param code
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 21:00
     * @since v1.0
     **/
    public static String getOpsInfo(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return "";
        }
        return getLogMessage(code, LogConstant.ACTION_INFO_PREFIX);
    }

    /**
     * 功能描述：根据错误码code获取翻译信息
     *
     * @param code
     * @param msg
     * @author wangcanfeng
     * @time 2019/3/4 23:19
     * @since v1.0
     **/
    public static String getErrorMessage(String code, String msg) {
        String key = code + ".error";
        String message = source.getMessage(key, new Object[]{}, Locale.SIMPLIFIED_CHINESE);
        // 如果键值没有得到翻译，仍旧返回原先的msg
        if (Objects.equals(message, key)) {
            return msg;
        }
        return message;
    }

    /**
     * 功能描述：根据前缀和编码获取日志相关的翻译
     *
     * @param code
     * @param prefix
     * @author wangcanfeng
     * @time 2019/3/4 23:19
     * @since v1.0
     **/
    public static String getLogMessage(String code, String prefix) {
        String key = prefix + code;
        return source.getMessage(key, new Object[]{}, Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 功能描述：根据信息code获取翻译信息
     *
     * @param infoEnum
     * @author wangcanfeng
     * @time 2019/3/4 23:19
     * @since v1.0
     **/
    public static String getInfoTranslation(InfoEnum infoEnum) {
        String key = "translation." + infoEnum.getCode();
        String message = source.getMessage(key, new Object[]{}, Locale.SIMPLIFIED_CHINESE);
        // 如果键值没有得到翻译，仍旧返回原先的msg
        if (Objects.equals(message, key)) {
            return infoEnum.getInfo().toString();
        }
        return message;
    }
}
