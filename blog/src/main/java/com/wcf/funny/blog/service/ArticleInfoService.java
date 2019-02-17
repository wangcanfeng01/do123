package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章信息服务接口
 **/
public interface ArticleInfoService {

    /**
     * 功能描述：  简单的文章信息列表，只用于文章列表展示
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    PageInfo<ArticleSimpleVo> getSimpleArticles(Integer currentPage, Integer pageSize);


    /**
     * 功能描述：  文章信息列表，只用于文章列表展示
     *
     * @param currentPage
     * @param pageSize
     * @param withContent 是否包含文章内容
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    PageInfo<ArticleInfoVo> getArticles(Integer currentPage, Integer pageSize, boolean withContent);

    /**
     * 功能描述：  查询近期文章信息
     *
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    PageInfo<ArticleInfoVo> getRecentList();
}
