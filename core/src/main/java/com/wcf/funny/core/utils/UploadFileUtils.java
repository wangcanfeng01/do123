package com.wcf.funny.core.utils;

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
    private final static String FILE_PREFIX="/upload";
    /**
     * @param file
     * @return java.lang.String
     * @note 保存图片
     * @author WCF
     * @time 2018/6/15 19:09
     * @since v1.0
     **/
//    public String uploadFace(MultipartFile file) {
//        //插入数据库中的是图片原名
//        info.setName(fileName);
//        //显示名称改成uuid
//        fileName = UuidUtils.generateUuid() + ".jpg";
//        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        path = path.replaceAll("\\\\", "/");
//        String filePath = path + staticPath + staticPicturePath + pathStr + fileName;
//        filePath = filePath.replaceAll("//", "/");
//        info.setPath(pathStr + fileName);
//        //上传图片
//        try (InputStream in = file.getInputStream();
//             OutputStream out = new FileOutputStream(filePath)) {
//            IOUtils.copy(in, out);
//        } catch (IOException e) {
//            log.error("Can't upload picture to: " + filePath, e);
//        } catch (Exception e) {
//            log.error("Can't upload picture to: " + filePath, e);
//        }
//        //上传到备份路径
//        String backupPath = backupPicturePath + pathStr + fileName;
//        try (InputStream in = file.getInputStream();
//             OutputStream backup = new FileOutputStream(backupPath)) {
//            IOUtils.copy(in, backup);
//        } catch (IOException e) {
//            log.error("Can't upload picture to backup path: " + filePath, e);
//        } catch (Exception e) {
//            log.error("Can't upload picture to backup path: " + filePath, e);
//        }
//
//        return staticPicturePath + pathStr + fileName;
//    }

    public static String getFileName(String totalName){
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

}
