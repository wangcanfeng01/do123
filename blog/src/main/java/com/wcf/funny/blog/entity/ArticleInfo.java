package com.wcf.funny.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function 文章信息
 **/
@Data
public class ArticleInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 文章表主键
     */
    private Integer id;

    /**
     * 内容标题
     */
    private String title;
    /**
     * 缩略名，用于当成地址访问,对标题进行加密，防止外链
     */
    private String slug;

    /**
     * 文章封面链接
     */
    private String cover;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 修改时间
     */
    private Integer modifyTime;
    /**
     * 具体内容信息
     */
    private String text;
    /**
     * 作者名字
     */
    private String author;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 内容状态
     */
    private String status;
    /**
     * 文章类型
     */
    private String categories;
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
     * @return
     */
    private Short deleteFlag;
}
