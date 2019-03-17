package com.wcf.funny.admin.job;

import com.wcf.funny.admin.service.ServerInfoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 系统信息统计任务
 **/
@Service
public class SystemInfoStatisticJob implements Job {

    @Autowired
    private ServerInfoService serverInfoService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        serverInfoService.insertServerInfo();
    }
}
