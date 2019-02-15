package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.ArticleSimple;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function 文章数据库请求映射
 **/
public interface ArticleInfoMapper {

    /**
     * @param slug
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据缩略名查询文章
     * @author WCF
     * @time 2018/6/13 21:45
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article WHERE title_simple=#{slug} and delete_flag=0")
    ArticleInfo getArticleInfoBySlug(String slug) throws Exception;

    /**
     * @param id
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据id寻找文章
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article WHERE id=#{id} and delete_flag=0")
    ArticleInfo getArticleInfoById(Integer id) throws Exception;

    /**
     * @note 搜索文章的简略信息列表,根据修改的时间倒序排列
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, modify_time as modifyTime, author, keywords, status," +
            " categories as category FROM info_article WHERE delete_flag=0 ORDER BY modify_time DESC")
    List<ArticleSimple> getAllArticleInfo() throws Exception;
}
