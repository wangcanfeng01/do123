package com.wcf.funny.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WCF
 * @time 2018/12/27
 * @function
 **/
@Controller
public class IndexPageController {
    @RequestMapping("/login")
    public String login() {
       return "login";
    }
}
