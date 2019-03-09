package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.AdminConstant;
import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.constant.UserStatus;
import com.wcf.funny.admin.constant.UserType;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.entity.UserRelatedMenu;
import com.wcf.funny.admin.exception.errorcode.UploadErrorCode;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.admin.service.PersonDetailsService;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.admin.vo.UserMenuAuthVo;
import com.wcf.funny.admin.vo.UserInfoVo;
import com.wcf.funny.admin.vo.req.PasswordReq;
import com.wcf.funny.admin.vo.req.UserBaseReq;
import com.wcf.funny.admin.vo.req.UserInfoReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.controller.BaseController;
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.ErrorResponse;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.MD5Utils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private UserRoleService roleService;

    @Autowired
    private MenuInfoService menuInfoService;
    @Autowired
    private PersonDetailsService personDetailsService;

    /**
     * 这个id对应的菜单是不存在的
     */
    private final static String NULL_MENU_ID = "0";

    /**
     * 功能描述：获取用户登录的信息，以及最初始的导航栏
     *
     * @author wangcanfeng
     * @time 2019/1/12 0:07
     * @since v1.0
     **/
    @GetMapping("/get/login")
    public BaseResponse<UserMenuAuthVo> getLoginUser() {
        String username = RequestUtils.getUserName();
        UserRelatedMenu relatedMenu = userInfoService.getMenusStringListByName(username);
        StringBuilder sb = new StringBuilder();
        if (ObjectUtils.isEmpty(relatedMenu)) {
            // 如果是查询不到的用户则直接报错
            throw new ErrorResponse(UserErrorCode.LOGIN_USER_INFO_ERROR);
        }
        if (ObjectUtils.isEmpty(relatedMenu.getMenuIds())) {
            sb.append(NULL_MENU_ID);
        } else {
            // 对菜单id做去重
            HashSet<Integer> menuIds = new HashSet<>();
            relatedMenu.getMenuIds().forEach(menusString -> {
                ConvertIdUtils.getList(menusString).forEach(menuId -> menuIds.add(menuId));
            });
            menuIds.forEach(id -> sb.append(id).append(","));
            if (sb.length() > 0) {
                //去掉最后一个逗号
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(NULL_MENU_ID);
            }
        }
        List<CodeAndName> codeAndNames = menuInfoService.selectMenuMap(sb.toString(), AdminConstant.MENU_ROOT_LEVEL);
        Map<String, String> menuMap = ConvertIdUtils.convertListToMap(codeAndNames);
        UserMenuAuthVo vo = new UserMenuAuthVo();
        vo.setUserId(relatedMenu.getId());
        vo.setUsername(username);
        vo.setFacePath(relatedMenu.getFacePath());
        vo.setMenuMap(menuMap);
        return new BaseResponse<>(CommonCode.DEFAULT_SUCCESS_CODE, vo);

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
    @OperationLog(action = LogConstant.ActionType.REGISTER, object = LogConstant.ActionObject.USER)
    public BaseResponse register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.encode(password);
        //注册时生成默认头像
        String facePath = UserConstant.DEFAULT_FACE;
        // 根据默认角色查询角色id
        Integer roleId = roleService.getRoleIdByType(UserConstant.DEFAULT_REGISTER_ROLE_TYPE);
        userInfoService.addNewUser(username, password, facePath, roleId);
        //插入用户相详细信息
        personDetailsService.insertDetails(username);
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
    @OperationLog(action = LogConstant.ActionType.ADD, object = LogConstant.ActionObject.USER)
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
        info.setIsEnable(UserStatus.ENABLE.getInfo());
        info.setUserType(UserType.USER.getInfo());
        userInfoService.addNewUser(info);
        //插入用户相详细信息
        personDetailsService.insertDetails(user.getUsername());
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  根据用户的id修改用户状态
     *
     * @param isEnable
     * @param id
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 15:22
     * @since v1.0
     **/
    @PutMapping("/status/{id}/{isEnable}")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER, info = LogConstant.ActionInfo.CHANGE_USER_STATUS)
    public BaseResponse changeUserStatus(@PathVariable("isEnable") Integer isEnable, @PathVariable("id") Integer id) {
        //在枚举转型方法中加入了参数类型检验
        UserStatus status = UserStatus.valueOfInteger(isEnable);
        userInfoService.changeStatus(status.getInfo(), id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：修改用户角色信息
     *
     * @param req
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 15:37
     * @since v1.0
     **/
    @PutMapping("/modify/role")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER, info = LogConstant.ActionInfo.UPDATE_USER_ROLE)
    public BaseResponse modifyBase(@RequestBody UserInfoReq req) {
        String role = ConvertIdUtils.getString(req.getRole());
        userInfoService.changeRole(role, req.getId());
        return BaseResponse.ok();
    }

    /**
     * 功能描述：使用默认密码进行重置当前用户的密码
     *
     * @param id
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 15:50
     * @since v1.0
     **/
    @PutMapping("/resetPass/{id}")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER, info = LogConstant.ActionInfo.RESET_USER_PASSWORD)
    public BaseResponse resetPass(@PathVariable("id") Integer id) {
        String password = MD5Utils.encode(UserConstant.DEFAULT_PASSWORD);
        userInfoService.changePassword(password, id);
        return BaseResponse.ok();
    }


    /**
     * 功能描述：  根据用户的id修改用户类型
     *
     * @param userType
     * @param id
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 15:22
     * @since v1.0
     **/
    @PutMapping("/type/{id}/{userType}")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER, info = LogConstant.ActionInfo.CHANGE_USER_TYPE)
    public BaseResponse changeUserStatus(@PathVariable("userType") String userType, @PathVariable("id") Integer id) {
        // 枚举转型方法中涵盖了参数类型校验
        UserType type = UserType.valueOfString(userType);
        userInfoService.changeUserTypeById(type.getInfo(), id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述： 根据用户名称获取用户信息
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/9 16:13
     * @since v1.0
     **/
    @GetMapping("/userBaseInfo")
    public BaseResponse<UserInfoVo> getUserBaseInfoByName(@RequestParam("username") String username) {
        UserInfoVo vo = userInfoService.getVoByUsername(username);
        return new BaseResponse<>(vo);
    }

    /**
     * 功能描述：  更新用户基础信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/9 16:55
     * @since v1.0
     **/
    @PutMapping("/update/userBase")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER,
            info = LogConstant.ActionInfo.UPDATE_USER_BASE)
    public BaseResponse updateUserBaseInfo(@RequestBody UserBaseReq req) {
        UserInfo info = new UserInfo();
        info.setUsername(req.getUsername());
        info.setFacePath(req.getFacePath());
        info.setId(req.getId());
        info.setIntroduce(req.getIntroduce());
        userInfoService.modifyUserBase(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：修改用户密码
     *
     * @param req
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/2/3 15:50
     * @since v1.0
     **/
    @PutMapping("/updatePass")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.USER,
            info = LogConstant.ActionInfo.UPDATE_USER_PASSWORD)
    public BaseResponse updatePassword(@RequestBody PasswordReq req) {
        UserInfo info = userInfoService.getUserByid(req.getId());
        String source = MD5Utils.encode(req.getSourcePass());
        //判断输入的原密码和数据库中的密码是否相同
        if (!Objects.equals(source, info.getPassword())) {
            throw new ErrorResponse(UserErrorCode.SOURCE_PASSWORD_ERROR);
        }
        //更新用户密码
        userInfoService.changePassword(MD5Utils.encode(req.getPassword()), req.getId());
        return BaseResponse.ok();
    }

}
