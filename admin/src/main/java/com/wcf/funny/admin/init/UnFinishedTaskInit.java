package com.wcf.funny.admin.init;

import com.wcf.funny.admin.constant.TaskTriggerMap;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.service.JobScheduleService;
import com.wcf.funny.admin.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/22
 * @function 用于重启任务
 **/
@Component
public class UnFinishedTaskInit implements ApplicationRunner {

    @Autowired
    private TaskLogService taskLogService;

    @Autowired
    private JobScheduleService jobScheduleService;

    /**
     * 功能描述：重启任务
     *
     * @param args
     * @author wangcanfeng
     * @time 2019/3/22 22:07
     * @since v1.0
     **/
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ScheduleTaskInfo> list = taskLogService.searchUnFinishedTask();
        if (!ObjectUtils.isEmpty(list)) {
            list.forEach(task -> jobScheduleService.restartJob(task));
        }
    }
}
