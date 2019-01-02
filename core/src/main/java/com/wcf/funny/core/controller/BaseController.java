package com.wcf.funny.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcanfeng
 * @time 2019/1/1
 * @function 基础控制器，获取用户名等信息
 **/
public abstract class BaseController {

    /**
     * 功能描述：  获取用户名
     *
     * @param request
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/1 22:03
     * @since v1.0
     **/
    public String getUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }

    /**
     * 功能描述：  获取用户密码
     *
     * @param request
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/1/1 22:03
     * @since v1.0
     **/
    public String getPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }


    /**
     * 功能描述：  vue静态调试，增加许可
     *
     * @param response
     * @return void
     * @author wangcanfeng
     * @time 2019/1/2 23:08
     * @since v1.0
     **/
    public void setAllowOrigin(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
