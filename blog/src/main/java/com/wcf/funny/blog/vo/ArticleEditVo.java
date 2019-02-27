package com.wcf.funny.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文章编辑界面的视图信息
 **/
@Data
public class ArticleEditVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;

    /**
     * uuid类型的标题
     */
    private String slug;

    /**
     * 作者名称
     */
    private String author;
    /**
     * 文章内容
     */
    private String text;
    /**
     * 关键字列表
     */
    private List<String> keywords;

    /**
     * 专题名
     */
    private String category;
    /**
     * 是否允许评论
     */
    private Boolean allowComment;
    /**
     * 是否公开
     */
    private Boolean allowSee;
}
