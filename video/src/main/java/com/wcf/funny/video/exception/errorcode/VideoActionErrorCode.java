package com.wcf.funny.video.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/4/20
 * @function 视频操作错误码
 **/
public enum  VideoActionErrorCode implements CoreCode{
    //视频已经被收藏
    FAVORITE_VIDEO_EXIST("0x07010001", "video has already been collected"),
    //视频收藏异常
    FAVORITE_VIDEO_ERROR("0x07010002", "collect the favorite video info failed"),
    //取消收藏异常
    CANCEL_VIDEO_ERROR("0x07010003", "cancel the favorite video failed"),
    //视频收藏查询异常
    SELECT_VIDEO_ERROR("0x07010004", "select the favorite videos failed");

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    VideoActionErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getReason() {
        return msg;
    }
}
