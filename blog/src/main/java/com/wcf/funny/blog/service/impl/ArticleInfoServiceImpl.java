package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.ArticleSimple;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.mapper.ArticleInfoMapper;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章信息服务接口实现
 **/
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    /**
     * 功能描述：  简单的文章信息列表，只用于文章列表展示
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    @Override
    public PageInfo<ArticleSimpleVo> getSimpleArticles(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<ArticleSimple> articleSimples = articleInfoMapper.getArticleInfoSimple();
            // 将文章列表转成视图分页信息
            return convertArticleSimplePage(articleSimples);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }


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
    @Override
    public PageInfo<ArticleInfoVo> getArticles(Integer currentPage, Integer pageSize, boolean withContent) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<ArticleInfo> articles = articleInfoMapper.getArticleInfoWithoutContent();
            // 将文章列表转成视图分页信息
            return convertArticlePage(articles, withContent);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询近期文章信息
     *
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    @Override
    public PageInfo<ArticleInfoVo> getRecentList() {
        try {
            List<ArticleInfo> articles = articleInfoMapper.getRecentArticles();
            // 将文章列表转成视图分页信息
            return convertArticlePage(articles, true);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  转换简单的文章视图列表信息
     *
     * @param simplePageInfo
     * @author wangcanfeng
     * @time 2019/2/17 14:11
     * @since v1.0
     **/
    private PageInfo<ArticleSimpleVo> convertArticleSimplePage(List<ArticleSimple> simplePageInfo) {
        PageInfo<ArticleSimple> pageInfo = new PageInfo<>(simplePageInfo);
        List<ArticleSimpleVo> vos = new ArrayList<>();
        // 要显示的视图信息
        PageInfo<ArticleSimpleVo> articleSimplePage = new PageInfo<>();
        if (ObjectUtils.isEmpty(pageInfo.getList())) {
            return articleSimplePage;
        } else {
            pageInfo.getList().forEach(info -> vos.add(convertSimple(info)));
            articleSimplePage.setTotal(pageInfo.getTotal());
            articleSimplePage.setList(vos);
            return articleSimplePage;
        }
    }

    /**
     * 功能描述：  转换简单的文章信息
     *
     * @param simple
     * @author wangcanfeng
     * @time 2019/2/17 14:11
     * @since v1.0
     **/
    private ArticleSimpleVo convertSimple(ArticleSimple simple) {
        ArticleSimpleVo vo = new ArticleSimpleVo();
        vo.setId(simple.getId());
        vo.setTitle(simple.getTitle());
        vo.setSlug(simple.getSlug());
        vo.setCover(simple.getCover());
        vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(simple.getModifyTime()));
        vo.setAuthor(simple.getAuthor());
        vo.setKeywords(simple.getKeywords());
        vo.setCategory(simple.getCategory());
        return vo;
    }

    /**
     * 功能描述：  转换文章视图列表信息
     *
     * @param simplePageInfo
     * @author wangcanfeng
     * @time 2019/2/17 14:11
     * @since v1.0
     **/
    private PageInfo<ArticleInfoVo> convertArticlePage(List<ArticleInfo> simplePageInfo, boolean withContent) {
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(simplePageInfo);
        List<ArticleInfoVo> vos = new ArrayList<>();
        // 要显示的视图信息
        PageInfo<ArticleInfoVo> articlePage = new PageInfo<>();
        if (ObjectUtils.isEmpty(pageInfo.getList())) {
            return articlePage;
        } else {
            pageInfo.getList().forEach(info -> vos.add(convert(info, withContent)));
            articlePage.setTotal(pageInfo.getTotal());
            articlePage.setList(vos);
            return articlePage;
        }
    }

    /**
     * 功能描述：  转换文章信息
     *
     * @param articleInfo 文章信息
     * @param withContent 是否包含内容信息
     * @author wangcanfeng
     * @time 2019/2/17 14:11
     * @since v1.0
     **/
    private ArticleInfoVo convert(ArticleInfo articleInfo, boolean withContent) {
        ArticleInfoVo vo = new ArticleInfoVo();
        vo.setId(articleInfo.getId());
        vo.setTitle(articleInfo.getTitle());
        vo.setSlug(articleInfo.getSlug());
        vo.setCover(articleInfo.getCover());
        vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(articleInfo.getModifyTime()));
        vo.setAuthor(articleInfo.getAuthor());
        vo.setKeywords(articleInfo.getKeywords());
        vo.setCategory(articleInfo.getCategory());
        if (withContent) {
            String text = articleInfo.getText();
            if (ObjectUtils.isEmpty(text)) {
                vo.setText("");
            } else {
                // 文章内容过长的时候进行截取
                if (text.length() >= 150) {
                    text = text.substring(0, 150);
                }
                text+="......";
                vo.setText(text);
            }
        }
        vo.setAllowComment(articleInfo.getAllowComment());
        vo.setAllowSee(articleInfo.getAllowSee());
        vo.setCommentsNum(articleInfo.getCommentsNum());
        vo.setHits(articleInfo.getHits());
        vo.setCreateTime(articleInfo.getCreateTime());
        vo.setStars(articleInfo.getStars());
        vo.setStatus(articleInfo.getStatus());
        return vo;
    }
}