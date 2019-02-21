package com.wcf.funny.blog.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/21
 * @function 评论请求内容
 **/
@Data
public class CommentReq {
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 评论人名
     */
    private String authorName;
    /**
     * 评论人id
     */
    private Integer authorId;
    /**
     * 评论内容
     */
    private String commentText;
    /**
     * 评论的父节点
     */
    private Integer parent;

}
