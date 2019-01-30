package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.UserRoleMapper;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.admin.vo.RoleVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2018/12/30
 * @function 用户角色服务
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper roleMapper;

    /**
     * 功能描述：  根据角色id获取角色信息
     *
     * @param id
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Override
    public UserRole getUserInfoById(Integer id) {
        try {
            return roleMapper.getUserInfoById(id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_ROLR_ERROR, e);
        }
    }

    /**
     * 功能描述：  插入角色信息
     *
     * @param role
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Override
    public int insertRole(UserRole role) {
        try {
            return roleMapper.insertRole(role);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.INSETR_ROLR_ERROR, e);
        }
    }

    /**
     * 功能描述：  获取角色列表
     *
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.funny.admin.entity.UserRole>
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Override
    public PageInfo<RoleVo> getRoleList(int page, int limit) {
        PageHelper.startPage(page, limit);
        try {
            List<UserRole> roles = roleMapper.getRoleList();
            PageInfo<UserRole> pageInfo = new PageInfo<>(roles);
            return convertPageInfo(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_ROLR_ERROR, e);
        }
    }

    /**
     * 功能描述： 根据id删除角色
     *
     * @param id
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    @Override
    public int deleteRoleById(Integer id) {
        try {
            return roleMapper.deleteRoleById(id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.DELETE_ROLR_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id更新角色信息
     *
     * @param id
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    @Override
    public int updateRoleById(Integer id) {
        try {
            return roleMapper.updateRoleById(id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.UPDATE_ROLR_ERROR, e);
        }
    }

    /**
     * 功能描述：  将数据库信息转换成视图信息
     *@author wangcanfeng
     *@time 2019/1/28 21:28
     *@since v1.0
     * @param roles
     *@return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.MenuVo>
     **/
    private PageInfo<RoleVo> convertPageInfo(PageInfo<UserRole> roles){
        List<UserRole> roleList=roles.getList();
        List<RoleVo> roleVos=new ArrayList<>(roleList.size());
        for(UserRole role:roleList){
            RoleVo vo=new RoleVo();
            vo.setId(role.getId());
            vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(role.getCreateTime()));
            vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(role.getUpdateTime()));
            vo.setCreator(role.getRoleCreator());
            vo.setDescription(role.getDescription());
            vo.setRoleType(role.getRoleType());
            vo.setRoleName(role.getRoleName());
            vo.setRoleAuth(new ArrayList<>());
            roleVos.add(vo);
        }
        PageInfo<RoleVo> pageInfo=new PageInfo<>();
        pageInfo.setTotal(roles.getTotal());
        pageInfo.setList(roleVos);
        return pageInfo;
    }
}
