package com.wcf.funny.core.utils;

import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.errorcode.FileException;
import com.wcf.funny.core.exception.errorcode.FileUploadErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author wangcanfeng
 * @time 2019/2/2
 * @function 图片上传工具类
 **/
public class UploadFileUtils {
    /**
     * @param file
     * @param type
     * @return java.lang.String
     * @note 保存图片
     * @author WCF
     * @time 2018/6/15 19:09
     * @since v1.0
     **/
    public static PictureUploadInfo uploadFace(MultipartFile file, PictureType type) {
        // 新建一个返回信息的对象，这里只填充部分信息，还有部分信息在业务层填充
        PictureUploadInfo info = new PictureUploadInfo();
        //插入数据库中的是图片原名
        info.setPicName(file.getOriginalFilename());
        //显示名称改成uuid
        String uuid = UuidUtils.generateUuid() + ".jpg";
        info.setUuid(uuid);
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        path = path.replaceAll("\\\\", "/");
        String filePath = path + type.getPrefix();
        filePath = filePath.replaceAll("//", "/");
        //创建文件夹
        createDirectory(filePath);
        info.setPath(type.getPrefix() + uuid);
        info.setPicType(type.getType());
        info.setSize(getSizeToKB(file.getSize()));
        //上传图片
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(filePath + uuid)) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            throw new FileException(FileUploadErrorCode.FILE_COPY_TO_DIRECTORY_ERROR, e);
        }
        return info;
    }

    /**
     * 功能描述：  获取上传的文件名称
     *
     * @param totalName
     * @author wangcanfeng
     * @time 2019/2/22 23:52
     * @since v1.0
     **/
    public static String getFileName(String totalName) {
        String fileName = totalName;
        //因为系统不一致可能分隔符不一样,需要统一转成当前系统的分隔符在做处理
        fileName = fileName.replaceAll("\\\\", File.separator);
        String[] arr = fileName.split(File.separator);
        //如果分割处理后获取到的数组只有一个元素，则直接取来当作文件名称
        if (arr.length < 2) {
            if (!ObjectUtils.isEmpty(arr[0])) {
                fileName = arr[0];
            } else {
                return null;
            }
        } else {
            fileName = arr[arr.length - 1];
        }
        return fileName;
    }

    /**
     * 功能描述：  将size转成kb为单位的大小
     *
     * @param size
     * @author wangcanfeng
     * @time 2019/2/22 23:52
     * @since v1.0
     **/
    public static Integer getSizeToKB(long size) {
        return (int) size / 1024;
    }

    /**
     * 功能描述：创建文件路径
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/23 14:37
     * @since v1.0
     **/
    public static void createDirectory(String path) {
        if (ObjectUtils.isEmpty(path)) {
            throw new FileException(FileUploadErrorCode.FILE_PATH_NULL);
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
