package com.wcf.funny.video.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function 视频收藏信息
 **/
@Data
public class VideoFavoriteInfo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 收藏者
     */
    private String collector;
    /**
     * 视频名称
     */
    private String title;

    /**
     * 视频图片
     */
    private String image;

    /**
     * 视频播放地址
     */
    private String playUrl;

    /**
     * 播放类型
     */
    private String type;

    /**
     * 视频源地址
     */
    private String value;

    /**
     * 其他信息
     */
    private String other;

    /**
     * 演员
     */
    private String director;

    /**
     * 简介
     */
    private String summary;
    /**
     * 收藏时间
     */
    private Integer createTime;
}
