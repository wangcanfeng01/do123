package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.CommentInfo;
import com.wcf.funny.blog.service.CommentInfoService;
import com.wcf.funny.blog.vo.CommentVo;
import com.wcf.funny.blog.vo.req.CommentReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
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
        String username = RequestUtils.getUserName();
        PageInfo<CommentVo> pageInfo = commentInfoService.getRecentComments(limit, username);
        return new PageResponse<>(pageInfo);
    }


    /**
     * 功能描述：  查询本文章的评论列表
     *
     * @param currentPage
     * @param articleId
     * @author wangcanfeng
     * @time 2019/2/21 22:29
     * @since v1.0
     **/
    @GetMapping("/article/commentList")
    public BaseResponse<List<CommentVo>> getArticleComments(@RequestParam("currentPage") Integer currentPage,
                                                            @RequestParam("articleId") Integer articleId) {
        PageInfo<CommentVo> pageInfo = commentInfoService.getArticleComments(currentPage, CoreConstant.DEFAULT_PAGE_SIZE, articleId);
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
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.COMMENT,
            info = LogConstant.ActionInfo.DELETE_COMMENT)
    public BaseResponse deleteCommentById(@PathVariable("id") Integer id) {
        commentInfoService.deleteCommentById(id);
        // 文章内评论数减去1



        return BaseResponse.ok();
    }

    /**
     * 功能描述：  增加文章的评论
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/2/21 22:58
     * @since v1.0
     **/
    @PostMapping("/article/addComment")
    @OperationLog(action = LogConstant.ActionType.ADD, object = LogConstant.ActionObject.COMMENT,
            info = LogConstant.ActionInfo.ADD_COMMENT)
    public BaseResponse addCommentByArticleId(@RequestBody CommentReq req) {
        CommentInfo info = new CommentInfo();
        info.setArticleId(req.getArticleId());
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setUpdateTime(FunnyTimeUtils.nowUnix());
        info.setAuthorName(req.getAuthorName());
        info.setAuthorId(req.getAuthorId());
        info.setIp(RequestUtils.getRemoteIp());
        info.setText(req.getCommentText());
        info.setType(1);
        info.setIsRead(0);
        info.setParent(req.getParent());
        commentInfoService.addComment(info);

        //文章内评论数增加1

        return BaseResponse.ok();
    }

}
