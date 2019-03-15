package com.wcf.funny.admin.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务间隔对应的cron表达式map
 **/
public class TaskIntervalMap {
    private final static Map<String, String> intervalMap = new HashMap<>();

    /**
     * 功能描述：根据interval对应的cron表达式
     *
     * @param interval
     * @author wangcanfeng
     * @time 2019/3/15 23:03
     * @since v1.0
     **/
    public static String getCron(Integer interval) {
        // 如果还没有赋初值，则调用赋值方法
        if (intervalMap.size() == 0) {
            init();
        }
        return intervalMap.get(interval);
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
        intervalMap.put("perHour", "0 0 * * * ? *");
        intervalMap.put("perDay", "0 0 0 * * ? *");
        intervalMap.put("perWeek", "0 0 0 0 0 * *");
        intervalMap.put("perMonth", "0 0 0 0 * ? *");
        intervalMap.put("perYear", "0 0 0 0 0 ? *");
    }
}
