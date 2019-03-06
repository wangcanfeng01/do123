package com.wcf.funny.admin.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 访客信息
 **/
@Data
public class LoginUser {
    /**
     * 登录信息序列号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像图片路径
     */
    private String facePath;
    /**
     * 用户角色列表
     */
    private List<SimpleRoleInfo> roleInfos;
    /**
     * 用户等级
     */
    private Integer userLevel;
    /**
     * 登录时间
     */
    private Integer loginTime;
    /**
     * 登出时间
     */
    private Integer logoutTime;
    /**
     * 登录者ip
     */
    private String remoteIp;
    /**
     * 登录者区域
     */
    private String remoteArea;

}
