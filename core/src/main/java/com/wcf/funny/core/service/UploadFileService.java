package com.wcf.funny.core.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.entity.PictureUploadInfo;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文件上传接口
 **/
public interface UploadFileService {
    /**
     * 功能描述：  上传专题封面
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 12:55
     * @since v1.0
     **/
    void uploadPictureInfo(PictureUploadInfo info);

    /**
     * 功能描述：  根据uuid删除图片上传记录信息
     *
     * @param uuid
     * @author wangcanfeng
     * @time 2019/2/25 22:01
     * @since v1.0
     **/
    void deletePictureInfo(String uuid);


    /**
     * 功能描述：  查询图片上传记录，分页查
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/25 22:34
     * @since v1.0
     **/
    PageInfo<PictureUploadInfo> getPictureLogs(int currentPage, int pageSize);


    /**
     * 功能描述：  查询图片上传记录，分页查
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/25 22:34
     * @since v1.0
     **/
    PageInfo<PictureUploadInfo> getPictureLogs(int currentPage, int pageSize, int start, int end);

}
