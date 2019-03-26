package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.blog.vo.BlogIndexVo;
import com.wcf.funny.core.entity.NameAndCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计mapper
 **/
@Mapper
public interface ArticleStatisticMapper {

    /**
     * 功能描述：文章统计信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Select("select count(1) as articles,SUM(words) as words, SUM(hits) as hits," +
            " SUM(comments_num) as comments from info_article")
    ArticleStatisticVo getArticleStatistic();

    /**
     * 功能描述：文章总赞数
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Select("select SUM(stars) as stars,SUM(words) as words, SUM(hits) as hits," +
            " SUM(comments_num) as comments from info_article")
    BlogIndexVo getBlogIndex();

    /**
     * 功能描述：获取最受欢迎的文章
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Select("select title,stars from info_article order by stars desc limit #{limit}")
    List<ArticleInfo> getPopularArticles(Integer limit);


    /**
     * 功能描述：查询文章内图片总数
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Select("select count(1) from info_upload_pic where pic_type=2")
    Long getPictureCount();

    /**
     * 功能描述：  查询近期博客发表情况
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/27 0:34
     * @since v1.0
     **/
    @Select("SELECT MONTH(FROM_UNIXTIME(modify_time)) as name, count(1) as count " +
            "FROM info_article  WHERE modify_time > #{modifyTime} " +
            " GROUP BY MONTH(FROM_UNIXTIME(modify_time)) ORDER BY modify_time DESC")
    List<NameAndCount> getRecentArticleCount(Integer modifyTime);
}
