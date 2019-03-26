package com.wcf.funny.blog.service.impl;

import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.mapper.ArticleStatisticMapper;
import com.wcf.funny.blog.service.ArticleStatisticService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.blog.vo.BlogIndexVo;
import com.wcf.funny.core.entity.NameAndCount;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计接口实现类
 **/
@Service
public class ArticleStatisticServiceImpl implements ArticleStatisticService {

    @Autowired
    private ArticleStatisticMapper articleStatisticMapper;


    /**
     * 功能描述：文章统计信息
     *
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Override
    public ArticleStatisticVo getArticleStatistic() {
        try {
            return articleStatisticMapper.getArticleStatistic();
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：文章指数信息
     *
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Override
    public BlogIndexVo getBlogIndex() {
        try {
            return articleStatisticMapper.getBlogIndex();
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述： 最受欢迎的博客
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 23:44
     * @since v1.0
     **/
    @Override
    public List<ArticleInfo> getPopularArticles() {
        try {
            Integer limit = 7;
            return articleStatisticMapper.getPopularArticles(limit);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述： 查询所有文章内的图片总数
     *
     * @author wangcanfeng
     * @time 2019/3/26 23:50
     * @since v1.0
     **/
    @Override
    public Long getArticlePictures() {
        try {
            return articleStatisticMapper.getPictureCount();
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询近期博客发表情况
     *
     * @param month 往前几个月
     * @author wangcanfeng
     * @time 2019/3/27 0:34
     * @since v1.0
     **/
    @Override
    public List<NameAndCount> getRecentArticleCount(Integer month) {
        try {
            int time = FunnyTimeUtils.toUnixTime(LocalDateTime.now().minusMonths(month));
            return articleStatisticMapper.getRecentArticleCount(time);
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }
}
