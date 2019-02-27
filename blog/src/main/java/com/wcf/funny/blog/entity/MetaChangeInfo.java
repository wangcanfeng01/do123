package com.wcf.funny.blog.entity;

import com.wcf.funny.blog.constant.IncreaseType;
import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/26
 * @function 标签改变信息
 **/
@Data
public class MetaChangeInfo {
    /**
     * 标签类型
     */
    private String type;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 增加或减少  1或-1
     */
    private IncreaseType decreaseOrIncrease;
}
