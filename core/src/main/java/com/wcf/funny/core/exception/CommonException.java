package com.wcf.funny.core.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/3/11
 * @function 通用的异常处理
 **/
public class CommonException extends BaseException{
    /**
     * 功能描述：  枚举构造函数
     *
     * @param coreCode
     * @return
     * @authorwangcanfengg
     * @2018/12/29123:5723:56
     * @sin* @param coreCode
     */
    public CommonException(CoreCode coreCode) {
        super(coreCode);
    }

    /**
     * 功能描述：  携带异常原因的异常返回结果
     *
     * @param coreCode
     * @param e
     * @author wangcanfeng
     * @time 2019/2/23 11:12
     * @since v1.0
     **/
    public CommonException(CoreCode coreCode,Throwable e){
        super(coreCode,e);
    }
}
