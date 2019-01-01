package com.wcf.funny.core.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangcanfeng
 * @time 2019/1/1
 * @function 基础控制器，获取用户名等信息
 **/
public abstract class BaseController {

    /**
     * 功能描述：  获取用户名
     *@author wangcanfeng
     *@time 2019/1/1 22:03
     *@since v1.0
     * @param request
     *@return java.lang.String
     **/
    public String getUsername(HttpServletRequest request){
        return request.getParameter("username");
    }

    /**
     * 功能描述：  获取用户密码
     *@author wangcanfeng
     *@time 2019/1/1 22:03
     *@since v1.0
     * @param request
     *@return java.lang.String
     **/
    public String getPassword(HttpServletRequest request){
        return request.getParameter("password");
    }
}
