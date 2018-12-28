package com.wcf.funny.core.exception.errorcode;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 错误信息父类
 **/
public interface CoreCode {

    /**
     *@note 获取状态码
     *@author WCF
     *@time 2018/12/28 22:49
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    String getCode();

    /**
     *@note 获取原因信息
     *@author WCF
     *@time 2018/12/28 22:49
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    String getReason();
}
