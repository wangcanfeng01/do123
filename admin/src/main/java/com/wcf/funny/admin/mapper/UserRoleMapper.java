package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.SimpleRoleInfo;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.mapper.provider.RoleInfoProvider;
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
    UserRole getUserRoleById(Integer id);

    /**
     * 功能描述：  根据角色id获取角色信息
     *
     * @param ids
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Select("SELECT id, role_name as roleName, role_type as roleType, role_auth as roleAuth, role_creator as roleCreator," +
            " create_time as createTime, update_time as updateTime, description" +
            " FROM info_role where id in (#{ids})")
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
    @Select("SELECT id, role_name as roleName, role_type as roleType, role_auth as roleAuth,role_auth as roleMenu," +
            " role_creator as roleCreator, create_time as createTime, update_time as updateTime, description" +
            " FROM info_role")
    @Results({@Result(property = "menuInfos", column = "roleMenu"
            , many = @Many(select = "com.wcf.funny.admin.mapper.MenuInfoMapper.simpleMenuByIds"))})
    List<UserRole> getRoleList();


    /**
     * 功能描述：  获取角色列表,仅携带需要权限的菜单
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.UserRole>
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Select("SELECT id, role_name as roleName, role_type as roleType, role_auth as roleAuth,role_auth as roleMenu," +
            " role_creator as roleCreator, create_time as createTime, update_time as updateTime, description" +
            " FROM info_role")
    @Results({@Result(property = "menuInfos", column = "roleMenu"
            , many = @Many(select = "com.wcf.funny.admin.mapper.MenuInfoMapper.simpleMenuWithAuthByIds"))})
    List<UserRole> getRoleListWithAuth();

    /**
     * 功能描述：  查询简单的菜单信息
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:07
     * @since v1.0
     **/
    @Select("SELECT id, role_name as roleName, role_type as roleType FROM info_role")
    List<SimpleRoleInfo> simpleRoleList();

    /**
     * 功能描述：  查询简单的角色列表信息
     *
     * @param ids
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleRoleInfo>
     * @author wangcanfeng
     * @time 2019/2/3 14:08
     * @since v1.0
     **/
    @SelectProvider(type = RoleInfoProvider.class, method = "simpleRoleByIdsSQL")
    List<SimpleRoleInfo> simpleRoleByIds(@Param("ids") String ids);

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
     * @param role
     * @return int
     * @author wangcanfeng
     * @time 2019/1/20 13:42
     * @since v1.0
     **/
    @Update("update info_role set role_name=#{roleName},role_type=#{roleType},role_auth=#{roleAuth}, " +
            " role_creator=#{roleCreator},update_time=#{updateTime}, description=#{description} where id=#{id}")
    int updateRoleById(UserRole role);


    /**
     * 功能描述：  根据角色id获取角色关联的菜单id串
     *
     * @param ids
     * @return com.wcf.funny.admin.entity.UserRole
     * @author wangcanfeng
     * @time 2019/1/20 13:39
     * @since v1.0
     **/
    @Select("SELECT role_auth as roleAuth FROM info_role where id in (#{ids})")
    List<String> getRoleRelatedMenuByIds(String ids);

    /**
     * 功能描述：  根据角色类型查询角色id
     *
     * @param roleType
     * @author wangcanfeng
     * @time 2019/3/3 11:33
     * @since v1.0
     **/
    @Select("SELECT id FROM info_role where role_type=#{roleType}")
    Integer getRoleIdByType(String roleType);
}
