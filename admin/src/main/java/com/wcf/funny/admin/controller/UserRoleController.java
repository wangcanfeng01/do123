package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcanfeng
 * @time 2019/1/20
 * @function 用户角色控制器
 **/
@RestController
@RequestMapping("/ui/role")
public class UserRoleController {

    @Autowired
    private UserRoleService roleService;

    /**
     * 功能描述：  根据id获取角色信息
     *
     * @param id
     * @return com.wcf.funny.core.reponse.BaseResponse<com.wcf.funny.admin.entity.UserRole>
     * @author wangcanfeng
     * @time 2019/1/20 17:37
     * @since v1.0
     **/
    @RequestMapping("/get/role/{id}")
    public BaseResponse<UserRole> getRoleById(@PathVariable("id") Integer id) {
        UserRole role = roleService.getUserInfoById(id);
        return new BaseResponse<>(role);
    }

    /**
     * 功能描述：  根据分页信息获取角色列表
     *
     * @param page
     * @param limit
     * @return com.wcf.funny.core.reponse.BaseResponse<com.github.pagehelper.PageInfo<com.wcf.funny.admin.entity.UserRole>>
     * @author wangcanfeng
     * @time 2019/1/20 17:38
     * @since v1.0
     **/
    @RequestMapping("/get/roleList")
    public BaseResponse<PageInfo<UserRole>> getRoleList(Integer page, Integer limit) {
        PageInfo<UserRole> roles = roleService.getRoleList(page, limit);
        return new BaseResponse<>(roles);
    }

    /**
     * 功能描述：  新建角色
     *
     * @param role
     * @return com.wcf.funny.core.reponse.BaseResponse<com.github.pagehelper.PageInfo<com.wcf.funny.admin.entity.UserRole>>
     * @author wangcanfeng
     * @time 2019/1/20 17:38
     * @since v1.0
     **/
    @RequestMapping("/add/role")
    public BaseResponse<PageInfo<UserRole>> getRoleList(@RequestBody UserRole role) {
        roleService.insertRole(role);
        return BaseResponse.ok();
    }
}
