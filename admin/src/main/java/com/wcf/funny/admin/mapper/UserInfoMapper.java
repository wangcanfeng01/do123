package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function
 **/
@Mapper
public interface UserInfoMapper {

    /**
     *@note 根据用户名称查询用户信息
     *@author WCF
     *@time 2018/6/12 22:23
     *@since v1.0
     * @param name
     *@return com.wcf.hellohome.user.model.UserInfo
     **/
    @Select("select id, name as username,password,face_path as facePath,register_time as registerTime," +
            " role, introduce from info_user where name = #{name}")
    UserInfo getUserByName(String name) throws Exception ;

    /**
     *@note 根据id查询用户信息
     *@author WCF
     *@time 2018/6/12 22:36
     *@since v1.0
     * @param id
     *@return com.wcf.hellohome.user.model.UserInfo
     **/
    @Select("select id, name as username,password,face_path as facePath,register_time as registerTime, " +
            "role, introduce from info_user where id = #{id}")
    UserInfo getUserById(Integer id)throws Exception;

    /**
     *@note 插入用户信息
     *@author WCF
     *@time 2018/6/12 22:37
     *@since v1.0
     * @param info
     *@return int
     **/
    @Insert("insert into info_user (name,password,face_path,register_time,role,introduce) " +
            "VALUES (#{username},#{password},#{facePath},#{registerTime},#{role},#{introduce})")
    int insertUserInfo(UserInfo info)throws Exception;

    /**
     *@note 根据名称删除用户
     *@author WCF
     *@time 2018/6/12 22:38
     *@since v1.0
     * @param name
     *@return void
     **/
    @Delete("delete from info_user where name=#{name}")
    void deleteByName(String name)throws Exception;

    /**
     *@note 更新用户信息
     *@author WCF
     *@time 2018/6/12 22:38
     *@since v1.0
     * @param info
     *@return void
     **/
    @Update("update info_user set password=#{password},face_path=#{facePath},register_time=#{registerTime}," +
            "role=#{role},introduce=#{introduce} where name=#{username}")
    void updateByName(UserInfo info)throws Exception;
}