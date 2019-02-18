package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT id, article_id as articleId, create_time as createTime, update_time as updateTime, author_name as authorName," +
            " author_id as authorId, ip, text, type, is_read, parent FROM info_comment order by update_time")
    List<CommentInfo> getCommentLogs();
}
