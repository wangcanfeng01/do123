package com.wcf.funny.core.utils;

import com.wcf.funny.core.constant.CoreConstant;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 请求处理工具类
 **/
public class RequestUtils {
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();

    /**
     * 操作详情
     */
    private static ThreadLocal<String> actionDetails = new ThreadLocal<>();

    /**
     * 功能描述：  设置操作详情
     *
     * @param details
     * @return void
     * @author wangcanfeng
     * @time 2019/1/13 20:00
     * @since v1.0
     **/
    public static void setActionDetails(String details) {
        actionDetails.set(details);
    }

    /**
     * 功能描述：  获取本地变量的操作详情
     *
     * @param
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/13 20:01
     * @since v1.0
     **/
    public static String getActionDetails() {
        return actionDetails.get();
    }

    /**
     * 功能描述： 获取登录用户的名称
     *
     * @param
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/27 22:47
     * @since v1.0
     **/
    public static String getUserName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String name = request.getRemoteUser();
        if (ObjectUtils.isEmpty(name)) {
            return null;
        } else {
            return name;
        }
    }


    /**
     * 功能描述： 获取远程用户的ip地址
     *
     * @param
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/27 22:47
     * @since v1.0
     **/
    public static String getRemoteIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String address = request.getRemoteAddr();
        if (ObjectUtils.isEmpty(address)) {
            return CoreConstant.UNKNOWN_ADDRESS;
        } else {
            return address;
        }
    }


}
