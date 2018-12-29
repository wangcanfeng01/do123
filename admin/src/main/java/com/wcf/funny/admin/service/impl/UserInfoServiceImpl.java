package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.UserInfoMapper;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.core.exception.PgSqlException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function 用户信息处理
 **/
@Service
@Log4j2
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserInfoMapper mapper;

    @Override
    public boolean addNewUser(UserInfo userInfo) {
        //判断这个用户是否存在
        UserInfo user = null;
        try {
            user = mapper.getUserByName(userInfo.getUsername());
        } catch (Exception e) {
            //在统一异常处理的地方打印日志信息
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR,e);
        }
        //判断用户是否已经存在
        if (null != user) {
            throw new UserException(UserErrorCode.USER_ALREADY_EXIST);
        }
        int result = 0;
        //插入新的用户信息
        try {
            result = mapper.insertUserInfo(userInfo);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.INSERT_USER_ERROR,e);
        }
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public UserInfo getByUsername(String name) {
        return null;
    }

    @Override
    public UserInfo getUserByid(Integer id) {
        return null;
    }
}
