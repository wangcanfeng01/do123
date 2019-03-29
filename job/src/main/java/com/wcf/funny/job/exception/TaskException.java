package com.wcf.funny.job.exception;

import com.wcf.funny.core.exception.BaseException;
import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务异常信息
 **/
public class TaskException extends BaseException {
    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public TaskException(CoreCode errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public TaskException(CoreCode errorMessage) {
        super(errorMessage);

    }

}
