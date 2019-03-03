package com.wcf.funny.about.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本信息
 **/
@Data
public class VersionInfo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 版本号
     */
    private String version;
    /**
     * 发布时间
     */
    private Integer publishTime;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 作者
     */
    private String author;
    /**
     * 修改时间
     */
    private Integer modifyTime;
}
