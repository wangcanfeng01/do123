package com.wcf.funny.core.constant;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作结果
 **/
public enum ActionResult {
    SUCCESS(1),
    FAIL(0);

    ActionResult(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return this.code;
    }

}
