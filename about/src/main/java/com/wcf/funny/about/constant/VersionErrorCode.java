package com.wcf.funny.about.constant;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function
 **/
public enum VersionErrorCode implements CoreCode {
    //插入版本信息异常
    INSERT_VERSION_ERROR("00000005", "Insert version info into database failed"),
    //搜索版本信息异常
    SELECT_VERSION_ERROR("00000006", "Select version info from database failed"),
    //更新版本信息异常
    UPDATE_VERSION_ERROR("00000007", "Update version info into database failed"),
    //删除版本信息异常
    DELETE_VERSION_ERROR("00000008", "Delete version info from database failed");

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    VersionErrorCode(String code, String msg) {
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
