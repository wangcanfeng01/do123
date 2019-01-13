package com.wcf.funny.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserInfoController {

    /**
     * 主页跳转
     *
     * @return
     */
    @RequestMapping({"/home", "/"})
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }
}
