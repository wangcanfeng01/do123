package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.ArticlePrivateStatus;
import com.wcf.funny.blog.constant.ArticlePublishStatus;
import com.wcf.funny.blog.constant.CommentEnableStatus;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.ArticleSimple;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.mapper.ArticleInfoMapper;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.vo.ArticleEditVo;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.blog.vo.req.ArticleQueryReq;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.ArticleUtils;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
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
     * 功能描述：  根据参数查找，简单的文章信息列表，只用于文章列表展示
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/2/17 13:22
     * @since v1.0
     **/
    @Override
    public PageInfo<ArticleSimpleVo> getSimpleArticlesByReq(ArticleQueryReq req) {
        try {
            PageHelper.startPage(req.getCurrentPage(), req.getPageSize());
            List<ArticleSimple> articleSimples = articleInfoMapper.getArticleInfoSimpleByParams(req.getCategory(), req.getTitle());
            // 将文章列表转成视图分页信息
            return convertArticleSimplePage(articleSimples);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  较为详细的文章信息列表，只用于文章列表展示，一般用于文章管理列表展示
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
     * 功能描述：  根据slug查询文章内容
     *
     * @param slug
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    @Override
    public ArticleInfoVo getArticleBySlug(String slug) {
        try {
            ArticleInfo article = articleInfoMapper.getArticleInfoBySlug(slug);
            if (ObjectUtils.isEmpty(article)) {
                return null;
            }
            // 将文章列表转成视图分页信息
            return convert(article, true, false);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据slug查询文章内容
     *
     * @param slug
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    @Override
    public ArticleEditVo getArticleEditBySlug(String slug) {
        try {
            ArticleInfo article = articleInfoMapper.getArticleInfoBySlug(slug);
            if (ObjectUtils.isEmpty(article)) {
                return null;
            }
            // 将文章列表转成视图分页信息
            return convertToEditVo(article);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id查询文章内容
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/19 21:36
     * @since v1.0
     **/
    @Override
    public ArticleInfoVo getArticleById(Integer id) {
        try {
            ArticleInfo article = articleInfoMapper.getArticleInfoById(id);
            if (ObjectUtils.isEmpty(article)) {
                return null;
            }
            // 将文章列表转成视图分页信息,博客内容简化为展示缩略信息
            return convert(article, true, false);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * @param id
     * @param hits
     * @return void
     * @note 通过id更新点击率
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    @Override
    public void updateHitsById(Integer id, Integer hits) {
        try {
            articleInfoMapper.updateHitsById(id, hits);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.UPDATE_ARTICLE_ERROR, e);
        }
    }

    /**
     * @param id
     * @param stars
     * @return void
     * @note 根据id更新喜欢数
     * @author WCF
     * @time 2018/6/13 21:49
     * @since v1.0
     **/
    @Override
    public void updateStarsById(Integer id, Integer stars) {
        try {
            articleInfoMapper.updateStarsById(id, stars);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.UPDATE_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：根据id更新文章封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:21
     * @since v1.0
     **/
    @Override
    public void updateArticleCoverById(String cover, Integer id) {
        try {
            articleInfoMapper.updateCoverById(id, cover);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.UPDATE_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id删除文章
     *
     * @author wangcanfeng
     * @time 2019/2/23 15:48
     * @since v1.0
     **/
    @Override
    public void deleteArticleByIdFake(Integer id) {
        try {
            articleInfoMapper.deleteArticleByIdFake(id);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.UPDATE_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  创建文章信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 17:29
     * @since v1.0
     **/
    @Override
    public ArticleEditVo createNewArticle(ArticleInfo info) {
        try {
//            articleInfoMapper.insertNewArticle(info);
            return convertToEditVo(info);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.INSERT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述： 编辑文章信息
     *
     * @param info@author wangcanfeng
     * @time 2019/2/23 22:49
     * @since v1.0
     **/
    @Override
    public void modifyArticleInfo(ArticleInfo info) {
        try {
            articleInfoMapper.modifyArticleInfoById(info);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.UPDATE_ARTICLE_ERROR, e);
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
            pageInfo.getList().forEach(info -> vos.add(convert(info, withContent, true)));
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
    private ArticleInfoVo convert(ArticleInfo articleInfo, boolean withContent, boolean simple) {
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
                // 文章内容只需要简单展示，且过长的时候进行截取
                if (simple && text.length() >= 150) {
                    text = text.substring(0, 150);
                    text += "......";
                } else {
                    text = ArticleUtils.mdToHtml(text);
                }
                vo.setText(text);
            }
        }
        // 设置是否允许评论
        if (CommentEnableStatus.ALLOW_COMMENT.getStatus().equals(articleInfo.getAllowComment())) {
            vo.setAllowComment("允许");
        } else {
            vo.setAllowComment("不允许");
        }
        // 是否公开可见
        if (ArticlePrivateStatus.PUBLIC.getStatus().equals(articleInfo.getAllowSee())) {
            vo.setAllowSee("公开");
        } else {
            vo.setAllowSee("仅自己可见");
        }
        vo.setCommentsNum(articleInfo.getCommentsNum());
        vo.setHits(articleInfo.getHits());
        vo.setCreateTime(articleInfo.getCreateTime());
        vo.setStars(articleInfo.getStars());
        if (ArticlePublishStatus.PUBLISH.getStatus().equals(articleInfo.getStatus())) {
            vo.setPublishStatus("已发布");
        } else {
            vo.setPublishStatus("草稿");
        }
        vo.setWordCount(articleInfo.getWords());
        vo.setAuthorFace(articleInfo.getAuthorFace());
        return vo;
    }

    private ArticleEditVo convertToEditVo(ArticleInfo info){
        ArticleEditVo vo=new ArticleEditVo();
        vo.setId(info.getId());
        vo.setText(info.getText());
        vo.setTitle(info.getTitle());
        vo.setAuthor(info.getAuthor());
        vo.setCategory(info.getCategory());
        // 设置关键字列表
        if(ObjectUtils.isEmpty(info.getKeywords())){
            vo.setKeywords(Collections.emptyList());
        }else {
            vo.setKeywords(ConvertIdUtils.getStringList(info.getKeywords()));
        }
        // 设置评论状态
        if(CommentEnableStatus.ALLOW_COMMENT.getStatus().equals(info.getAllowComment())){
            vo.setAllowComment(true);
        }else {
            vo.setAllowComment(false);
        }
        // 设置公开状态
        if(ArticlePrivateStatus.PUBLIC.getStatus().equals(info.getAllowSee())){
            vo.setAllowSee(true);
        }else {
            vo.setAllowSee(false);
        }
        return vo;
    }
}
