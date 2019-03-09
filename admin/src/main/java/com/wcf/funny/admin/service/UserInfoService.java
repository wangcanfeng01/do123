package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.entity.UserRelatedMenu;
import com.wcf.funny.admin.vo.UserInfoVo;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 用户信息服务
 **/
public interface UserInfoService {

    /**
     * 功能描述：  获取到用户信息列表
     *
     * @param
     * @return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.UserInfoVo>
     * @author wangcanfeng
     * @time 2019/2/3 13:08
     * @since v1.0
     **/
    PageInfo<UserInfoVo> getUserList(Integer currentPage, Integer pageSize);

    /**
     * @param name
     * @param password
     * @param facePath
     * @return boolean
     * @note 添加新的用户, 注册用户使用
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    boolean addNewUser(String name, String password, String facePath, Integer roleId);

    /**
     * @param info
     * @return boolean
     * @note 添加新的用户，系统管理员增加用户时使用
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    boolean addNewUser(UserInfo info);


    /**
     * @param name
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户名获取用户信息
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    UserInfo getByUsername(String name);

    /**
     * @param name
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户名获取用户视图信息
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    UserInfoVo getVoByUsername(String name);


    /**
     * @param id
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过评论中的用户id寻找用户
     * @author WCF
     * @time 2018/6/12 22:19
     * @since v1.0
     **/
    UserInfo getUserByid(Integer id);

    /**
     * 功能描述：  启用/禁用当前用户
     *
     * @param isEnable
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:15
     * @since v1.0
     **/
    void changeStatus(Integer isEnable, Integer id);

    /**
     * 功能描述： 修改用户基础信息，不包含头像，密码什么的
     *
     * @param
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:37
     * @since v1.0
     **/
    void modifyUserBase(UserInfo info);


    /**
     * 功能描述：  根据id修改用户密码
     *
     * @param password
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:52
     * @since v1.0
     **/
    void changePassword(String password, Integer id);

    /**
     * 功能描述：  根据id修改用户角色信息
     *
     * @param role
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 19:49
     * @since v1.0
     **/
    void changeRole(String role, Integer id);

    /**
     * @param name
     * @return String
     * @note 通过用户名获取用户头像
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    String getFaceByUsername(String name);

    /**
     * 功能描述: 根据用户名称查询用户拥有角色对应的菜单id字符串
     *
     * @param username
     * @return:java.util.List<java.lang.String>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/2/13 11:38
     */
    UserRelatedMenu getMenusStringListByName(String username);

    /**
     * 功能描述：根据用户的id修改用户的类型
     *
     * @param userType
     * @param id
     * @author wangcanfeng
     * @time 2019/3/9 12:36
     * @since v1.0
     **/
    void changeUserTypeById(String userType, Integer id);

    /**
     * 功能描述： 根据用户
     *
     * @param facePath
     * @param id
     * @author wangcanfeng
     * @time 2019/3/9 15:53
     * @since v1.0
     **/
    void changeUserFaceById(String facePath, Integer id);
}
