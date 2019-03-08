package com.wcf.funny.admin.service;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人详情操作接口
 **/
public interface PersonDetailsService {

    /**
     * 功能描述：插入详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:08
     * @since v1.0
     **/
    void insertDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述：插入详细信息
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/8 22:08
     * @since v1.0
     **/
    void insertDetails(String username);
    /**
     * 功能描述： 更新用户详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:39
     * @since v1.0
     **/
    void updateDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述：查询用户列表
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    List<PersonalInfo> getPersonByName(String username);

    /**
     * 功能描述：  获取开发者详情列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/8 22:29
     * @since v1.0
     **/
    List<PersonDetailsInfo> getProgrammerDetails();
}
