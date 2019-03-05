package com.wcf.funny.admin.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/2
 * @function 文件上传的异常错误码
 **/
public enum UploadErrorCode implements CoreCode {
    //本次上传的文件不是图片资源
    NOT_PICTURE_ERROR("0x02020001", "this file is not a picture"),
    //头像大小不能超过1MB
    FACE_SIZE_OVER_1MB("0x02020002"," the face-picture's size can not over 1MB"),
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    UploadErrorCode(String code, String msg) {
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
