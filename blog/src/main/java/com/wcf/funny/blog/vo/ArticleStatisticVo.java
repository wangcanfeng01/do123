package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计信息
 **/
@Data
public class ArticleStatisticVo {
    /**
     * 文章数目
     */
    private Long articles;

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
}
