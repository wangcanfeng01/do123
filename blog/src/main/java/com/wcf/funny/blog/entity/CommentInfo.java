package com.wcf.funny.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论内容实体信息
 **/
@Data
public class CommentInfo implements Serializable{
    private static final long serialVersionUID = 2L;
    /**
     * 评论id
     */
    private Integer id;
    /**
     * 评论生成时的GMT unix时间戳
     */
    private Integer createTime;
    /**
     * 评论修改时的unix时间戳
     */
    private Integer updateTime;
    /**
     * 评论人
     */
    private String authorName;
    /**
     * 作者id
     */
    private Integer authorId;
    /**
     * 所属文章编号
     */
    private Integer articleId;
    /**
     * 评论者ip地址
     */
    private String ip;
    /**
     * 评论具体内容
     */
    private String text;
    /**
     * 类型，是回复还是初始评论
     */
    private String type;
    /**
     * 是否已经阅读
     */
    private String isRead;
    /**
     * 父节点的id
     */
    private Integer parent;
}
