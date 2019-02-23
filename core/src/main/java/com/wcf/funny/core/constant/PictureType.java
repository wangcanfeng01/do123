package com.wcf.funny.core.constant;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 图片类型
 **/
public enum PictureType {
    // 文章封面
    ARTICLE_COVER(0,"/upload/image/cover/article/"),
    // 专题封面
    CATEGORY_COVER(1,"/upload/image/cover/category/"),
    // 文章信息
    ARTICLE_INFO(2,"/upload/image/article/"),
    FACE(3,"/upload/image/face/");

    PictureType(Integer type,String prefix) {
        this.prefix=prefix;
        this.type = type;
    }

    /**
     * 图片类型
     */
    private Integer type;
    /**
     * 路径前缀
     */
    private String prefix;

    public Integer getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }
}
