package com.wcf.funny.admin.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/25
 * @function  图片上传记录视图信息
 **/
@Data
public class UploadPictureLogVo {
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
    private String uploadTime;
    /**
     * 图片类型
     */
    private Integer picType;
    /**
     * 图片类型名称
     */
    private String typeName;
    /**
     * 图片大小,单位kb
     */
    private Integer size;
    /**
     * 图片归属对象
     */
    private Integer belongTo;
}
