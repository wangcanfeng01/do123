package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.entity.UserRelatedMenu;
import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.UserInfoMapper;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.vo.UserInfoVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


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
     * 功能描述：  获取到用户信息列表
     *
     * @return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.UserInfoVo>
     * @author wangcanfeng
     * @time 2019/2/3 13:08
     * @since v1.0
     **/
    @Override
    public PageInfo<UserInfoVo> getUserList(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        try {
            List<UserInfo> infos = mapper.selectUserList();
            PageInfo<UserInfo> pageInfo = new PageInfo<>(infos);
            return convertPageInfo(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
        }
    }

    /**
     * @param info 用户信息
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    public boolean addNewUser(UserInfo info) {
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
     * @param facePath
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    @Override
    public boolean addNewUser(String name, String password, String facePath,Integer roleId) {
        UserInfo info = new UserInfo();
        info.setUsername(name);
        info.setPassword(password);
        info.setIntroduce(UserConstant.NULL_INRODUCE);
        info.setFacePath(facePath);
        info.setRegisterTime(FunnyTimeUtils.now());
        info.setUpdateTime(FunnyTimeUtils.now());
        info.setRole(roleId.toString());
        //初始0分
        info.setScore(0);
        //新注册的用户默认都是1级会员
        info.setUserLevel(1);
        info.setIsEnable(UserConstant.USER_ENABLE);
        return addNewUser(info);
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
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
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
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id启用/禁用当前用户
     *
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:15
     * @since v1.0
     **/
    @Override
    public void changeStatus(Integer isEnable, Integer id) {
        try {
            mapper.changeUserStatus(isEnable, id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.UPDATE_USER_ERROR, e);
        }
    }

    /**
     * 功能描述： 修改用户基础信息，不包含头像，密码什么的
     *
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:37
     * @since v1.0
     **/
    @Override
    public void modifyUserBase() {

    }

    /**
     * 功能描述： 根据id修改用户密码
     *
     * @param password
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 15:52
     * @since v1.0
     **/
    @Override
    public void changePassword(String password, Integer id) {
        try {
            mapper.changePassword(password, id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.UPDATE_USER_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id修改用户角色信息
     *
     * @param role
     * @param id
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/2/3 19:49
     * @since v1.0
     **/
    @Override
    public void changeRole(String role, Integer id) {
        try {
            mapper.changeRole(role, id);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.UPDATE_USER_ERROR, e);
        }
    }

    /**
     * @param name
     * @return String
     * @note 通过用户名获取用户头像
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    @Override
    public String getFaceByUsername(String name) {
        try {
            return mapper.getFaceByName(name);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
        }
    }
    
    
        /**
     * 功能描述: 根据用户名称查询用户拥有角色对应的菜单id字符串
     *
     * @param username
     * @return:java.util.List<java.lang.String>
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/2/13 11:38
     */
    @Override
    public UserRelatedMenu getMenusStringListByName(String username) {
        try {
            return mapper.getMenusStringListByName(username);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_USER_ERROR, e);
        }
    }

    /**
     * 功能描述：  将数据库信息转换成视图信息
     *
     * @param infos
     * @return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.MenuVo>
     * @author wangcanfeng
     * @time 2019/1/28 21:28
     * @since v1.0
     **/
    private PageInfo<UserInfoVo> convertPageInfo(PageInfo<UserInfo> infos) {
        List<UserInfo> userList = infos.getList();
        List<UserInfoVo> userVos = new ArrayList<>(userList.size());
        for (UserInfo user : userList) {
            UserInfoVo vo = new UserInfoVo();
            vo.setId(user.getId());
            vo.setRegisterTime(user.getRegisterTime());
            vo.setUpdateTime(user.getUpdateTime());
            vo.setIntroduce(user.getIntroduce());
            vo.setFacePath(user.getFacePath());
            vo.setUserAuth(ConvertIdUtils.getList(user.getRole()));
            if (ObjectUtils.isEmpty(user.getIsEnable()) || user.getIsEnable() != UserConstant.USER_ENABLE) {
                vo.setIsEnable("已禁用");
            } else {
                vo.setIsEnable("已启用");
            }
            vo.setUsername(user.getUsername());
            vo.setScore(user.getScore());
            vo.setUserLevel(user.getUserLevel());
            vo.setRoleInfos(user.getRoleInfos());
            userVos.add(vo);
        }
        PageInfo<UserInfoVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(infos.getTotal());
        pageInfo.setList(userVos);
        return pageInfo;
    }


}
