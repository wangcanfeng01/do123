package com.wcf.funny.blog.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 简单的专题信息
 **/
@Data
public class CategorySimple {
    /**
     * id
     */
    private Integer id;
    /**
     * 专题名
     */
    private String name;
}
