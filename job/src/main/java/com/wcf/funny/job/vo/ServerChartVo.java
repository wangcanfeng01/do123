package com.wcf.funny.job.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/24
 * @function 服务器表格信息
 **/
@Data
public class ServerChartVo {
    /**
     * 坐标轴信息
     */
    private String[] xAxis;
    /**
     * 堆内存信息
     */
    private double[] heap;
    /**
     * 非堆内存数组
     */
    private double[] noheap;
    /**
     * 硬盘信息数组
     */
    private double[] disk;
    /**
     * cpu信息数组
     */
    private double[] cpu;
}
