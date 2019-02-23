package com.wcf.funny.blog.constant;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 标签类型
 **/
public enum MetaType {
    KEYWORD("keyword"),
    CATEGORY("category");

    MetaType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
