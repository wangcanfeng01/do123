package com.wcf.funny.job.service;

import com.wcf.funny.job.entity.ScheduleTaskInfo;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务调度服务接口
 **/
public interface JobScheduleService {


    /**
     * 功能描述: 添加简单任务
     *
     * @param info
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void addJob(ScheduleTaskInfo info);


    /**
     * 功能描述： 服务器重启之后恢复任务
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/22 21:02
     * @since v1.0
     **/
    void restartJob(ScheduleTaskInfo info);


    /**
     * 功能描述: 修改任务Trigger，即修改任务的定时机制
     *
     * @param
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void modifyJob(ScheduleTaskInfo info);

    /**
     * 功能描述: 暂停任务，只支持定时任务的暂停，不支持单次任务，单次任务需要interrupt
     *
     * @param taskId
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void pauseJobById(Integer taskId);

    /**
     * 功能描述: 从暂停状态中恢复定时任务运行
     *
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void resumeJobById(Integer taskId);

    /**
     * 功能描述: 删除任务
     *
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    void deleteJobById(Integer taskId);

}
