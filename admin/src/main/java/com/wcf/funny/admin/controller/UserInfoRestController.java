package com.wcf.funny.admin.controller;

import com.wcf.funny.core.controller.BaseController;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserInfoRestController extends BaseController {

    @GetMapping("/get/login")
    public BaseResponse<String> getLoginUser(HttpServletRequest request, HttpServletResponse response) {
        setAllowOrigin(response);
        String username = request.getRemoteUser();
        return new BaseResponse<>(CommonCode.DEFAULT_SUCCESS_CODE, username);
    }
}
