package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.vo.ArticleStatisticVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}
