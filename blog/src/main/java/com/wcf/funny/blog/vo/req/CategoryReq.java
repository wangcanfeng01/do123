package com.wcf.funny.blog.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 专题请求对象
 **/
@Data
public class CategoryReq {
    /**
     * id
     */
    private Integer id;
    /**
     * 专题名
     */
    private String name;
    /**
     * 专题封面
     */
    private String cover;
    /**
     * 专题描述
     */
    private String description;
}
