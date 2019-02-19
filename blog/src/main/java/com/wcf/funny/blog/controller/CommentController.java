package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.service.CommentInfoService;
import com.wcf.funny.blog.vo.CommentVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息相关
 **/
@RestController
@RequestMapping("/ui/blog")
public class CommentController {
    @Autowired
    private CommentInfoService commentInfoService;

    /**
     * 功能描述：  查询评论记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/19 20:15
     * @since v1.0
     **/
    @GetMapping("/commentLogs")
    public BaseResponse<List<CommentVo>> getCommentLogs(@RequestParam("currentPage") Integer currentPage,
                                                        @RequestParam("pageSize") Integer pageSize) {
        PageInfo<CommentVo> pageInfo = commentInfoService.getCommentLogs(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  查询当前人员id对应的近期评论
     *
     * @author wangcanfeng
     * @time 2019/2/19 20:15
     * @since v1.0
     **/
    @GetMapping("/recentComments")
    public BaseResponse<List<CommentVo>> getRecentComments() {
        Integer limit = 10;
        String username= RequestUtils.getUserName();
        username="wcf";
        PageInfo<CommentVo> pageInfo = commentInfoService.getRecentComments(limit, username);
        return new PageResponse<>(pageInfo);
    }

    @GetMapping("/article/commentList")
    public BaseResponse<List<CommentVo>> getArticleComments(@RequestParam("currentPage") Integer currentPage,
                                                            @RequestParam("articleId")Integer articleId ) {
        PageInfo<CommentVo> pageInfo = commentInfoService.getCommentLogs(currentPage, 20);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  根据id删除评论
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/19 20:15
     * @since v1.0
     **/
    @DeleteMapping("/comment/delete/{id}")
    public BaseResponse deleteCommentById(@PathVariable("id") Integer id) {
        commentInfoService.deleteCommentById(id);
        return BaseResponse.ok();
    }

}
