package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/26
 * @function 博客指数
 **/
@Data
public class BlogIndexVo {
    /**
     * 文章总赞数
     */
    private Long stars;

    /**
     * 文章总点击数
     */
    private Long hits;
    /**
     * 文章总评论数
     */
    private Long comments;
    /**
     * 文章总字数
     */
    private Long words;

    /**
     * 文章内图片总数
     */
    private Long pictures;
}
