package com.wcf.funny.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInfoController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping({"/home","/"})
    public String home(){
        return "home";
    }
}
