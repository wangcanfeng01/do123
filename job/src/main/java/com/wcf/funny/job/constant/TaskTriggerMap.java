package com.wcf.funny.job.constant;

import com.wcf.funny.core.utils.UuidUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;


/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务间隔对应的cron表达式map
 **/
public class TaskTriggerMap {
    /**
     * 每小时执行
     */
    private final static String HOUR_CRON = "0 0 0-23 * * ? *";
    /**
     * 每天执行
     */
    private final static String DAY_CRON = "0 0 0 1/1 * ? *";
    /**
     * 每周执行
     */
    private final static String WEEK_CRON = "0 0 0 ? 1-12 2 *";
    /**
     * 每月执行
     */
    private final static String MONTH_CRON = "0 0 0 1 1-12 ? *";
    /**
     * 每年执行
     */
    private final static String YEAR_CRON = "0 0 0 1 1 ? *";

    /**
     * 功能描述：根据interval对应的cron表达式
     *
     * @param interval
     * @author wangcanfeng
     * @time 2019/3/15 23:03
     * @since v1.0
     **/
    public static CronTrigger getCronTrigger(TaskInterval interval) {
        String cron = null;
        String trigger_name = null;
        switch (interval) {
            case PER_HOUR: {
                cron = HOUR_CRON;
                trigger_name = "perHourTrigger";
                break;
            }
            case PER_DAY: {
                cron = DAY_CRON;
                trigger_name = "perDayTrigger";
                break;
            }
            case PER_WEEK: {
                cron = WEEK_CRON;
                trigger_name = "perWeekTrigger";
                break;
            }
            case PER_MONTH: {
                cron = MONTH_CRON;
                trigger_name = "perMonthTrigger";
                break;
            }
            case PER_YEAR: {
                cron = YEAR_CRON;
                trigger_name = "perYearTrigger";
                break;
            }
        }
        return createTrigger(trigger_name + "_" + UuidUtils.generateShortUuid(), cron);
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
