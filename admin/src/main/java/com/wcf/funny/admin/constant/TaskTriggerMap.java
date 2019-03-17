package com.wcf.funny.admin.constant;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务间隔对应的cron表达式map
 **/
public class TaskTriggerMap {
    private final static Map<String, CronTrigger> triggerMap = new HashMap<>();

    /**
     * 功能描述：根据interval对应的cron表达式
     *
     * @param interval
     * @author wangcanfeng
     * @time 2019/3/15 23:03
     * @since v1.0
     **/
    public static CronTrigger getCronTrigger(String interval) {
        // 如果还没有赋初值，则调用赋值方法
        if (triggerMap.size() == 0) {
            init();
        }
        return triggerMap.get(interval);
    }

    /**
     * 功能描述：  初始化类型与cron表达式的map
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/15 23:01
     * @since v1.0
     **/
    private static void init() {
        triggerMap.put("perHour", createTrigger("perHourTrigger", "0 0 * * * ? *"));
        triggerMap.put("perDay", createTrigger("perDayTrigger", "0 0 0 * * ? *"));
        triggerMap.put("perWeek", createTrigger("perWeekTrigger", "0 0 0 0 0 * *"));
        triggerMap.put("perMonth", createTrigger("perMonthTrigger", "0 0 0 0 * ? *"));
        triggerMap.put("perYear", createTrigger("perYearTrigger", "0 0 0 0 0 ? *"));
    }

    /**
     * 功能描述： 创建trigger
     *
     * @param triggerName
     * @param cron
     * @author wangcanfeng
     * @time 2019/3/16 23:53
     * @since v1.0
     **/
    private static CronTrigger createTrigger(String triggerName, String cron) {
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(triggerName)
                .withSchedule(scheduleBuilder)
                .build();
        return trigger;
    }
}
