package com.wcf.funny.about.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本视图信息
 **/
@Data
public class VersionInfoVo {
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
    private String publishTime;
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
    private String modifyTime;
}
