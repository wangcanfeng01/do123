package com.wcf.funny.job.constant;

import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务执行结果
 **/
public enum TaskResult implements InfoEnum{
    SUCCESS("success_code","success"),
    FAILED("failed_code","failed"),
    ;

    TaskResult(String code,String result) {
        this.code=code;
        this.result = result;
    }


    private String result;

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
        return result;
    }

    /**
     * 功能描述：  将字符串信息转成枚举类型
     *
     * @param type
     * @author wangcanfeng
     * @time 2019/3/9 12:59
     * @since v1.0
     **/
    public static TaskResult valueOfString(String type) {
        TaskResult taskResult;
        switch (type) {
            case "success": {
                taskResult = SUCCESS;
                break;
            }
            case "failed": {
                taskResult = FAILED;
                break;
            }
            default: {
                throw new TaskException(TaskErrorCode.TASK_RESULT_UNSUPPORTED);
            }
        }
        return taskResult;
    }
}
