package com.wcf.funny.job.constant;

import com.wcf.funny.core.constant.InfoEnum;
import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务类型
 **/
public enum TaskType implements InfoEnum {
    //单次任务
    SINGLE("task_single_time_code", "single"),
    //定时多次任务
    MULTI("task_multi_times_code", "multi");

    TaskType(String code, String type) {
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


    /**
     * 功能描述：  将字符串信息转成枚举类型
     *
     * @param type
     * @author wangcanfeng
     * @time 2019/3/9 12:59
     * @since v1.0
     **/
    public static TaskType valueOfString(String type) {
        TaskType taskType;
        switch (type) {
            case "single": {
                taskType = SINGLE;
                break;
            }
            case "multi": {
                taskType = MULTI;
                break;
            }
            default: {
                throw new TaskException(TaskErrorCode.TASK_TYPE_UNSUPPORTED);
            }
        }
        return taskType;
    }
}
