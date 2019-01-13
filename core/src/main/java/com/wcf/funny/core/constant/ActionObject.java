package com.wcf.funny.core.constant;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作对象枚举
 **/
public enum ActionObject {
    //用户
    USER("user");

    ActionObject(String object) {

    }

    private String object;

    public String getObject() {
        return this.object;
    }
}
