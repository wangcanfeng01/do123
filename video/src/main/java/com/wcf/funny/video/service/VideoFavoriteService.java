package com.wcf.funny.video.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.video.entity.VideoFavoriteInfo;
import com.wcf.funny.video.vo.VideoInfoVo;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function 视频收藏夹
 **/
public interface VideoFavoriteService {

    /**
     * 功能描述：视频收藏信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/19 22:45
     * @since v1.0
     **/
    void collect(VideoFavoriteInfo info);

    /**
     * 功能描述： 删除收藏的视频信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/4/19 22:46
     * @since v1.0
     **/
    void cancelCollect(Integer id);

    /**
     * 功能描述： 查询收藏夹的信息
     *
     * @param currentPage 当前页
     * @param pageSize    分页大小
     * @param collector   收藏者
     * @param type        视频类型
     * @param title       视频标题
     * @author wangcanfeng
     * @time 2019/4/19 22:48
     * @since v1.0
     **/
    PageInfo<VideoInfoVo> getFavorites(Integer currentPage, Integer pageSize, String collector, String type, String title);

    /**
     * 功能描述：视频是否已经被收藏
     *
     * @param videoTitle 视频标题
     * @param collector  收藏者
     * @author wangcanfeng
     * @time 2019/4/19 22:50
     * @since v1.0
     **/
    boolean isExist(String videoTitle, String collector);
}
