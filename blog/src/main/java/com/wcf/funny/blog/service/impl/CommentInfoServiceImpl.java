package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.ArticleConstant;
import com.wcf.funny.blog.entity.CommentInfo;
import com.wcf.funny.blog.exception.errorcode.CommentErrorCode;
import com.wcf.funny.blog.mapper.CommentInfoMapper;
import com.wcf.funny.blog.service.CommentInfoService;
import com.wcf.funny.blog.vo.CommentVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息接口实现
 **/
@Service
public class CommentInfoServiceImpl implements CommentInfoService {
    @Autowired
    private CommentInfoMapper commentMapper;

    /**
     * 功能描述：  获取评论记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    @Override
    public PageInfo<CommentVo> getCommentLogs(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<CommentInfo> commentInfos = commentMapper.getCommentLogs();
            return convertPageInfo(commentInfos);
        } catch (Exception e) {
            throw new PgSqlException(CommentErrorCode.SELECT_COMMENT_ERROR, e);
        }
    }

    /**
     * 功能描述：  获取当前人员近期的几条评论
     *
     * @param limit    查询条数
     * @param username 作者名称
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    @Override
    public PageInfo<CommentVo> getRecentComments(Integer limit, String username) {
        try {
            List<CommentInfo> commentInfos = commentMapper.getRecentComments(limit, username);
            return convertPageInfo(commentInfos);
        } catch (Exception e) {
            throw new PgSqlException(CommentErrorCode.SELECT_COMMENT_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id删除评论
     *
     * @author wangcanfeng
     * @time 2019/2/19 20:05
     * @since v1.0
     **/
    @Override
    public void deleteCommentById(Integer id) {
        try {
            commentMapper.deleteById(id);
        } catch (Exception e) {
            throw new PgSqlException(CommentErrorCode.DELETE_COMMENT_ERROR, e);
        }
    }

    /**
     * 功能描述：  获取文章的评论
     *
     * @param currentPage 评论页码
     * @param pageSize    单页大小
     * @param articleId   文章id
     * @author wangcanfeng
     * @time 2019/2/20 23:20
     * @since v1.0
     **/
    @Override
    public PageInfo<CommentVo> getArticleComments(Integer currentPage, Integer pageSize, Integer articleId) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<CommentInfo> commentInfos = commentMapper.getArticleComments(articleId);
            return convertPageInfo(commentInfos);
        } catch (Exception e) {
            throw new PgSqlException(CommentErrorCode.SELECT_COMMENT_ERROR, e);
        }
    }


    /**
     * 功能描述：  给文章的写评论
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/21 22:34
     * @since v1.0
     **/
    @Override
    public void addComment(CommentInfo info) {
        try {
            commentMapper.insertComment(info);
        } catch (Exception e) {
            throw new PgSqlException(CommentErrorCode.INSERT_COMMENT_ERROR, e);
        }
    }

    /**
     * 功能描述：  将数据库查询结果转换成视图信息
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/2/18 23:48
     * @since v1.0
     **/
    private PageInfo<CommentVo> convertPageInfo(List<CommentInfo> list) {
        PageInfo<CommentInfo> pageInfo = new PageInfo<>(list);
        // 要返回的信息
        PageInfo<CommentVo> voPage = new PageInfo<>();
        if (ObjectUtils.isEmpty(list)) {
            return voPage;
        } else {
            List<CommentVo> vList = new LinkedList<>();
            list.forEach(commentInfo -> {
                CommentVo vo = new CommentVo();
                vo.setId(commentInfo.getId());
                vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(commentInfo.getCreateTime()));
                vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(commentInfo.getUpdateTime()));
                vo.setAuthorName(commentInfo.getAuthorName());
                vo.setAuthorId(commentInfo.getAuthorId());
                vo.setArticleId(commentInfo.getArticleId());
                vo.setIp(commentInfo.getIp());
                vo.setText(commentInfo.getText());
                if (!ObjectUtils.isEmpty(commentInfo.getType()) &&
                        commentInfo.getType().equals(ArticleConstant.TYPE_COMMENT)) {
                    vo.setType("评论");
                } else {
                    vo.setType("回复");
                }
                vo.setArticleTitle(commentInfo.getArticleTitle());
                vo.setParent(commentInfo.getParent());
                vo.setAuthorFace(commentInfo.getAuthorFace());
                vList.add(vo);
            });
            voPage.setTotal(pageInfo.getTotal());
            voPage.setList(vList);
        }
        return voPage;
    }
}
