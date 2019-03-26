package com.wcf.funny.blog.service;

import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.blog.vo.BlogIndexVo;
import com.wcf.funny.core.entity.NameAndCount;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计接口
 **/
public interface ArticleStatisticService {

    /**
     * 功能描述：文章统计信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    ArticleStatisticVo getArticleStatistic();

    /**
     * 功能描述：文章指数信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    BlogIndexVo getBlogIndex();

    /**
     * 功能描述： 最受欢迎的博客
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 23:44
     * @since v1.0
     **/
    List<ArticleInfo> getPopularArticles();

    /**
     * 功能描述： 查询所有文章内的图片总数
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 23:50
     * @since v1.0
     **/
    Long getArticlePictures();

    /**
     * 功能描述：  查询近期博客发表情况
     *
     * @param month 往前几个月
     * @author wangcanfeng
     * @time 2019/3/27 0:34
     * @since v1.0
     **/
    List<NameAndCount> getRecentArticleCount(Integer month);
}
