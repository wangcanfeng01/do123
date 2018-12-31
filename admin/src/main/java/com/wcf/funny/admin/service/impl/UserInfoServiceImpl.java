package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.UserInfoMapper;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
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

    /**
     * @param name
     * @param password
     * @param faceName
     * @param introduce
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    public boolean addNewUser(String name, String password, String faceName, String introduce) {
        UserInfo info = new UserInfo();
        info.setUsername(name);
        info.setPassword(password);
        info.setIntroduce(introduce);
        info.setFaceName(faceName);
        info.setRegisterTime(FunnyTimeUtils.nowString());
        info.setRole(UserConstant.DEFAULT_ROLE);
        //判断这个用户是否存在
        UserInfo user = null;
        try {
            user = mapper.getUserByName(info.getUsername());
        } catch (Exception e) {
            //在统一异常处理的地方打印日志信息
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
        }
        //判断用户是否已经存在
        if (null != user) {
            throw new UserException(UserErrorCode.USER_ALREADY_EXIST);
        }
        int result = 0;
        //插入新的用户信息
        try {
            result = mapper.insertUserInfo(info);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.INSERT_USER_ERROR, e);
        }
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * @param name
     * @param password
     * @param faceName
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    @Override
    public boolean addNewUser(String name, String password, String faceName) {
        return addNewUser(name, password, faceName, UserConstant.NULL_INRODUCE);
    }

    /**
     * @param name
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户名获取用户信息
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    @Override
    public UserInfo getByUsername(String name) {
        try {
            return mapper.getUserByName(name);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR,e);
        }
    }


    /**
     * @param id
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过评论中的用户id寻找用户
     * @author WCF
     * @time 2018/6/12 22:19
     * @since v1.0
     **/
    @Override
    public UserInfo getUserByid(Integer id) {
        try {
            return mapper.getUserById(id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR,e);
        }
    }
}
