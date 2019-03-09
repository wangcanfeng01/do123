package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.PersonDetailsMapper;
import com.wcf.funny.admin.service.PersonDetailsService;
import com.wcf.funny.core.exception.PgSqlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人详细信息接口实现
 **/
@Service
public class PersonDetailsServiceImpl implements PersonDetailsService {

    @Autowired
    private PersonDetailsMapper personDetailsMapper;

    /**
     * 功能描述：插入详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:08
     * @since v1.0
     **/
    @Override
    public void insertDetails(PersonDetailsInfo detailsInfo) {
        try {
            personDetailsMapper.insertDetails(detailsInfo);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.INSERT_USER_DETAILS_ERROR, e);
        }
    }

    /**
     * 功能描述：插入详细信息
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/8 22:08
     * @since v1.0
     **/
    @Override
    public void insertDetails(String username) {
        PersonDetailsInfo detailsInfo = new PersonDetailsInfo();
        detailsInfo.setUsername(username);
        insertDetails(detailsInfo);
    }

    /**
     * 功能描述： 更新用户详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:39
     * @since v1.0
     **/
    @Override
    public void updateDetails(PersonDetailsInfo detailsInfo) {
        try {
            personDetailsMapper.updateDetails(detailsInfo);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.UPDATE_USER_DETAILS_ERROR, e);
        }
    }

    /**
     * 功能描述：根据用户名称查询用户个人信息
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    @Override
    public PersonDetailsInfo getPersonDetailByName(String username) {
        try {
            return personDetailsMapper.getPersonDetailByName(username);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_DETAILS_ERROR, e);
        }
    }

    /**
     * 功能描述：  获取开发者详情列表
     *
     * @author wangcanfeng
     * @time 2019/3/8 22:29
     * @since v1.0
     **/
    @Override
    public List<PersonDetailsInfo> getProgrammerDetails() {
        try {
           return personDetailsMapper.getProgrammerDetails();
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_DETAILS_ERROR, e);
        }
    }
}
