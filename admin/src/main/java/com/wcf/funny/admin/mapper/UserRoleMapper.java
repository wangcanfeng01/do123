package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/20
 * @function 角色信息sql
 **/
@Mapper
public interface UserRoleMapper {

    /**
     * 功能描述：  根据角色id获取角色信息
     *
     * @param id
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Select("SELECT id, role_name as roleName, role_type as roleType, role_auth as roleAuth, role_creator as roleCreator," +
            " create_time as createTime, update_time as updateTime, description" +
            " FROM info_role where id=#{id}")
    UserRole getUserInfoById(Integer id);

    /**
     * 功能描述：  插入角色信息
     *
     * @param role
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Insert("insert into info_role (role_name, role_type, role_auth, role_creator, create_time, update_time, description)" +
            " VALUES (#{roleName}, #{roleType}, #{roleAuth}, #{roleCreator}, #{createTime}, #{updateTime}, #{description})")
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
    @Select("SELECT id, role_name as roleName, role_type as roleType, role_auth as roleAuth, role_creator as roleCreator," +
            " create_time as createTime, update_time as updateTime, description" +
            " FROM info_role")
    List<UserRole> getRoleList();

    /**
     * 功能描述： 根据id删除角色
     *
     * @param id
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    @Delete("delete from info_role where id=#{id}")
    int deleteRoleById(Integer id);

    /**
     * 功能描述：  根据id更新角色信息
     *
     * @param id
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    @Update("update info_role set role_name=#{roleName},role_type=#{roleType},role_auth=#{roleAuth}, " +
            " role_creator=#{roleCreator},update_time=#{updateTime}, description=#{description} where id=#{id}")
    int updateRoleById(Integer id);
}
