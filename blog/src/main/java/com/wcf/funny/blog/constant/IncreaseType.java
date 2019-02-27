package com.wcf.funny.blog.constant;

/**
 * @author wangcanfeng
 * @time 2019/2/26
 * @function 增加类型
 **/
public enum IncreaseType {
    // 增加
    INCREASE("+1"),
    // 减少
    DECREASE("-1"),
    // 新增
    NEW_INCREASE("0");


    IncreaseType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
