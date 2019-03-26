package com.wcf.funny.core.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/26
 * @function 图表信息
 **/
@Data
public class ChartInfo {
    /**
     * 坐标轴
     */
    private String[] axis;

    /**
     * 统计值
     */
    private int[] value;
}
