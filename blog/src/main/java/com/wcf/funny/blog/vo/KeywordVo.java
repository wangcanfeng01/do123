package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 标签的视图信息
 **/
@Data
public class KeywordVo {
    /**
     * 关键字序列号
     */
    private Integer id;
    /**
     * 关键字被使用的次数
     */
    private Integer count;
    /**
     * 关键字名称
     */
    private String name;
}
