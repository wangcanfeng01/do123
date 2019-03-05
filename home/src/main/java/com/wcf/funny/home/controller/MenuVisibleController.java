package com.wcf.funny.home.controller;

import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.ConvertIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author wangcanfeng
 * @time 2019/2/12
 * @function 菜单显隐控制器
 **/
@RestController
@RequestMapping("/ui/visibleMenu")
public class MenuVisibleController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    MenuInfoService menuInfoService;

//    /**
//     * 功能描述： 基础导航栏显隐控制
//     *@author wangcanfeng
//     *@time 2019/2/12 23:08
//     *@since v1.0
//     * @param
//     *@return com.wcf.funny.core.reponse.BaseResponse
//     **/
//    @GetMapping("/home")
//    public BaseResponse<Map<String,String>> getHomeVisibleMenu() {
//        UserInfo info = userInfoService.getByUsername("wcf");
//        if (ObjectUtils.isEmpty(info)) {
//            // 如果是查询不到的用户则直接报错
//            throw new UserException(UserErrorCode.LOGIN_USER_INFO_ERROR);
//        }
//        String roles = info.getRole();
//        List<UserRole> userRoles = userRoleService.getUserRoleByIds(roles);
//        HashSet<Integer> menuIds = new HashSet<>();
//        // 对角色做去重处理
//        userRoles.forEach(userRole -> {
//            ConvertIdUtils.getList(userRole.getRoleAuth()).forEach(id -> {
//                menuIds.add(id);
//            });
//        });
//
//        // 根据获取到具有权限的菜单id串，获取菜单map
//        List<CodeAndName> codeAndNames = menuInfoService.selectMenuMap("",1);
//        HashMap<String, String> menuVisibleMap = new HashMap<>();
//        codeAndNames.forEach(codeAndName -> menuVisibleMap.put(codeAndName.getCode(), codeAndName.getName()));
//        return new BaseResponse<>(menuVisibleMap);
//    }

}
