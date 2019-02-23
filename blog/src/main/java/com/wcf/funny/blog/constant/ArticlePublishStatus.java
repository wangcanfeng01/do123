package com.wcf.funny.blog.constant;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 文章发布状态
 **/
public enum ArticlePublishStatus {
    PUBLISH("publish"),
    DRAFT("draft");

    ArticlePublishStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
