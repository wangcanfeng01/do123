package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.ArticleSimple;
import com.wcf.funny.blog.mapper.provider.ArticleInfoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/15
 * @function 文章数据库请求映射
 **/
@Mapper
public interface ArticleInfoMapper {

    /**
     * @param slug
     * @return com.wcf.hellohome.read.model.ArticleInfo
     * @note 根据缩略名查询文章
     * @author WCF
     * @time 2018/6/13 21:45
     * @since v1.0
     **/
    @Select("SELECT a.id, a.title, a.title_simple as slug, a.cover, a.create_time as createTime, a.modify_time as modifyTime," +
            " a.text, a.words, a.author, a.keywords, a.status, a.categories as category, a.hits, a.stars, a.comments_num as commentsNum," +
            " a.allow_comment as allowComment, a.allow_see as allowSee, b.face_path as authorFace " +
            " FROM info_article as a LEFT JOIN info_user as b ON a.author=b.name WHERE title_simple=#{slug} and delete_flag=0")
    ArticleInfo getArticleInfoBySlug(String slug);

    /**
     * 功能描述：查询跟输入的标题类似的文章信息
     *
     * @param title
     * @author wangcanfeng
     * @time 2019/4/22 22:08
     * @since v1.0
     **/
    @Select("SELECT id, title, cover, text, author, keywords, categories as category, modify_time as modifyTime " +
            " FROM info_article WHERE title like '%${title}%' and delete_flag=0 and allow_see='public'")
    List<ArticleInfo> getArticleLikeTitle(@Param("title") String title);

    /**
     * @param id
     * @return com.wcf.hellohome.read.model.ArticleInfo
     * @note 根据id寻找文章
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT a.id, a.title, a.title_simple as slug, a.cover, a.create_time as createTime, a.modify_time as modifyTime," +
            " a.text, a.words, a.author, a.keywords, a.status, a.categories as category, a.hits, a.stars, a.comments_num as commentsNum," +
            " a.allow_comment as allowComment, a.allow_see as allowSee, b.face_path as authorFace " +
            " FROM info_article as a LEFT JOIN info_user as b ON a.author=b.name WHERE a.id=#{id} and delete_flag=0")
    ArticleInfo getArticleInfoById(Integer id);

    /**
     * @note 搜索文章的简略信息列表, 根据修改的时间倒序排列
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, modify_time as modifyTime, author, keywords, status," +
            " categories as category FROM info_article WHERE delete_flag=0 ORDER BY modify_time DESC")
    List<ArticleSimple> getArticleInfoSimple();

    /**
     * @note 搜索文章的简略信息列表, 根据修改的时间倒序排列
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @SelectProvider(type = ArticleInfoProvider.class, method = "getArticleInfoSimpleByParamsSQL")
    List<ArticleSimple> getArticleInfoSimpleByParams(@Param("category") String category, @Param("title") String title);

    /**
     * @note 搜索文章的信息列表, 根据修改的时间倒序排列，不查询文章内容信息
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, create_time as createTime, modify_time as modifyTime," +
            " author, keywords, status, categories as category, hits, stars, comments_num as commentsNum," +
            " allow_comment as allowComment, allow_see as allowSee" +
            " FROM info_article WHERE delete_flag=0 ORDER BY modify_time DESC")
    List<ArticleInfo> getArticleInfoWithoutContent();

    /**
     * @note 搜索近期文章的信息列表, 根据修改的时间倒序排列
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as slug, cover, create_time as createTime, modify_time as modifyTime," +
            " text, author, keywords, status, categories as category, hits, stars, comments_num as commentsNum," +
            " allow_comment as allowComment, allow_see as allowSee" +
            " FROM info_article WHERE delete_flag=0 ORDER BY modify_time DESC limit 10")
    List<ArticleInfo> getRecentArticles();

    /**
     * @param id
     * @param hits
     * @return void
     * @note 通过id更新点击率
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    @Update("UPDATE info_article SET hits = #{hits} WHERE id = #{id}")
    void updateHitsById(@Param("id") Integer id, @Param("hits") Integer hits);

    /**
     * @param id
     * @param stars
     * @return void
     * @note 根据id更新喜欢数
     * @author WCF
     * @time 2018/6/13 21:49
     * @since v1.0
     **/
    @Update("UPDATE info_article SET stars = #{stars} WHERE id = #{id}")
    void updateStarsById(@Param("id") Integer id, @Param("stars") Integer stars);

    /**
     * @param id
     * @param cover
     * @return void
     * @note 根据id更新文章的封面信息
     * @author WCF
     * @time 2018/6/13 21:49
     * @since v1.0
     **/
    @Update("UPDATE info_article SET cover = #{cover} WHERE id = #{id}")
    void updateCoverById(@Param("id") Integer id, @Param("cover") String cover);


    /**
     * @param id
     * @return void
     * @note 根据id删除文章(伪删除)
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    @Update("UPDATE info_article SET delete_flag = 1 WHERE id = #{id}")
    void deleteArticleByIdFake(@Param("id") Integer id);


    /**
     * @param info
     * @return int
     * @note 插入新的文章，主内容为空，只有骨架
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    @Insert("INSERT INTO info_article(title, title_simple, cover, create_time, modify_time, text, words, author, keywords,status," +
            " categories, hits,stars, comments_num,allow_comment, allow_see, delete_flag) VALUES(#{title}, #{slug}," +
            " #{cover}, #{createTime}, #{modifyTime},#{text},#{words},#{author},#{keywords},#{status}," +
            "#{category},#{hits},#{stars},#{commentsNum},#{allowComment},#{allowSee},#{deleteFlag});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNewArticle(ArticleInfo info);

    /**
     * 功能描述：编辑文章信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 22:53
     * @since v1.0
     **/
    @UpdateProvider(type = ArticleInfoProvider.class, method = "getModifyArticleSQL")
    void modifyArticleInfoById(ArticleInfo info);


    /**
     * 功能描述：减少文章的评论的统计值
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/25 22:59
     * @since v1.0
     **/
    @Update("UPDATE info_article SET comments_num = comments_num-1 WHERE id = #{id}")
    void reduceCommentNum(Integer id);

    /**
     * 功能描述：增加评论的统计值
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/25 22:59
     * @since v1.0
     **/
    @Update("UPDATE info_article SET comments_num = comments_num+1 WHERE id = #{id}")
    void increaseCommentNum(Integer id);
}
