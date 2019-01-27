package com.wcf.funny.core.utils;

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
     *@author wangcanfeng
     *@time 2019/1/27 22:47
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    public static String getUserName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //不可能为空，因为只有登录后才能操作菜单
        String name = request.getRemoteUser();
        return name;
    }

}
