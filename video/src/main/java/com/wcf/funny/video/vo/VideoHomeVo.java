package com.wcf.funny.video.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/12
 * @function 每个视频网站爬取的连接视频首页视图信息
 **/
@Data
public class VideoHomeVo {
    /**
     * 跑马灯视频信息
     */
    private List<VideoInfoVo> carousels;

    /**
     * 综艺视频信息
     */
    private List<VideoInfoVo> varietyShows;

    /**
     * 电视剧视频信息
     */
    private List<VideoInfoVo> tvHots;

    /**
     * 动漫视频信息
     */
    private List<VideoInfoVo> cartoons;

    /**
     * 电影视频信息
     */
    private List<VideoInfoVo> movies;
}
