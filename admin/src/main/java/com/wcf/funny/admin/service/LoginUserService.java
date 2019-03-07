package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.LoginUser;
import com.wcf.funny.admin.vo.LoginUserVo;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 登录用户服务接口
 **/
public interface LoginUserService {

    /**
     * 功能描述：插入登录登出的信息
     *
     * @param username
     * @param ip
     * @author wangcanfeng
     * @time 2019/3/6 21:22
     * @since v1.0
     **/
    void insertLoginUser(String username, String ip);

    /**
     * 功能描述：插入登出信息
     *
     * @param username
     * @param ip
     * @author wangcanfeng
     * @time 2019/3/7 21:15
     * @since v1.0
     **/
    void insertLogoutUser(String username, String ip);

    /**
     * 功能描述：获取登录用户的分页列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/6 21:24
     * @since v1.0
     **/
    PageInfo<LoginUserVo> getLoginUserList(int currentPage, int pageSize);

    /**
     * 功能描述：根据时间查询用户登录记录信息
     *
     * @param currentPage
     * @param pageSize
     * @param start
     * @param end
     * @author wangcanfeng
     * @time 2019/3/6 21:35
     * @since v1.0
     **/
    PageInfo<LoginUserVo> getLoginUserList(int currentPage, int pageSize, int start, int end);
}
