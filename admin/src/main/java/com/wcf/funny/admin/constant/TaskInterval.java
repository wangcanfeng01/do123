package com.wcf.funny.admin.constant;

import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务执行间隔
 **/
public enum TaskInterval implements InfoEnum{
    // 直接执行
    RIGHT_NOW("right_now_code","rightNow"),
    // 在某个时间点执行
    ONCE("once_code","once"),
    // 每个小时执行一次
    PER_HOUR( "per_hour_code","perHour"),
    // 每天执行一次
    PER_DAY( "per_day_code","perDay"),
    // 每周执行一次
    PER_WEEK( "per_week_code","perWeek"),
    // 每月执行一次
    PER_MONTH("per_month_code","perMonth"),
    // 每年执行一次
    PER_YEAR("per_year_code","perYear"),
    ;

    TaskInterval(String code,String interval) {
        this.code=code;
        this.interval = interval;
    }


    private String interval;

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
        return interval;
    }
}
