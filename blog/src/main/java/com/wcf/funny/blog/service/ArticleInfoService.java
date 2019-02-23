package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.vo.ArticleEditVo;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.blog.vo.req.ArticleQueryReq;

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
     * 功能描述：  简单的文章信息列表，只用于文章列表展示
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    PageInfo<ArticleSimpleVo> getSimpleArticlesByReq(ArticleQueryReq req);


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
     *
     * @param slug
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    ArticleInfoVo getArticleBySlug(String slug);


    /**
     * 功能描述：  根据slug查询文章内容
     *
     * @param slug
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    ArticleEditVo getArticleEditBySlug(String slug);

    /**
     * 功能描述：  根据id查询文章内容
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    ArticleInfoVo getArticleById(Integer id);

    /**
     * @param id
     * @param hits
     * @return void
     * @note 通过id更新点击率
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    void updateHitsById(Integer id, Integer hits);

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

    /**
     * 功能描述：根据id更新文章封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:21
     * @since v1.0
     **/
    void updateArticleCoverById(String cover, Integer id);

    /**
     * 功能描述：  根据id删除文章
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:48
     * @since v1.0
     **/
    void deleteArticleByIdFake(Integer id);

    /**
     * 功能描述：  创建文章信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 17:29
     * @since v1.0
     **/
    ArticleEditVo createNewArticle(ArticleInfo info);

    /**
     * 功能描述： 编辑文章信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/23 22:49
     * @since v1.0
     **/
    void modifyArticleInfo(ArticleInfo info);
}
