package com.wcf.funny.video.controller;

import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function
 **/
@RestController
@RequestMapping("/ui/video/action")
public class VideoActionController {


    @PostMapping("/collection")
    public BaseResponse collectVideo(){
        return BaseResponse.ok();
    }

    @PostMapping("/cancel/collection")
    public BaseResponse cancelCollect(){
        return BaseResponse.ok();
    }
}
