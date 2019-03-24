package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/13
 * @function 服务器信息
 **/
@Data
public class ServerInfo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 统计时间
     */
    private Integer createTime;
    /**
     * 堆内存使用量
     */
    private String heapUsed;
    /**
     * 非堆内存使用量
     */
    private String noHeapUsed;
    /**
     * 硬盘使用量
     */
    private String diskUsed;
    /**
     * cpu使用量
     */
    private String cpuUsed;
    /**
     * 统计类型
     */
    private String statisticType;
}
