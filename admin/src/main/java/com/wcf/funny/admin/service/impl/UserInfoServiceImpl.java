package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.service.UserInfoService;

/**
 * @author WCF
 * @time 2018/12/26
 * @function
 **/
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public boolean addNewUser(UserInfo userInfo) throws PgSqlException, UserException {
        return false;
    }

    @Override
    public UserInfo getByUsername(String name) throws PgSqlException {
        return null;
    }

    @Override
    public UserInfo getUserByid(Integer id) throws PgSqlException {
        return null;
    }
}
