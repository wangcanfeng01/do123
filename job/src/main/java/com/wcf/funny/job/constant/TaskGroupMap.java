package com.wcf.funny.job.constant;

import com.wcf.funny.job.task.SystemInfoStatisticJob;
import org.quartz.Job;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务分组map
 **/
public class TaskGroupMap {
    private final static Map<String, Class<? extends Job>> taskGroupMap = new HashMap<>();

    /**
     * 功能描述：初始化任务信息分组
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/17 0:11
     * @since v1.0
     **/
    private static void init() {
        taskGroupMap.put("system_statistic", SystemInfoStatisticJob.class);
    }

    /**
     * 功能描述：根据任务组名获取任务的class
     *
     * @param group
     * @author wangcanfeng
     * @time 2019/3/17 0:13
     * @since v1.0
     **/
    public static Class<? extends Job> getJobClazz(String group) {
        if (taskGroupMap.size() == 0) {
            init();
        }
        return taskGroupMap.get(group);
    }
}
