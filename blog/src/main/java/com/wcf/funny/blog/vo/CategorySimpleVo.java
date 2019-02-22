package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 专题的简单信息
 **/
@Data
public class CategorySimpleVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 专题名称
     */
    private String name;
}
