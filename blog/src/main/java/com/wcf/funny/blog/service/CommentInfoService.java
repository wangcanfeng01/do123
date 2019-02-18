package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.vo.CommentVo;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论信息接口
 **/
public interface CommentInfoService {
    /**
     * 功能描述：  获取评论记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/18 23:18
     * @since v1.0
     **/
    PageInfo<CommentVo> getCommentLogs(Integer currentPage, Integer pageSize);
}
