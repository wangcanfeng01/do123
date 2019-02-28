package com.wcf.funny.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.exception.errorcode.FileUploadErrorCode;
import com.wcf.funny.core.mapper.UploadFileMapper;
import com.wcf.funny.core.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void uploadPictureInfo(PictureUploadInfo info) {
        try {
            fileMapper.uploadPictureInfo(info);
        } catch (Exception e) {
            throw new PgSqlException(FileUploadErrorCode.FILE_INFO_INSERT_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据uuid删除图片上传记录信息
     *
     * @param uuid
     * @author wangcanfeng
     * @time 2019/2/25 22:01
     * @since v1.0
     **/
    @Override
    public void deletePictureInfo(String uuid) {
        try {
            fileMapper.deletePicByUuid(uuid);
        } catch (Exception e) {
            throw new PgSqlException(FileUploadErrorCode.FILE_INFO_DELETE_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询图片上传记录，分页查
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/25 22:34
     * @since v1.0
     **/
    @Override
    public PageInfo<PictureUploadInfo> getPictureLogs(int currentPage, int pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<PictureUploadInfo> uploadInfos = fileMapper.getPictureLogList();
            return new PageInfo<>(uploadInfos);
        } catch (Exception e) {
            throw new PgSqlException(FileUploadErrorCode.FILE_INFO_SELECT_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询图片上传记录，分页查
     *
     * @param currentPage
     * @param pageSize
     * @param start
     * @param end         @author wangcanfeng
     * @time 2019/2/25 22:34
     * @since v1.0
     **/
    @Override
    public PageInfo<PictureUploadInfo> getPictureLogs(int currentPage, int pageSize, int start, int end) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<PictureUploadInfo> uploadInfos = fileMapper.getPictureLogListByTime(start,end);
            return new PageInfo<>(uploadInfos);
        } catch (Exception e) {
            throw new PgSqlException(FileUploadErrorCode.FILE_INFO_SELECT_ERROR, e);
        }
    }

    /**
     * 功能描述：查询图片归属
     *
     * @param type
     * @param id
     * @author wangcanfeng
     * @time 2019/2/28 22:51
     * @since v1.0
     **/
    @Override
    public String getPictureBelongTo(PictureType type, Integer id) {
        return fileMapper.getPictureBelongTo(type,id);
    }
}
