package com.wcf.funny.admin.service;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务调度服务接口
 **/
public interface JobScheduleService {
    /**
     * 功能描述: 添加简单任务
     * @param  可以自定义一个任务信息对象，然后从信息对象中获取参数创建简单任务
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void addSimpleJob();

    /**
     * 功能描述: 添加定时任务
     * @param 可以自定义一个任务信息对象，然后从信息对象中获取参数创建定时任务
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void addCronJob();

    /**
     * 功能描述: 修改任务Trigger，即修改任务的定时机制
     * @param
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void modifyJob();

    /**
     * 功能描述: 暂停任务，只支持定时任务的暂停，不支持单次任务，单次任务需要interrupt
     * @param
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void pauseJob();

    /**
     * 功能描述: 从暂停状态中恢复定时任务运行
     * @param
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void resumeJob();

    /**
     * 功能描述: 删除任务
     * @param
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void deleteJob();
}
