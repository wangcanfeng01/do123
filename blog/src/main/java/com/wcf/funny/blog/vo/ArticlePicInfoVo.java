package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/24
 * @function 文章图片的视图信息
 **/
@Data
public class ArticlePicInfoVo {
    /**
     * 图片uuid
     */
    private String uuid;
    /**
     * 图片路径
     */
    private String path;
}
