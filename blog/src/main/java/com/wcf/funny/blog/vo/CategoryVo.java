package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 专题标签视图信息
 **/
@Data
public class CategoryVo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 专题名称
     */
    private String name;
    /**
     * 专题封面路径
     */
    private String cover;
    /**
     * 专题创建时间
     */
    private String createTime;
    /**
     * 专题对应的文章数量
     */
    private Integer count;
    /**
     * 专题描述信息
     */
    private String description;
}
