package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.LoginUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 登录用户数据库操作接口
 **/
@Mapper
public interface LoginUserMapper {
    /**
     * 功能描述：插入登录登出的信息
     *
     * @param user
     * @author wangcanfeng
     * @time 2019/3/6 21:22
     * @since v1.0
     **/
    @Insert("insert into info_login (username, login_time, logout_time, remote_ip, remote_area)" +
            " VALUES (#{username}, #{loginTime}, #{logoutTime}, #{remoteIp}, #{remoteArea})")
    void insertLoginUser(LoginUser user);

    /**
     * 功能描述：获取登录用户的分页列表
     *
     * @author wangcanfeng
     * @time 2019/3/6 21:24
     * @since v1.0
     **/
    @Select("SELECT a.id, a.username, a.login_time as loginTime, a.logout_time as logoutTime, a.remote_ip as remoteIp, " +
            "  a.remote_area as remoteArea, b.face_path as facePath, b.role as userRole,b.user_level as userLevel " +
            " FROM info_login as a LEFT JOIN info_user as b ON a.username=b.name ORDER BY a.id desc")
    @Results({@Result(property = "roleInfos", column = "userRole"
            , many = @Many(select = "com.wcf.funny.admin.mapper.UserRoleMapper.simpleRoleByIds"))})
    List<LoginUser> getLoginUserList();

    /**
     * 功能描述：根据时间查询用户登录记录信息
     *
     * @param start
     * @param end
     * @author wangcanfeng
     * @time 2019/3/6 21:35
     * @since v1.0
     **/
    @Select("SELECT a.id, a.username, a.login_time as loginTime, a.logout_time as logoutTime, a.remote_ip as remoteIp, " +
            "  a.remote_area as remoteArea, b.face_path as facePath, b.role as userRole,b.user_level as userLevel " +
            " FROM info_login as a LEFT JOIN info_user as b ON a.username=b.name WHERE a.login_time>#{start} " +
            " and a.login_time<#{end} ORDER BY a.id desc")
    @Results({@Result(property = "roleInfos", column = "userRole"
            , many = @Many(select = "com.wcf.funny.admin.mapper.UserRoleMapper.simpleRoleByIds"))})
    List<LoginUser> getLoginUserListByTime(@Param("start") Integer start, @Param("end") Integer end);
}
