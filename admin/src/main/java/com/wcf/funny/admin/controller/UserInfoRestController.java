package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.exception.errorcode.UploadErrorCode;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.vo.UserInfoVo;
import com.wcf.funny.admin.vo.req.UserInfoReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.controller.BaseController;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * 功能描述：  查询用户信息列表
     *
     * @param pageSize
     * @param currentPage
     * @return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.vo.UserInfoVo>>
     * @author wangcanfeng
     * @time 2019/2/3 13:32
     * @since v1.0
     **/
    @GetMapping("/userList")
    public BaseResponse<List<UserInfoVo>> getUserList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage) {
        PageInfo<UserInfoVo> pageInfo = userInfoService.getUserList(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
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
        //注册时生成默认头像
        String facePath = UserConstant.DEFAULT_FACE;
        userInfoService.addNewUser(username, password, facePath);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  由管理员创建用户信息
     *
     * @param user
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 14:55
     * @since v1.0
     **/
    @PostMapping("/add")
    @OperationLog(action = ActionType.ADD, object = ActionObject.USER)
    public BaseResponse addUser(@RequestBody UserInfoReq user) {
        UserInfo info = new UserInfo();
        info.setUsername(user.getUsername());
        info.setPassword(MD5Utils.encode(user.getPassword()));
        info.setIntroduce(user.getIntroduce());
        info.setFacePath(UserConstant.DEFAULT_FACE);
        info.setRegisterTime(FunnyTimeUtils.now());
        info.setUpdateTime(FunnyTimeUtils.now());
        //设置用户角色
        info.setRole(ConvertIdUtils.getString(user.getRole()));
        //初始0分
        info.setScore(0);
        info.setUserLevel(user.getLevel());
        info.setIsEnable(UserConstant.USER_ENABLE);
        userInfoService.addNewUser(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  根据用户的id修改用户状态
     *@author wangcanfeng
     *@time 2019/2/3 15:22
     *@since v1.0
     * @param isEnable
    * @param id
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PutMapping("/status/{id}/{isEnable}")
    public BaseResponse changeUserStatus(@PathVariable("isEnable") Integer isEnable, @PathVariable("id") Integer id) {
        userInfoService.changeStatus(isEnable, id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：修改用户角色信息
     *@author wangcanfeng
     *@time 2019/2/3 15:37
     *@since v1.0
     * @param req
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PutMapping("/modify/role")
    public BaseResponse modifyBase(@RequestBody UserInfoReq req){

        return BaseResponse.ok();
    }

    /**
     * 功能描述：
     *@author wangcanfeng
     *@time 2019/2/3 15:50
     *@since v1.0
     * @param id
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PutMapping("/resetPass/{id}")
    public BaseResponse resetPass(@PathVariable("id")Integer id){
        String password=MD5Utils.encode(UserConstant.DEFAULT_PASSWORD);
        userInfoService.changePassword(password,id);
        return BaseResponse.ok();
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
    public BaseResponse uploadFace(@RequestParam("file") MultipartFile face) {
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
