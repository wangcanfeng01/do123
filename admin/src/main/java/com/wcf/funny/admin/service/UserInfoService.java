package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.vo.UserInfoVo;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 用户信息服务
 **/
public interface UserInfoService {

    /**
     * 功能描述：  获取到用户信息列表
     *@author wangcanfeng
     *@time 2019/2/3 13:08
     *@since v1.0
     * @param
     *@return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.UserInfoVo>
     **/
    PageInfo<UserInfoVo> getUserList(Integer currentPage,Integer pageSize);

    /**
     * @param name
     * @param password
     * @param facePath
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    boolean addNewUser(String name,String password,String facePath);

    /**
     * @param info
     * @return boolean
     * @note 添加新的用户
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
    UserInfo getByUsername(String name) ;


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
     *@author wangcanfeng
     *@time 2019/2/3 15:15
     *@since v1.0
     * @param isEnable
     * @param id
     *@return void
     **/
    void changeStatus(Integer isEnable,Integer id);

    /**
     * 功能描述： 修改用户基础信息，不包含头像，密码什么的
     *@author wangcanfeng
     *@time 2019/2/3 15:37
     *@since v1.0
     * @param
     *@return void
     **/
    void modifyUserBase();


    /**
     * 功能描述：  根据id修改用户密码
     *@author wangcanfeng
     *@time 2019/2/3 15:52
     *@since v1.0
     * @param password
     * @param  id
     *@return void
     **/
    void changePassword(String password,Integer id);
}
