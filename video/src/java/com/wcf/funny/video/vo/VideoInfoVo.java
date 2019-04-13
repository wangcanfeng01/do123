package com.wcf.funny.video.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/12
 * @function 视频视图信息
 **/
@Data
public class VideoInfoVo {
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
}
