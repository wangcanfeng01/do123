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
    //任务类型不支持
    TASK_TYPE_UNSUPPORTED("0x02050002", "the task type is unsupported"),
    //任务执行周期不支持
    TASK_INTERVAL_UNSUPPORTED("0x02050003", "the task interval is unsupported"),
    //任务分组类型不支持
    TASK_GROUP_UNSUPPORTED("0x02050004", "the task group is unsupported"),
    //任务执行异常
    TASK_EXECUTE_FAILED("0x02050005", "task execute failed"),
    // 插入任务记录异常
    INSERT_TASK_FAILED("0x02050006", "insert task info failed"),
    // 删除任务记录异常
    DELETE_TASK_FAILED("0x02050007", "delete task info failed"),
    // 查询任务记录异常
    SEARCH_TASK_FAILED("0x02050008", "select task info failed"),
    // 更新任务记录异常
    UPDATE_TASK_FAILED("0x02050009", "update task info failed"),
    // 任务记录不存在
    TASK_LOG_NOT_EXIST("0x0205000A", "the task log is not exist"),
    // 任务暂停异常
    TASK_PAUSE_FAILED("0x0205000B", "pause task failed"),
    //该任务类型不支持暂停
    TASK_UNSUPPORTED_PAUSED("0x0205000C", "this type of task can not be paused"),
    //任务复位异常
    TASK_RESUME_FAILED("0x0205000D", "resume task failed"),
    //任务状态类型不支持
    TASK_STATUS_UNSUPPORTED("0x0205000E","ths status of task is unsupported"),
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
