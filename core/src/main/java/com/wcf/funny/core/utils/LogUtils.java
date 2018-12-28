package com.wcf.funny.core.utils;

import com.wcf.funny.core.exception.errorcode.CoreCode;
import org.springframework.util.ObjectUtils;

/**
 * @author WCF
 * @time 2018/12/28
 * @function
 **/
public class LogUtils {
    /**
     * 功能描述: 根据错误码和错误信息生成日志信息
     *
     * @param code
     * @param msg
     * @return:java.lang.String
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2018/12/27 16:40
     */
    public static String error(String code, String msg) {
        return makeString(code, msg);
    }

    /**
     * 功能描述: 根据错误枚举生成错误信息
     *
     * @param coreCode
     * @return:java.lang.String
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2018/12/27 16:39
     */
    public static String error(CoreCode coreCode) {

        if (ObjectUtils.isEmpty(coreCode)) {
            return "";
        }
        return makeString(coreCode.getCode(), coreCode.getReason());
    }

    /**
     * 功能描述: 将字符串中的占位符替换成具体的数据
     *
     * @param code  错误码
     * @param msg   错误信息
     * @param param 参数
     * @return:java.lang.String
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2018/12/27 16:34
     */
    private static String makeString(String code, String msg, final String... param) {
        if(ObjectUtils.isEmpty(param)) {
            return "[code: " + code + ", reason: " + msg + "]";
        }
        String details = String.format(msg, (Object[]) param);
        return "[code: " + code + ", reason: " + details + "]";
    }
}
