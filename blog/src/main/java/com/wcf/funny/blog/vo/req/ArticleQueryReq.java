package com.wcf.funny.blog.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 文章查询的请求参数
 **/
@Data
public class ArticleQueryReq {
    /**
     * 文章标题的名称
     */
    private String title;
    /**
     * 文章的分类
     */
    private String category;
    /**
     * 单页大小
     */
    private Integer pageSize;
    /**
     * 当前页码
     */
    private Integer currentPage;
}
