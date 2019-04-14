package com.wcf.funny.job.constant;

import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务分组
 **/
public enum TaskGroup implements InfoEnum {
    // 系统信息统计任务组
    SYSTEM_STATISTIC("system_statistic_code", "system_statistic"),
    // 腾讯视频爬取任务组
    TENCENT_CRAWLER("tencent_crawler_code", "tencent_crawler"),;

    TaskGroup(String code, String group) {
        this.code = code;
        this.group = group;
    }


    private String group;

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
        return group;
    }

    /**
     * 功能描述：将任务组名转成枚举
     *
     * @param group
     * @author wangcanfeng
     * @time 2019/3/17 12:59
     * @since v1.0
     **/
    public static TaskGroup valueOfGroup(String group) {
        TaskGroup taskGroup;
        switch (group) {
            case "system_statistic": {
                taskGroup = SYSTEM_STATISTIC;
                break;
            }
            case "tencent_crawler": {
                taskGroup = TENCENT_CRAWLER;
                break;
            }
            default: {
                throw new TaskException(TaskErrorCode.TASK_GROUP_UNSUPPORTED);
            }
        }
        return taskGroup;
    }
}
