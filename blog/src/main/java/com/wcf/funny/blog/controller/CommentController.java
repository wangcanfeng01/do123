package com.wcf.funny.blog.controller;

import com.wcf.funny.blog.vo.CommentVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息相关
 **/
@RestController
@RequestMapping("/ui/blog")
public class CommentController {
    @GetMapping("/commentLogs")
    public BaseResponse<List<CommentVo>> getCommentLogs(){

        return new PageResponse<>();
    }
}
