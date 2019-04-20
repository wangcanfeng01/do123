package com.wcf.funny.video.mapper;

import com.wcf.funny.video.entity.VideoFavoriteInfo;
import com.wcf.funny.video.mapper.provider.VideoFavoriteProvider;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into info_video_favorite  (collector, title, image, play_url, type, value, other," +
            " director, summary, create_time) " +
            " VALUES ( #{collector}, #{title}, #{image}, #{playUrl}, #{type}, #{value}, #{other}, #{director}," +
            " #{summary}, #{createTime}) ")
    void collect(VideoFavoriteInfo info);

    /**
     * 功能描述： 删除收藏的视频信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/4/19 22:46
     * @since v1.0
     **/
    @Delete("DELETE FROM info_video_favorite WHERE id=#{id}")
    void cancelCollect(Integer id);

    /**
     * 功能描述： 查询收藏夹的信息
     *
     * @param collector 收藏者
     * @param type      类型
     * @param title     标题
     * @author wangcanfeng
     * @time 2019/4/19 22:48
     * @since v1.0
     **/
    @SelectProvider(type = VideoFavoriteProvider.class, method = "favoritesSQL")
    List<VideoFavoriteInfo> getFavorites(@Param("collector") String collector,
                                         @Param("type") String type,
                                         @Param("title") String title);

    /**
     * 功能描述：视频是否已经被收藏
     *
     * @param videoTitle 视频标题
     * @param collector  收藏者
     * @author wangcanfeng
     * @time 2019/4/19 22:50
     * @since v1.0
     **/
    @Select("SELECT  id, collector, title, image, play_url as playUrl, type, value, other," +
            " director, summary, create_time as createTime " +
            " FROM info_video_favorite WHERE collector=#{collector} and title=#{title}")
    VideoFavoriteInfo getVideoInfo(@Param("title") String videoTitle,
                                   @Param("collector") String collector);
}
