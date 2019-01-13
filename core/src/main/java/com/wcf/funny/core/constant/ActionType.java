package com.wcf.funny.core.constant;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作类型
 **/
public enum ActionType {
    //增加
    ADD(1),
    //删除
    DELETE(2),
    //更新
    UPDATE(3),
    //搜索
    SEARCH(4),
    //登录
    LOGIN(5),
    //登出
    LOGOUT(6),
    //注册
    REGISTER(7);

    ActionType(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return this.code;
    }
}
