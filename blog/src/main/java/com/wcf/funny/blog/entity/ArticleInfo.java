package com.wcf.funny.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function 文章信息
 **/
@Data
public class ArticleInfo extends ArticleSimple implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 具体内容信息
     */
    private String text;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 文章被点击次数
     */
    private Integer hits;
    /**
     * 点赞数目
     */
    private Integer stars;
    /**
     * 文章被评论次数
     */
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;
    /**
     * 是否允许可见
     */
    private String allowSee;

    /**
     * 文章删除标志
     *
     * @return
     */
    private Short deleteFlag;
}
