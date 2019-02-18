package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.CommentInfo;
import com.wcf.funny.blog.exception.errorcode.CommentErrorCode;
import com.wcf.funny.blog.mapper.CommentInfoMapper;
import com.wcf.funny.blog.service.CommentInfoService;
import com.wcf.funny.blog.vo.CommentVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息接口实现
 **/
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
            throw new PgSqlException(CommentErrorCode.SELECT_KEYWORD_ERROR, e);
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
                vo.setType(commentInfo.getType());
                vo.setParent(commentInfo.getParent());
            });
        }

        return voPage;
    }
}
