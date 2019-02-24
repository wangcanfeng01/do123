package com.wcf.funny.core.mapper;

import com.wcf.funny.core.entity.PictureUploadInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文件上传信息记录到数据库的操作语句
 **/
@Mapper
public interface UploadFileMapper {
    /**
     * 功能描述：  上传专题封面
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 12:55
     * @since v1.0
     **/
    @Insert("INSERT INTO info_upload_pic(pic_name, uuid, path, uploader, upload_time, pic_type," +
            " size,belong_to) VALUES(#{picName},#{uuid},#{path},#{uploader},#{uploadTime},#{picType}," +
            " #{size},#{belongTo})")
    void uploadPictureInfo(PictureUploadInfo info);
}
