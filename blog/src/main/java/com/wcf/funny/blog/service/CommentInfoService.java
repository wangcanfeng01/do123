package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.vo.CommentVo;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息接口
 **/
public interface CommentInfoService {
    /**
     * 功能描述：  获取评论记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    PageInfo<CommentVo> getCommentLogs(Integer currentPage, Integer pageSize);

    /**
     * 功能描述：  获取近期的几条评论
     *
     * @param limit    查询条数
     * @param username 作者名称
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    PageInfo<CommentVo> getRecentComments(Integer limit, String username);

    /**
     * 功能描述：  根据id删除评论
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/19 20:05
     * @since v1.0
     **/
    void deleteCommentById(Integer id);

    /**
     * 功能描述：  获取文章的评论
     *
     * @param currentPage 评论页码
     * @param pageSize 单页大小
     * @param articleId 文章id
     * @author wangcanfeng
     * @time 2019/2/20 23:20
     * @since v1.0
     **/
    PageInfo<CommentVo> getArticleComments(Integer currentPage, Integer pageSize, Integer articleId);

}
