package com.wcf.funny.video.service;

import com.wcf.funny.video.vo.VideoInfoVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/12
 * @function 视频缓存信息接口
 **/
public interface VideoCacheService {

    /**
     * 功能描述：  将爬虫获取到的视频信息存入缓存
     *
     * @param client 客户端的名称
     * @param key    缓存键值
     * @param list   缓存的视频信息
     * @author wangcanfeng
     * @time 2019/4/13 22:31
     * @since v1.0
     **/
    void savesVideos(String client, String key, List<VideoInfoVo> list);

    /**
     * 功能描述： 跑马灯视频
     *
     * @param client
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> carousels(String client);

    /**
     * 功能描述：  综艺视频
     *
     * @param client
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> varietyShow(String client);

    /**
     * 功能描述：  热门电视剧
     *
     * @param client
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> tvHots(String client);

    /**
     * 功能描述：  动漫视频
     *
     * @param client
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> cartoons(String client);

    /**
     * 功能描述：  电影
     *
     * @param client
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> movies(String client);

    /**
     * 功能描述：  换一批视频
     *
     * @param client
     * @param key
     * @param index
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    List<VideoInfoVo> changeVideo(String client, String key, Integer index);

}
