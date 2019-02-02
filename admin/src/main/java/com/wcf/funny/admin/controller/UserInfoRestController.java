package com.wcf.funny.admin.controller;

import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.exception.errorcode.UploadErrorCode;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.vo.req.UserInfoReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.controller.BaseController;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author wangcanfeng
 * @time 2019/1/11
 * @function 用户restful接口
 **/
@RestController
@RequestMapping("/ui/user")
public class UserInfoRestController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 功能描述：
     *
     * @param request
     * @return com.wcf.funny.core.reponse.BaseResponse<java.lang.String>
     * @author wangcanfeng
     * @time 2019/1/12 0:07
     * @since v1.0
     **/
    @GetMapping("/get/login")
    public BaseResponse<String> getLoginUser(HttpServletRequest request) {
        String username = request.getRemoteUser();
        return new BaseResponse<>(CommonCode.DEFAULT_SUCCESS_CODE, username);
    }

    /**
     * @author WCF
     * @time 2018/1/29 23:55
     * @Description 注册新用户
     **/
    @PostMapping("/register")
    @OperationLog(action = ActionType.REGISTER, object = ActionObject.USER)
    public BaseResponse register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.encode(password);
        //注册是时候默认生成头像
        String faceName=new Random().nextInt(10) + ".jpg";
        userInfoService.addNewUser(username,password,faceName);
        return BaseResponse.ok();
    }

    @PostMapping("/add")
    @OperationLog(action = ActionType.ADD, object = ActionObject.USER)
    public BaseResponse addUser(@RequestBody UserInfoReq user) {
        String username = user.getUsername();
        String password = user.getPassword();
        password = MD5Utils.encode(password);
        //注册是时候默认生成头像
        String faceName=new Random().nextInt(10) + ".jpg";
        userInfoService.addNewUser(username,password,faceName);
        return BaseResponse.ok();
    }


    /**
     * 功能描述：  上传头像
     *@author wangcanfeng
     *@time 2019/2/2 18:52
     *@since v1.0
     * @param face 头像文件，只能是图片
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PostMapping("/uploadFace")
    public BaseResponse uploadFace(@RequestParam("file") MultipartFile face){
        if (face.isEmpty() || !face.getContentType().startsWith("image")) {
            return new BaseResponse<>(UploadErrorCode.NOT_PICTURE_ERROR);
        }
        long size = face.getSize();
        if (size > CoreConstant.MB) {
            return new BaseResponse<>(UploadErrorCode.FACE_SIZE_OVER_1MB);
        }
        PictureUploadInfo info = new PictureUploadInfo();
//        UploadFileUtils uploadFileUtils=new UploadFileUtils(staticPicturePath,backupPicturePath);
//        String fileLink = uploadFileUtils.uploadPicture(file, picturePath, info);
//        if (!ObjectUtils.isEmpty(fileLink)) {
//            info.setAuthor(authorName);
//            info.setSize((int) size / 1024);
//            info.setOrganisationId(articleId);
//            info.setType("picture");
//            info.setCreateTime(DateUtils.getCurrentUnixTime());
//            info.setModifyTime(DateUtils.getCurrentUnixTime());
//            try {
//                pictureUploadService.insertPicture(info);
//            } catch (PgSqlException e) {
//                return BaseResponse.error(e);
//            }
//            return BaseResponse.ok(fileLink);
//        }
//        return BaseResponse.error("图片上传失败");
        return new BaseResponse<>("/upload/banner.gif");
    }


}
