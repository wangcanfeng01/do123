package com.wcf.funny.core.service.impl;

import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.exception.errorcode.FileUploadErrorCode;
import com.wcf.funny.core.mapper.UploadFileMapper;
import com.wcf.funny.core.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文件信息上传接口实现
 **/
@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private UploadFileMapper fileMapper;

    /**
     * 功能描述：  上传专题封面
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 12:55
     * @since v1.0
     **/
    @Override
    public void uploadCategoryCover(PictureUploadInfo info) {
        try {
            fileMapper.uploadCategoryCover(info);
        } catch (Exception e) {
            throw new PgSqlException(FileUploadErrorCode.FILE_INFO_INSERT_ERROR, e);
        }
    }
}
