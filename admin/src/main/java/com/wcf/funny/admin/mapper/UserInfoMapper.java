package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function
 **/
@Mapper
public interface UserInfoMapper {

    /**
     *@note 查询用户信息列表
     *@author WCF
     *@time 2018/6/12 22:23
     *@since v1.0
     *@return com.wcf.hellohome.user.model.UserInfo
     **/
    @Select("select id, name as username,password,face_path as facePath,register_time as registerTime, " +
            "update_time as updateTime, user_level as userLevel, role, role as userRole, introduce, score," +
            " is_enable as isEnable from info_user")
    @Results({@Result(property ="roleInfos",column="userRole"
            ,many =@Many(select ="com.wcf.funny.admin.mapper.UserRoleMapper.simpleRoleByIds"))})
    List<UserInfo> selectUserList();

    /**
     *@note 根据用户名称查询用户信息
     *@author WCF
     *@time 2018/6/12 22:23
     *@since v1.0
     * @param name
     *@return com.wcf.hellohome.user.model.UserInfo
     **/
    @Select("select id, name as username,password,face_path as facePath,register_time as registerTime, " +
            "update_time as updateTime, user_level as userLevel, role, introduce, score, is_enable as isEnable" +
            " from info_user where name = #{name}")
    UserInfo getUserByName(String name);


    /**
     *@note 根据用户名称查询用户头像
     *@author WCF
     *@time 2018/6/12 22:23
     *@since v1.0
     * @param name
     *@return String
     **/
    @Select("SELECT face_path from info_user where name = #{name}")
    String getFaceByName(String name);

    /**
     *@note 根据id查询用户信息
     *@author WCF
     *@time 2018/6/12 22:36
     *@since v1.0
     * @param id
     *@return com.wcf.hellohome.user.model.UserInfo
     **/
    @Select("select id, name as username,password, face_path as facePath, register_time as registerTime, " +
            "update_time as updateTime, user_level as userLevel, role, introduce, score," +
            " is_enable as isEnable from info_user where id = #{id}")
    UserInfo getUserById(Integer id);

    /**
     *@note 插入用户信息
     *@author WCF
     *@time 2018/6/12 22:37
     *@since v1.0
     * @param info
     *@return int
     **/
    @Insert("insert into info_user (name, password, face_path, register_time, update_time, user_level, role, introduce, score, is_enable) " +
            "VALUES (#{username},#{password},#{facePath},#{registerTime}, #{updateTime}, #{userLevel}, #{role},#{introduce},#{score},#{isEnable})")
    int insertUserInfo(UserInfo info);

    /**
     * 功能描述：  根据id启用/禁用当前用户
     *@author wangcanfeng
     *@time 2019/2/3 15:17
     *@since v1.0
     * @param isEnable
     * @param  id
     *@return void
     **/
    @Update("update info_user set is_enable=#{isEnable} where id=#{id}")
   void changeUserStatus(@Param("isEnable") Integer isEnable,@Param("id") Integer id);

    /**
     * 功能描述：  修改用户密码
     *@author wangcanfeng
     *@time 2019/2/3 15:52
     *@since v1.0
     * @param password
     *@return void
     **/
    @Update("update info_user set password=#{password} where id=#{id}")
    void changePassword(@Param("password")String password,@Param("id") Integer id);

    /**
     * 功能描述：  根据id修改用户角色信息
     *@author wangcanfeng
     *@time 2019/2/3 19:49
     *@since v1.0
     * @param role
    * @param id
     *@return void
     **/
    @Update("update info_user set role=#{role} where id=#{id}")
    void changeRole(@Param("role") String role,@Param("id") Integer id);
    
    /**
     * 功能描述: 根据用户名称查询用户拥有角色对应的菜单id字符串
     * @param username
     * @return:java.util.List<java.lang.String>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/2/13 11:38
     */
    @Select("SELECT name as username, role as userRole from info_user where name = #{name}")
    @Results({@Result(property ="menuIds",column="userRole"
            ,many =@Many(select ="com.wcf.funny.admin.mapper.UserRoleMapper.getRoleRelatedMenuByIds"))})
     UserRelatedMenu getMenusStringListByName(@Param("name") String username);
}
