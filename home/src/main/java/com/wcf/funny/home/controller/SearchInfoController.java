package com.wcf.funny.home.controller;

import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcanfeng
 * @time 2019/4/9
 * @function 查询信息页面控制器
 **/
@RestController
@RequestMapping("/ui/search")
public class SearchInfoController {
    /**
     * 功能描述：根据检索内容关键字查询结果
     *
     * @param keys
     * @author wangcanfeng
     * @time 2019/4/9 23:06
     * @since v1.0
     **/
    @GetMapping("/keywords")
    public BaseResponse searchInfo(@RequestParam("keys") String keys) {

        return BaseResponse.ok();
    }
}
