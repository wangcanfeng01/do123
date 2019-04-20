package com.wcf.funny.video.constant;

import com.wcf.funny.core.constant.InfoEnum;


/**
 * @author wangcanfeng
 * @time 2019/4/20
 * @function
 **/
public enum VideoType implements InfoEnum {
    //电视剧
    TV("tv_code", "tv"),
    // 电影
    MOVIE("movie_code", "movie"),
    // 卡通动漫
    CARTOON("cartoon_code", "cartoon"),
    // 综艺
    VARIETY("variety_code", "variety"),;

    VideoType(String code, String type) {
        this.code = code;
        this.type = type;
    }


    private String type;

    private String code;

    /**
     * @return java.lang.String
     * @note 获取状态码
     * @author WCF
     * @time 2018/12/28 22:49
     * @since v1.0
     **/
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return java.lang.String
     * @note 获取信息
     * @author WCF
     * @time 2018/12/28 22:49
     * @since v1.0
     **/
    @Override
    public Object getInfo() {
        return type;
    }
}
