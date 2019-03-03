package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.SimpleRoleInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.vo.RoleVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2018/12/30
 * @function 用户角色查询
 **/
public interface UserRoleService {
    /**
     * 功能描述：  根据角色id获取角色信息
     *
     * @param id
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    UserRole getUserRoleById(Integer id);

    /**
     * 功能描述：  根据角色ids获取角色信息,支持批量查询
     *
     * @param ids
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    List<UserRole> getUserRoleByIds(String ids);

    /**
     * 功能描述：  插入角色信息
     *
     * @param role
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    int insertRole(UserRole role);

    /**
     * 功能描述：  获取角色列表
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.UserRole>
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    PageInfo<RoleVo> getRoleList(int page, int limit);


    /**
     * 功能描述：  获取角色列表
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.UserRole>
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    List<UserRole> getRoleList();

    /**
     * 功能描述：  查询简单的角色列表信息
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleRoleInfo>
     * @author wangcanfeng
     * @time 2019/2/3 14:47
     * @since v1.0
     **/
    List<SimpleRoleInfo> simpleRoleList();

    /**
     * 功能描述： 根据id删除角色
     *
     * @param id
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    int deleteRoleById(Integer id);

    /**
     * 功能描述：  根据id更新角色信息
     *
     * @param role
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    int updateRoleById(UserRole role);

    /**
     * 功能描述：  根据角色类型查询角色id
     *
     * @param roleType
     * @author wangcanfeng
     * @time 2019/3/3 11:33
     * @since v1.0
     **/
    Integer getRoleIdByType(String roleType);
}
