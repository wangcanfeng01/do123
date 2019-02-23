package com.wcf.funny.core.service;

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
    void uploadCategoryCover(PictureUploadInfo info);
}
