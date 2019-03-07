package com.wcf.funny.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.AdminConstant;
import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.LoginUser;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.admin.mapper.LoginUserMapper;
import com.wcf.funny.admin.service.LoginUserService;
import com.wcf.funny.admin.vo.LoginUserVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 登录用户服务接口实现
 **/
@Service
@Log4j2
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 功能描述：插入登录登出的信息
     *
     * @param username
     * @param ip
     * @author wangcanfeng
     * @time 2019/3/6 21:22
     * @since v1.0
     **/
    @Override
    public void insertLoginUser(String username, String ip) {
        try {
            //记录访客信息
            LoginUser user = new LoginUser();
            user.setUsername(username);
            user.setLoginTime(FunnyTimeUtils.nowUnix());
            user.setRemoteIp(ip);
            //查询登录用户的地域信息
            user.setRemoteArea(getArea(ip));
            loginUserMapper.insertLoginUser(user);
        } catch (Exception e) {
            log.error(UserErrorCode.INSERT_LOGIN_ERROR.getReason(), e);
        }
    }

    /**
     * 功能描述：  根据ip去查询用户的区域
     *
     * @param ip
     * @author wangcanfeng
     * @time 2019/3/7 21:31
     * @since v1.0
     **/
    private String getArea(String ip) {
        if (AdminConstant.LOCAL_IP.equals(ip) || AdminConstant.LOCALHOST_IP.equals(ip)) {
            return AdminConstant.LOCAL_AREA;
        }
        String area = "";
        try {
            // 这个接口出现失败的概率目前有点高
            String result = restTemplate.getForObject(UserConstant.REQUEST_FOR_AREA_ADDRESS + "?ip=" + ip, String.class);
            JSONObject object = JSON.parseObject(result);
            if (!ObjectUtils.isEmpty(object.getJSONObject("data"))) {
                JSONObject data = object.getJSONObject("data");
                String country = data.getString("country");
                String region = data.getString("region");
                String city = data.getString("city");
                area = country + "." + region + "." + city;
            }
        } catch (Exception e) {
            log.error("select the user's area has been failed,details:" + e.getMessage());
        }
        return area;
    }

    /**
     * 功能描述：插入登出信息
     *
     * @param username
     * @param ip
     * @author wangcanfeng
     * @time 2019/3/7 21:15
     * @since v1.0
     **/
    @Override
    public void insertLogoutUser(String username, String ip) {
        try {
            //记录访客信息
            LoginUser user = new LoginUser();
            user.setUsername(username);
            user.setLogoutTime(FunnyTimeUtils.nowUnix());
            user.setRemoteIp(ip);
            //查询登录用户的地域信息
            //查询登录用户的地域信息
            user.setRemoteArea(getArea(ip));
            loginUserMapper.insertLoginUser(user);
        } catch (Exception e) {
            log.error(UserErrorCode.INSERT_LOGIN_ERROR.getReason(), e);
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
                if(ObjectUtils.isEmpty(loginUser.getLoginTime())){
                    vo.setLoginTime("--");
                }else {
                    vo.setLoginTime(FunnyTimeUtils.getTimeByUnixTime(loginUser.getLoginTime()));
                }
                if(ObjectUtils.isEmpty(loginUser.getLogoutTime())){
                    vo.setLogoutTime("--");
                }else {
                    vo.setLogoutTime(FunnyTimeUtils.getTimeByUnixTime(loginUser.getLogoutTime()));
                }
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
