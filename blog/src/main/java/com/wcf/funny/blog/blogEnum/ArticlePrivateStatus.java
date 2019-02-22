package com.wcf.funny.blog.blogEnum;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 文章公开状态
 **/
public enum ArticlePrivateStatus {
    PUBLIC("public"),
    PRIVATE("private");

    ArticlePrivateStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
