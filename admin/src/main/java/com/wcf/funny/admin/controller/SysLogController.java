package com.wcf.funny.admin.controller;

import com.wcf.funny.admin.vo.SysLogVo;
import com.wcf.funny.core.filter.FileTimeFilter;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.UploadFileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @description 系统日志controller
 * @Date Created in 9:26-2019/3/8
 */
@RestController
@RequestMapping("/ui/sysLog")
@Log4j2
public class SysLogController {

    /**
     * 日志的路径
     */
    @Value("${log.path}")
    private String logPath;

    /**
     * 功能描述: 获取系统日志列表
     *
     * @param startTime
     * @param endTime
     * @return:com.wcf.funny.core.reponse.BaseResponse
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 10:02
     */
    @GetMapping("/select")
    public BaseResponse<List<SysLogVo>> getLogList(@RequestParam(value = "startTime", required = false) String startTime,
                                                   @RequestParam(value = "endTime", required = false) String endTime) {
        File total = new File(logPath);
        //通过文件过滤器获取符合条件的文件列表
        File[] fileList;
        FileFilter fileFilter;
        if (ObjectUtils.isEmpty(endTime) || ObjectUtils.isEmpty(startTime)) {
            fileFilter = new FileTimeFilter();
        } else {
            fileFilter = new FileTimeFilter(FunnyTimeUtils.getMillsTime(startTime), FunnyTimeUtils.getMillsTime(endTime));
        }
        fileList = total.listFiles(fileFilter);
        List<SysLogVo> vos = new ArrayList<>();
        for (File file : fileList) {
            //调用递归方法获取文件列表
            getFileList(file, vos, fileFilter);
        }
        return new ListResponse<>(vos);
    }


    /**
     * 功能描述: 日志文件根据传入的路径下载
     *
     * @param response
     * @param path
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 14:25
     */
    @GetMapping(value = "download")
    public void download(HttpServletResponse response, @RequestParam("path") String path) {
        //获取要下载的文件名
        String fileName = UploadFileUtils.getFileName(path);
        response.reset();
        try (InputStream in = new FileInputStream(path); OutputStream out = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            //设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //获取文件输入流
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            out.write(buffer);
            out.flush();
        } catch (Exception e) {
            log.error("download system logs failed", e);
        }
    }

    /**
     * 功能描述: 根据文件路径删除文件
     *
     * @param path
     * @return:com.wcf.funny.core.reponse.BaseResponse
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 14:56
     */
    @GetMapping("/delete")
    public BaseResponse deleteFile(@RequestParam("path") String path) {
        UploadFileUtils.deletePicture(path);
        return BaseResponse.ok();
    }

    /**
     * 功能描述: 递归获取指定路径下的文件列表
     *
     * @param total
     * @return:java.util.List<com.wcf.funny.admin.vo.SysLogVo>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/8 13:37
     */
    private void getFileList(File total, List<SysLogVo> logVos, FileFilter fileFilter) {
        // 判断是否文件夹
        if (total.isDirectory()) {
            File[] files = total.listFiles(fileFilter);
            for (File file : files) {
                getFileList(file, logVos, fileFilter);
            }
        } else {
            SysLogVo vo = new SysLogVo();
            vo.setSize(UploadFileUtils.getSizeToKB(total.length()));
            vo.setName(total.getName());
            vo.setPath(total.getPath());
            vo.setLastModifyTime(FunnyTimeUtils.getTimeByMillsTime(total.lastModified()));
            logVos.add(vo);
        }
    }
}
