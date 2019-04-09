package com.wcf.funny.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangcanfeng
 * @time 2019/1/20
 * @function 主页页面路由
 **/
@Controller
public class PageResolverController {
    /**
     * 主页跳转
     *
     * @return
     */
    @RequestMapping({"/home", "/"})
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

    /**
     * 功能描述：访问后台页面时跳转到前端登录路由
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/login")
    public String login() {
        return "/index.html";
    }

    /**
     * 功能描述：访问后台页面时跳转到前端登出路由
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/logout")
    public String logout() {
        return "/index.html";
    }

    /**
     * 功能描述：访问后台页面时跳转到前端管理界面
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/admin/**")
    public String admin() {
        return "/index.html";
    }

    /**
     * 功能描述：访问后台页面时跳转到前端个人信息页面
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/personInfo/**")
    public String personalInfo() {
        return "/index.html";
    }

    /**
     * 功能描述：访问后台页面时跳转到前端博客页面
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/blog/**")
    public String blog() {
        return "/index.html";
    }


    /**
     * 功能描述：访问后台页面时跳转到前端视频播放页面
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/video/**")
    public String video() {
        return "/index.html";
    }

    /**
     * 功能描述：访问后台页面时跳转到前端版本展示
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/10 12:05
     * @since v1.0
     **/
    @RequestMapping("/about/**")
    public String about() {
        return "/index.html";
    }


    /**
     * 功能描述：pdf展示页面
     *
     * @param
     * @author wangcanfeng
     * @time 2019/4/9 23:02
     * @since v1.0
     **/
    @RequestMapping("/readPDF")
    public String pdf() {
        return "/index.html";
    }

    /**
     * 功能描述：查询首页
     *
     * @param
     * @author wangcanfeng
     * @time 2019/4/9 23:02
     * @since v1.0
     **/
    @RequestMapping("/searchInfo")
    public String search() {
        return "/index.html";
    }

}
