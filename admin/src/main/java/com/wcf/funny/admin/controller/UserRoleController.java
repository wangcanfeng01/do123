package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.SimpleRoleInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.admin.vo.RoleVo;
import com.wcf.funny.admin.vo.req.RoleReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
    @GetMapping("/role/{id}")
    public BaseResponse<UserRole> getRoleById(@PathVariable("id") Integer id) {
        UserRole role = roleService.getUserRoleById(id);
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
    @GetMapping("/roleList")
    public BaseResponse<List<RoleVo>> getRoleList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage) {
        PageInfo<RoleVo> roles = roleService.getRoleList(currentPage, pageSize);
        return new PageResponse<>(roles);
    }


    /**
     * 功能描述：
     *@author wangcanfeng
     *@time 2019/1/30 22:31
     *@since v1.0
     * @param
     *@return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>>
     **/
    @GetMapping("/roleList/simple")
    public BaseResponse<List<SimpleRoleInfo>> getMenuList() {
        List<SimpleRoleInfo> pageInfo = roleService.simpleRoleList();
        return new ListResponse<>(pageInfo);
    }

    /**
     * 功能描述： 新建角色
     *
     * @param req
     * @return com.wcf.funny.core.reponse.BaseResponse<com.github.pagehelper.PageInfo<com.wcf.funny.admin.entity.UserRole>>
     * @author wangcanfeng
     * @time 2019/1/20 17:38
     * @since v1.0
     **/
    @PostMapping("/add")
    @OperationLog(action = LogConstant.ActionType.ADD,object = LogConstant.ActionObject.ROLE,info = LogConstant.ActionInfo.ADD_ROLE)
    public BaseResponse getRoleList(@RequestBody RoleReq req) {
        UserRole role = new UserRole();
        role.setCreateTime(FunnyTimeUtils.nowUnix());
        role.setUpdateTime(FunnyTimeUtils.nowUnix());
        role.setDescription(req.getDesc());
        //如果权限不是空的，则进行权限设置
        role.setRoleAuth(getAuth(req.getAuth()));
        role.setRoleName(req.getName());
        role.setRoleType(req.getType());
        roleService.insertRole(role);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  更新角色信息
     *@author wangcanfeng
     *@time 2019/1/31 22:23
     *@since v1.0
     * @param req
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PutMapping("/modify")
    @OperationLog(action = LogConstant.ActionType.UPDATE,object = LogConstant.ActionObject.ROLE,info = LogConstant.ActionInfo.MODIFY_ROLE)
    public BaseResponse updateRole(@RequestBody RoleReq req){
        UserRole role = new UserRole();
        role.setId(req.getId());
        role.setUpdateTime(FunnyTimeUtils.nowUnix());
        role.setDescription(req.getDesc());
        //如果权限不是空的，则进行权限设置
        role.setRoleAuth(getAuth(req.getAuth()));
        role.setRoleName(req.getName());
        role.setRoleType(req.getType());
        roleService.updateRoleById(role);
        return BaseResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    @OperationLog(action = LogConstant.ActionType.DELETE,object = LogConstant.ActionObject.ROLE,info = LogConstant.ActionInfo.DELETE_ROLE)
    public BaseResponse deleteRole(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return BaseResponse.ok();
    }



    /**
     * 功能描述：将权限转成String，中间用逗号隔开
     *@author wangcanfeng
     *@time 2019/1/31 22:20
     *@since v1.0
     * @param auths
     *@return java.lang.String
     **/
    private String getAuth(List<Integer> auths){
        StringBuilder sb = new StringBuilder();
        if (!ObjectUtils.isEmpty(auths)) {
            auths.forEach(auth -> sb.append(auth).append(","));
            //将最后一位的,去掉
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
}
