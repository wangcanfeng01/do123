package com.wcf.funny.core.constant;

import com.wcf.funny.core.exception.PictureException;
import com.wcf.funny.core.exception.errorcode.CommonCode;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 图片类型
 **/
public enum PictureType {
    // 文章封面
    ARTICLE_COVER(0, "/upload/image/cover/article/"),
    // 专题封面
    CATEGORY_COVER(1, "/upload/image/cover/category/"),
    // 文章信息
    ARTICLE_INFO(2, "/upload/image/article/"),
    //人脸图片
    FACE(3, "/upload/image/face/");


    PictureType(Integer type, String prefix) {
        this.prefix = prefix;
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

    /**
     * 功能描述：根据类型的int值转成枚举
     *
     * @param type
     * @author wangcanfeng
     * @time 2019/2/28 22:48
     * @since v1.0
     **/
    public static PictureType vauleOf(Integer type) {
        switch (type) {
            case 0: {
                return ARTICLE_COVER;
            }
            case 1: {
                return CATEGORY_COVER;
            }
            case 2: {
                return ARTICLE_INFO;
            }
            case 3: {
                return FACE;
            }
            default: {
                throw new PictureException(CommonCode.UNSUPPORTED_PICTURE_TYPE);
            }
        }
    }
}
