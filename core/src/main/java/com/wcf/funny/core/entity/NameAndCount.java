package com.wcf.funny.core.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/27
 * @function 名称和统计值
 **/
@Data
public class NameAndCount {
    /**
     * 名称
     */
    private String name;
    /**
     * 统计值
     */
    private Integer count;
}
