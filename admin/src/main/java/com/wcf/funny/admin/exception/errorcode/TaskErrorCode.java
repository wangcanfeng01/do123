package com.wcf.funny.admin.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function
 **/
public enum TaskErrorCode implements CoreCode {
    //任务结果类型不支持
    TASK_RESULT_UNSUPPORTED("0x02050001", "the task result type is unsupported"),
    ;


    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    TaskErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
     * @note 获取原因信息
     * @author WCF
     * @time 2018/12/28 22:49
     * @since v1.0
     **/
    @Override
    public String getReason() {
        return msg;
    }
}
