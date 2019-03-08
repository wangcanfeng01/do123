package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;
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
            throw new PgSqlException();
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
        }catch (Exception e){
            throw new PgSqlException();
        }
    }

    /**
     * 功能描述：查询用户列表
     *
     * @param username
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    @Override
    public List<PersonalInfo> getPersonByName(String username) {
       try {
           personDetailsMapper.getPersonByName(username);
       }catch (Exception e){
           throw new PgSqlException();
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
            personDetailsMapper.getProgrammerDetails();
        }catch (Exception e){
            throw new PgSqlException();
        }
    }
}
