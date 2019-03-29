package com.wcf.funny.job.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/3/13
 * @function
 **/
public enum ServerInfoErrorCode implements CoreCode {
    //服务器信息查询异常
    SELECT_SERVER_ERROR("0x02040001", "select server info failed"),
    //服务器信息插入异常
    INSERT_SERVER_ERROR("0x02040002"," insert server info failed"),
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    ServerInfoErrorCode(String code, String msg) {
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
