package com.wcf.funny.blog.constant;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 是否允许评论
 **/
public enum CommentEnableStatus {
    ALLOW_COMMENT(1),
    NO_COMMENT(0);

    CommentEnableStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
