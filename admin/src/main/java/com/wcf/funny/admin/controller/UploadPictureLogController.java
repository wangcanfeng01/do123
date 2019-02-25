package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.vo.OpsLogVo;
import com.wcf.funny.admin.vo.UploadPictureLogVo;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.constant.PictureTypeMap;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.service.UploadFileService;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wangcanfeng
 * @time 2019/2/25
 * @function 图片上传记录控制器
 **/
@RestController
public class UploadPictureLogController {

    @Autowired
    private UploadFileService fileService;

    /**
     * 功能描述：  查询图片上传记录信息
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/25 22:29
     * @since v1.0
     **/
    @GetMapping("/logList")
    public BaseResponse<List<UploadPictureLogVo>> getOpsLogList(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        PageInfo<PictureUploadInfo> pageInfo = fileService.getPictureLogs(currentPage, pageSize);
        return new PageResponse<>(convertPageInfo(pageInfo));
    }

    /**
     * 功能描述：  带时间条件查询图片上传记录信息
     *
     * @param currentPage
     * @param pageSize
     * @return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.vo.OpsLogVo>>
     * @author wangcanfeng
     * @time 2019/2/4 12:27
     * @since v1.0
     **/
    @GetMapping("/logList/time")
    public BaseResponse<List<UploadPictureLogVo>> getOpsLogListByTime(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam("start") String start, @RequestParam("end") String end) {
        Integer startTime = FunnyTimeUtils.getUnixTime(start);
        Integer endTime = FunnyTimeUtils.getUnixTime(end);
        PageInfo<PictureUploadInfo> pageInfo = fileService.getPictureLogs(currentPage, pageSize, startTime, endTime);
        return new PageResponse<>(convertPageInfo(pageInfo));
    }


    /**
     * 功能描述： 将数据库查询结果转成视图信息
     *
     * @param pageInfo
     * @author wangcanfeng
     * @time 2019/2/25 23:25
     * @since v1.0
     **/
    private PageInfo<UploadPictureLogVo> convertPageInfo(PageInfo<PictureUploadInfo> pageInfo) {
        PageInfo<UploadPictureLogVo> result = new PageInfo<>();
        if (ObjectUtils.isEmpty(pageInfo.getList())) {
            return result;
        } else {
            List<PictureUploadInfo> list = pageInfo.getList();
            List<UploadPictureLogVo> vos = new ArrayList<>();
            list.forEach(picture -> {
                UploadPictureLogVo vo = new UploadPictureLogVo();
                vo.setId(picture.getId());
                vo.setPicName(picture.getPicName());
                vo.setUuid(picture.getUuid());
                vo.setPath(picture.getPath());
                vo.setUploader(picture.getUploader());
                vo.setUploadTime(FunnyTimeUtils.getTimeByUnixTime(picture.getUploadTime()));
                vo.setPicType(picture.getPicType());
                vo.setTypeName(PictureTypeMap.getName(picture.getPicType()));
                vo.setSize(picture.getSize());
                vo.setBelongTo(picture.getBelongTo());
                vos.add(vo);
            });
            result.setTotal(pageInfo.getTotal());
            result.setList(vos);
        }
        return result;
    }

}
