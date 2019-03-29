package com.wcf.funny.job.constant;

import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务执行间隔
 **/
public enum TaskInterval implements InfoEnum {
    // 直接执行
    RIGHT_NOW("right_now_code", "rightNow"),
    // 在某个时间点执行
    ONCE("once_code", "once"),
    // 每个小时执行一次
    PER_HOUR("per_hour_code", "perHour"),
    // 每天执行一次
    PER_DAY("per_day_code", "perDay"),
    // 每周执行一次
    PER_WEEK("per_week_code", "perWeek"),
    // 每月执行一次
    PER_MONTH("per_month_code", "perMonth"),
    // 每年执行一次
    PER_YEAR("per_year_code", "perYear"),;

    TaskInterval(String code, String interval) {
        this.code = code;
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

    /**
     * 功能描述：  根据interval信息转换成枚举
     *
     * @param interval
     * @author wangcanfeng
     * @time 2019/3/17 12:53
     * @since v1.0
     **/
    public static TaskInterval valueOfInterval(String interval) {
        TaskInterval taskInterval;
        switch (interval) {
            case "rightNow": {
                taskInterval = RIGHT_NOW;
                break;
            }
            case "once": {
                taskInterval = ONCE;
                break;
            }
            case "perHour": {
                taskInterval = PER_HOUR;
                break;
            }
            case "perDay": {
                taskInterval = PER_DAY;
                break;
            }
            case "perWeek": {
                taskInterval = PER_WEEK;
                break;
            }
            case "perMonth": {
                taskInterval = PER_MONTH;
                break;
            }
            case "perYear": {
                taskInterval = PER_YEAR;
                break;
            }
            default: {
                throw new TaskException(TaskErrorCode.TASK_INTERVAL_UNSUPPORTED);
            }
        }
        return taskInterval;
    }
}
