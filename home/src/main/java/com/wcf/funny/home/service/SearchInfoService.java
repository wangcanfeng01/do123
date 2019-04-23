package com.wcf.funny.home.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.home.vo.SearchInfoVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/21
 * @function 查询服务接口
 **/
public interface SearchInfoService {

    /**
     * 功能描述：根据关键字查询视频信息，视频信息来自互联网
     *
     * @param keyword 关键字
     * @author wangcanfeng
     * @time 2019/4/21 21:11
     * @since v1.0
     **/
    List<SearchInfoVo> searchVideos(String keyword);

    /**
     * 功能描述：根据关键字查询博客信息
     *
     * @param keyword 关键字
     * @author wangcanfeng
     * @time 2019/4/21 21:11
     * @since v1.0
     **/
    List<SearchInfoVo> searchBlogs(String keyword);

    /**
     * 功能描述：  根据关键字查询信息
     *
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @param type
     * @author wangcanfeng
     * @time 2019/4/21 21:18
     * @since v1.0
     **/
    PageInfo<SearchInfoVo> searchInfo(String keyword, Integer currentPage, Integer pageSize,String type);
}
