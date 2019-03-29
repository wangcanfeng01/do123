package com.wcf.funny.job.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/24
 * @function 服务器指标
 **/
@Data
public class ServerIndexVo {
    /**
     * cpu使用量
     */
    private Double cpuPercentage;
    /**
     * 堆信息使用量
     */
    private Double heapPercentage;
    /**
     * 非堆信息使用量
     */
    private Double noheapPercentage;
    /**
     * 硬盘使用量
     */
    private Double diskPercentage;
}
