package com.wcf.funny.blog.vo;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function
 **/
public class ArticleSimpleVo {
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
     * 修改时间
     */
    private String modifyTime;

    /**
     * 作者名字
     */
    private String author;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 文章类型
     */
    private String category;
}
