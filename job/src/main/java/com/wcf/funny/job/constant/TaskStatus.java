package com.wcf.funny.job.constant;

import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务执行状态
 **/
public enum TaskStatus implements InfoEnum {
    //未开始
    UNSTART("unstart_code", "unstart"),
    //执行中
    EXECUTING("executing_code", "executing"),
    //已结束
    FINISHED("finish_code", "finished"),
    //等待中
    WAITING("waiting_code", "waiting"),
    //已暂停
    PAUSED("paused_code", "paused"),;

    TaskStatus(String code, String result) {
        this.code = code;
        this.status = result;
    }

    private String status;

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
        return status;
    }


    /**
     * 功能描述：  将字符串信息转成枚举类型
     *
     * @param status
     * @author wangcanfeng
     * @time 2019/3/9 12:59
     * @since v1.0
     **/
    public static TaskStatus valueOfStatus(String status) {
        TaskStatus taskStatus;
        switch (status) {
            case "unstart": {
                taskStatus = UNSTART;
                break;
            }
            case "executing": {
                taskStatus = EXECUTING;
                break;
            }
            case "finished": {
                taskStatus = FINISHED;
                break;
            }
            case "waiting": {
                taskStatus = WAITING;
                break;
            }
            case "paused": {
                taskStatus = PAUSED;
                break;
            }
            default: {
                throw new TaskException(TaskErrorCode.TASK_STATUS_UNSUPPORTED);
            }
        }
        return taskStatus;
    }
}
