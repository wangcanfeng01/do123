package com.wcf.funny.core.utils;

import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 多语言转换工具
 **/
public class I18Utils {
    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作类型值
     *@author wangcanfeng
     *@time 2019/2/3 21:00
     *@since v1.0
     * @param code
     *@return java.lang.String
     **/
    public static String getOpsTypeInfo(Object code){
        if(ObjectUtils.isEmpty(code)){
            return "";
        }
        return code.toString();
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作对象值
     *@author wangcanfeng
     *@time 2019/2/3 21:00
     *@since v1.0
     * @param code
     *@return java.lang.String
     **/
    public static String getOpsObejctInfo(Object code){
        if(ObjectUtils.isEmpty(code)){
            return "";
        }
        return code.toString();
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作结果值
     *@author wangcanfeng
     *@time 2019/2/3 21:00
     *@since v1.0
     * @param code
     *@return java.lang.String
     **/
    public static String getOpsResultInfo(Object code){
        if(ObjectUtils.isEmpty(code)){
            return "";
        }
        if(code.equals(1)){
            return "成功";
        }else {
            return "失败";
        }
    }

    /**
     * 功能描述：  根据编码获取对应当前语言环境的操作内容值
     *@author wangcanfeng
     *@time 2019/2/3 21:00
     *@since v1.0
     * @param code
     *@return java.lang.String
     **/
    public static String getOpsInfo(Object code){
        if(ObjectUtils.isEmpty(code)){
            return "";
        }
        return code.toString();
    }
}
