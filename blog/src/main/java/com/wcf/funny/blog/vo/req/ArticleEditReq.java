package com.wcf.funny.blog.vo.req;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文章编辑请求
 **/
@Data
public class ArticleEditReq {
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String text;
    /**
     * 关键字列表
     */
    private List<String> keywords;
    /**
     * 是否允许评论
     */
    private Boolean allowComment;
    /**
     * 是否可见
     */
    private Boolean allowSee;
    /**
     * 分类
     */
    private String category;
}
