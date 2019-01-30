package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.admin.vo.RoleVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping("/role/{id}")
    public BaseResponse<UserRole> getRoleById(@PathVariable("id") Integer id) {
        UserRole role = roleService.getUserInfoById(id);
        return new BaseResponse<>(role);
    }

    /**
     * 功能描述：  根据分页信息获取角色列表
     *
     * @param pageSize
     * @param currentPage
     * @return com.wcf.funny.core.reponse.BaseResponse<com.github.pagehelper.PageInfo<com.wcf.funny.admin.entity.UserRole>>
     * @author wangcanfeng
     * @time 2019/1/20 17:38
     * @since v1.0
     **/
    @RequestMapping("/roleList")
    public BaseResponse<List<RoleVo>> getRoleList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage) {
        PageInfo<RoleVo> roles = roleService.getRoleList(currentPage, pageSize);
        return new PageResponse<>(roles);
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
    @RequestMapping("/add")
    public BaseResponse<PageInfo<UserRole>> getRoleList(@RequestBody UserRole role) {
        roleService.insertRole(role);
        return BaseResponse.ok();
    }
}
