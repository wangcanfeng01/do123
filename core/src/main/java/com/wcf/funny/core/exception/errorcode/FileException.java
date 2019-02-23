package com.wcf.funny.core.exception.errorcode;

import com.wcf.funny.core.exception.BaseException;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文件异常
 **/
public class FileException extends BaseException{
    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public FileException(CoreCode errorMessage,Throwable e) {
        super(errorMessage,e);
    }

    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public FileException(CoreCode errorMessage) {
        super(errorMessage);
    }
}
