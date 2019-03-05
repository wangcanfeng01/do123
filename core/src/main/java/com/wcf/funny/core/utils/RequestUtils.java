package com.wcf.funny.core.utils;

import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.entity.OperationLogInfo;
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
    /**
     * 操作日志信息
     */
    private static ThreadLocal<OperationLogInfo> localLog = new ThreadLocal<>();

    /**
     * 功能描述：  设置操作日志对象
     *
     * @param logInfo
     * @return void
     * @author wangcanfeng
     * @time 2019/1/13 20:00
     * @since v1.0
     **/
    public static void setLogInfo(OperationLogInfo logInfo) {
        localLog.set(logInfo);
    }

    /**
     * 功能描述：  获取操作对象
     *
     * @return void
     * @author wangcanfeng
     * @time 2019/1/13 20:00
     * @since v1.0
     **/
    public static OperationLogInfo getLogInfo() {
       return localLog.get();
    }

    /**
     * 功能描述：  设置本地变量的操作详情
     *
     * @param
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/13 20:01
     * @since v1.0
     **/
    public static void setActionDetails(String details) {
         localLog.get().setDetails(details);
    }

    /**
     * 功能描述： 设置本地变量的操作类型
     *@author wangcanfeng
     *@time 2019/3/5 21:16
     *@since v1.0
     * @param type
     **/
    public static void setActionType(String type){
        localLog.get().setActionType(type);
    }


    /**
     * 功能描述： 设置本地变量中的操作内容
     *@author wangcanfeng
     *@time 2019/3/5 21:17
     *@since v1.0
     * @param info
     **/
    public static void setActionInfo(String info){
        localLog.get().setActionInfo(info);
    }

    /**
     * 功能描述： 设置本地变量中的操作对象
     *@author wangcanfeng
     *@time 2019/3/5 21:17
     *@since v1.0
     * @param object
     **/
    public static void setActionObject(String object){
        localLog.get().setActionObject(object);
    }

    /**
     * 功能描述：  清除本地变量中的日志信息
     *@author wangcanfeng
     *@time 2019/3/5 21:14
     *@since v1.0
     * @param
     **/
    public static void clearAll(){
        localLog.remove();
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
