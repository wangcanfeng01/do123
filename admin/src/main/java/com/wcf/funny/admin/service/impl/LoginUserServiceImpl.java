package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.LoginUser;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.LoginUserMapper;
import com.wcf.funny.admin.service.LoginUserService;
import com.wcf.funny.admin.vo.LoginUserVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 登录用户服务接口实现
 **/
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * 功能描述：插入登录登出的信息
     *
     * @param user
     * @author wangcanfeng
     * @time 2019/3/6 21:22
     * @since v1.0
     **/
    @Override
    public void insertLoginUser(LoginUser user) {
        try {
            loginUserMapper.insertLoginUser(user);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.INSERT_LOGIN_ERROR, e);
        }
    }

    /**
     * 功能描述：获取登录用户的分页列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/6 21:24
     * @since v1.0
     **/
    @Override
    public PageInfo<LoginUserVo> getLoginUserList(int currentPage, int pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<LoginUser> loginUsers = loginUserMapper.getLoginUserList();
            return convertPage(loginUsers);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_LOGIN_ERROR, e);
        }
    }

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
    @Override
    public PageInfo<LoginUserVo> getLoginUserList(int currentPage, int pageSize, int start, int end) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<LoginUser> loginUsers = loginUserMapper.getLoginUserListByTime(start, end);
            return convertPage(loginUsers);
        } catch (Exception e) {
            throw new PgSqlException(UserErrorCode.SELECT_LOGIN_ERROR, e);
        }
    }

    /**
     * 功能描述：  转换页面信息
     *
     * @param loginUsers
     * @author wangcanfeng
     * @time 2019/3/6 22:40
     * @since v1.0
     **/
    private PageInfo<LoginUserVo> convertPage(List<LoginUser> loginUsers) {
        PageInfo<LoginUser> source = new PageInfo<>(loginUsers);
        PageInfo<LoginUserVo> pageInfo = new PageInfo<>();
        if (ObjectUtils.isEmpty(loginUsers)) {
            return pageInfo;
        } else {
            List<LoginUserVo> vos = new ArrayList<>(loginUsers.size());
            loginUsers.forEach(loginUser -> {
                LoginUserVo vo = new LoginUserVo();
                vo.setId(loginUser.getId());
                vo.setUsername(loginUser.getUsername());
                vo.setFacePath(loginUser.getFacePath());
                vo.setFacePath(loginUser.getFacePath());
                vo.setRoleInfos(loginUser.getRoleInfos());
                vo.setUserLevel(loginUser.getUserLevel());
                vo.setLoginTime(FunnyTimeUtils.getTimeByUnixTime(loginUser.getLoginTime()));
                vo.setLogoutTime(FunnyTimeUtils.getTimeByUnixTime(loginUser.getLogoutTime()));
                vo.setRemoteIp(loginUser.getRemoteIp());
                vo.setRemoteArea(loginUser.getRemoteArea());
                vos.add(vo);
            });
            pageInfo.setList(vos);
            pageInfo.setTotal(source.getTotal());
            return pageInfo;
        }
    }
}
