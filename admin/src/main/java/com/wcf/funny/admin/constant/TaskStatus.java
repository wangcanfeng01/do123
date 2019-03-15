package com.wcf.funny.admin.constant;

import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务执行状态
 **/
public enum TaskStatus implements InfoEnum {
    //未开始
    UNSTART("unstart_code","unstart"),
    //执行中
    EXECUTING("executing_code","executing"),
    //已结束
    FINISHED("finish_code","finished"),
    //等待中
    WAITING("waiting_code","waiting"),
            ;

    TaskStatus(String code,String result) {
        this.code=code;
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

}
