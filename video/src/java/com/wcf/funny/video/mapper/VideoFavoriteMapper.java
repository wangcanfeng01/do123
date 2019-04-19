package com.wcf.funny.video.mapper;

import com.wcf.funny.video.entity.VideoFavoriteInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function 视频收藏数据库接口
 **/
@Mapper
public interface VideoFavoriteMapper {
    /**
     * 功能描述：视频收藏信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/19 22:45
     * @since v1.0
     **/
    @Insert("")
    void collect(VideoFavoriteInfo info);

    /**
     * 功能描述： 删除收藏的视频信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/4/19 22:46
     * @since v1.0
     **/
    @Delete("")
    void cancelCollect(Integer id);

    /**
     * 功能描述： 查询收藏夹的信息
     *
     * @param currentPage 当前页
     * @param pageSize    分页大小
     * @param collector   收藏者
     * @author wangcanfeng
     * @time 2019/4/19 22:48
     * @since v1.0
     **/
    @Select("")
    List<VideoFavoriteInfo> getFavorites(Integer currentPage, Integer pageSize, String collector);

    /**
     * 功能描述：视频是否已经被收藏
     *
     * @param videoTitle 视频标题
     * @param collector  收藏者
     * @author wangcanfeng
     * @time 2019/4/19 22:50
     * @since v1.0
     **/
    @Select("")
    VideoFavoriteInfo getVideoInfo(String videoTitle, String collector);
}
