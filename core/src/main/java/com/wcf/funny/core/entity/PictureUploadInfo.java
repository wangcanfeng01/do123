package com.wcf.funny.core.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/2
 * @function 图片上传对象
 **/
@Data
public class PictureUploadInfo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 图片真实名称
     */
    private String picName;
    /**
     * 图片uuid
     */
    private String uuid;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 图片上传者
     */
    private String uploader;
    /**
     * 图片上传时间
     */
    private Integer uploadTime;
    /**
     * 图片类型
     */
    private Integer picType;
    /**
     * 图片大小
     */
    private Integer size;
    /**
     * 图片归属对象
     */
    private String belongTo;
}
