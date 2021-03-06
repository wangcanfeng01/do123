package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.CommentInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息数据库操作
 **/
@Mapper
public interface CommentInfoMapper {
    /**
     * 功能描述：  获取评论记录
     *
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    @Select("SELECT a.id, a.article_id as articleId, a.create_time as createTime, a.update_time as updateTime, " +
            "a.author_name as authorName, a.author_id as authorId, a.ip, a.text, a.type, a.is_read as isRead," +
            " a.parent, b.face_path as authorFace, c.title as articleTitle FROM info_comment as a LEFT JOIN info_user " +
            "as b ON a.author_id=b.id  LEFT JOIN info_article as c ON a.article_id=c.id order by a.update_time desc")
    List<CommentInfo> getCommentLogs();

    /** 查找当前人员最近的几条评论
     *@note
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param limit
     * @param username
     **/
    @Select("SELECT a.id, a.article_id as articleId, a.create_time as createTime, a.update_time as updateTime, " +
            "a.author_name as authorName, a.author_id as authorId, a.ip, a.text, a.type, a.is_read as isRead," +
            " a.parent, b.face_path as authorFace, c.title as articleTitle FROM info_comment as a LEFT JOIN" +
            " info_user as b ON a.author_id=b.id LEFT JOIN info_article as c ON a.article_id=c.id" +
            " where a.author_name=#{username} order by a.update_time desc limit #{limit}")
    List<CommentInfo> getRecentComments(@Param("limit") Integer limit,@Param("username") String username) throws Exception;

    /**
     *@note 统计总评论条数
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param
     *@return long
     **/
    @Select("SELECT count(id) FROM info_comment;")
    long countComments()throws Exception;

    /**
     *@note 通过id删除评论
     *@author WCF
     *@time 2018/6/13 22:20
     *@since v1.0
     * @param id
     *@return int
     **/
    @Delete("DELETE FROM info_comment WHERE id = #{id}")
    int deleteById(Integer id)throws Exception;

    /**
     * 功能描述：  获取当前文章对应的评论列表
     *@author wangcanfeng
     *@time 2019/2/20 23:22
     *@since v1.0
     * @param articleId
     **/
    @Select("SELECT a.id, a.create_time as createTime, a.update_time as updateTime, " +
            "a.author_name as authorName, a.author_id as authorId, a.text, a.type, a.is_read as isRead," +
            " a.parent, b.face_path as authorFace FROM info_comment as a LEFT JOIN" +
            " info_user as b ON a.author_id=b.id where a.article_id=#{articleId} order by a.update_time desc")
    List<CommentInfo> getArticleComments(Integer articleId);

    /**
     *@note 插入新的评论
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param info
     *@return int
     **/
    @Insert("insert into info_comment(article_id,create_time, update_time, author_name, author_id, ip, text, type, is_read, parent)" +
            " values(#{articleId},#{createTime},#{updateTime},#{authorName},#{authorId},#{ip},#{text},#{type},#{isRead},#{parent})")
    int insertComment(CommentInfo info);
}
