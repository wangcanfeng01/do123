package com.wcf.funny.admin.controller;

import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.controller.BaseController;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author wangcanfeng
 * @time 2019/1/11
 * @function 用户restful接口
 **/
@RestController
@RequestMapping("/ui")
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
}
