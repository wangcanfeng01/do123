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

    /**
     * 功能描述：  根据slug查询文章内容
     *@author wangcanfeng
     *@time 2019/2/19 21:36
     *@since v1.0
     * @param slug
     **/
    ArticleInfoVo getArticleBySlug(String slug);

    /**
     * @param id
     * @param hits
     * @return void
     * @note 通过id更新点击率
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    void updateHitsById(Integer id,Integer hits);

    /**
     * @param id
     * @param stars
     * @return void
     * @note 根据id更新喜欢数
     * @author WCF
     * @time 2018/6/13 21:49
     * @since v1.0
     **/
    void updateStarsById(Integer id, Integer stars);
}
