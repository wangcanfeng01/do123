package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function  文章信息视图
 **/
@Data
public class ArticleInfoVo extends ArticleSimpleVo{
    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 具体内容信息
     */
    private String text;

    /**
     * 内容状态,是草稿还是已发布
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

}
