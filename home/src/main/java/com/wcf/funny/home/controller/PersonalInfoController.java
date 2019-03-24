package com.wcf.funny.home.controller;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;
import com.wcf.funny.admin.exception.errorcode.UploadErrorCode;
import com.wcf.funny.admin.service.PersonDetailsService;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.NameAndType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.service.UploadFileService;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import com.wcf.funny.core.utils.UploadFileUtils;
import com.wcf.funny.home.vo.PersonDetailsVo;
import com.wcf.funny.home.vo.req.PersonalDetailInfoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 用户个人信息controller
 **/
@RestController
@RequestMapping("/ui/personal")
public class PersonalInfoController {

    @Autowired
    private UploadFileService fileService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PersonDetailsService personDetailsService;

    @GetMapping("/details")
    public BaseResponse<PersonDetailsVo> getPersonDetailsInfo(@RequestParam("username") String username) {
        PersonDetailsInfo person = personDetailsService.getPersonDetailByName(username);
        return new BaseResponse<>(convertToDetailVo(person));
    }

    /**
     * 功能描述：  上传头像
     *
     * @param face 头像文件，只能是图片
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/2 18:52
     * @since v1.0
     **/
    @PostMapping("/uploadFace")
    @OperationLog(action = LogConstant.ActionType.UPLOAD, object = LogConstant.ActionObject.USER,
            info = LogConstant.ActionInfo.UPLOAD_USER_FACE)
    public BaseResponse uploadFace(@RequestParam("file") MultipartFile face,
                                   @RequestParam("id") Integer id,
                                   @RequestParam("path") String path) {
        // 先调用工具类，完成用户头像上传
        PictureUploadInfo info = UploadFileUtils.uploadFace(face, PictureType.FACE);
        info.setBelongTo(id);
        info.setUploader(RequestUtils.getUserName());
        info.setUploadTime(FunnyTimeUtils.nowUnix());
        //如果原先存在图片信息，则先删除
        if (!ObjectUtils.isEmpty(path)) {
            // 提取路径参数中的uuid,先删除数据库中的记录，再删除文件夹中的图片
            fileService.deletePictureInfo(UploadFileUtils.getFileName(path));
            UploadFileUtils.deletePictureByRelative(path);
        }
        fileService.uploadPictureInfo(info);
        // 更新用户头像信息
        userInfoService.changeUserFaceById(info.getPath(), id);
        return new BaseResponse<>(info.getPath());
    }


    /**
     * 功能描述： 更新用户详情
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/9 20:05
     * @since v1.0
     **/
    @PutMapping("/update/personDetails")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER,
            info = LogConstant.ActionInfo.UPDATE_USER_DETAILS)
    public BaseResponse updatePersonDetails(@RequestBody PersonalDetailInfoReq req) {
        PersonDetailsInfo detailsInfo = new PersonDetailsInfo();
        detailsInfo.setEmail(req.getEmail());
        detailsInfo.setMind(req.getMind());
        detailsInfo.setPersonName(req.getPersonName());
        detailsInfo.setUsername(req.getUsername());
        detailsInfo.setWorkArea(req.getWorkArea());
        detailsInfo.setTelephone(req.getTelephone());
        detailsInfo.setTags(ConvertIdUtils.convertListToTags(req.getTags()));
        detailsInfo.setResume(req.getResume());
        personDetailsService.updateDetails(detailsInfo);
        return BaseResponse.ok();
    }


    /**
     * 功能描述：  上传个人简历
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/24 23:30
     * @since v1.0
     **/
    @PostMapping("/uploadResume")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER,
            info = LogConstant.ActionInfo.UPLOAD_RESUME)
    public BaseResponse uploadResume(@RequestParam("resume") String resume) {
        String username = RequestUtils.getUserName();
        //删除原先的简历

        personDetailsService.updateResumeByName(username, resume);
        return BaseResponse.ok();
    }


    /**
     * 功能描述：将详细信息转成视图信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/9 18:07
     * @since v1.0
     **/
    private PersonDetailsVo convertToDetailVo(PersonDetailsInfo detailsInfo) {
        PersonDetailsVo vo = new PersonDetailsVo();
        vo.setEmail(detailsInfo.getEmail());
        vo.setMind(detailsInfo.getMind());
        vo.setPersonName(detailsInfo.getPersonName());
        vo.setTags(ConvertIdUtils.convertTagsToList(detailsInfo.getTags()));
        vo.setTelephone(detailsInfo.getTelephone());
        vo.setUsername(detailsInfo.getUsername());
        vo.setWorkArea(detailsInfo.getWorkArea());
        vo.setResume(detailsInfo.getResume());
        return vo;
    }

}
