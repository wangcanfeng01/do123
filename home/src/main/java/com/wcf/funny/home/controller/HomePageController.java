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
public class HomePageController {
    /**
     * 主页跳转
     *
     * @return
     */
    @RequestMapping({"/home", "/"})
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

//    /**
//     * 主页跳转
//     *
//     * @return
//     */
//    @RequestMapping("/**")
//    public String home() throws IOException {
//        return "/index.html";
//    }
}
