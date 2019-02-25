package com.wcf.funny.core.mapper;

import com.wcf.funny.core.entity.PictureUploadInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 功能描述：  根据uuid删除图片记录信息
     *
     * @param uuid
     * @author wangcanfeng
     * @time 2019/2/25 22:02
     * @since v1.0
     **/
    @Delete("DELETE FROM info_upload_pic where uuid=#{uuid}")
    void deletePicByUuid(String uuid);

    /**
     * 功能描述：查询图片上传记录的列表信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/25 22:49
     * @since v1.0
     **/
    @Select("SELECT id, pic_name as picName, uuid, path, uploader, upload_time as uploadTime, " +
            "pic_type as picType, size, belong_to as belongTo FROM info_upload_pic order by upload_time desc")
    List<PictureUploadInfo> getPictureLogList();


    /**
     * 功能描述：  根据时间查询图片上传记录的列表信息
     *
     * @param start
     * @param end
     * @author wangcanfeng
     * @time 2019/2/25 22:49
     * @since v1.0
     **/
    @Select("SELECT id, pic_name as picName, uuid, path, uploader, upload_time as uploadTime, " +
            "pic_type as picType, size, belong_to as belongTo FROM info_upload_pic" +
            " WHERE upload_time >=#{start} AND upload_time <=#{end} ORDER BY upload_time DESC")
    List<PictureUploadInfo> getPictureLogListByTime(@Param("start") Integer start, @Param("end") Integer end);
}
