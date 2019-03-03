package com.wcf.funny.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 用户信息
 **/
@Data
public class UserInfo {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像图片路径
     */
    private String facePath;
    /**
     * 注册的时间
     */
    private String registerTime;
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 用户角色id
     */
    private String role;
    /**
     * 用户角色列表
     */
    private List<SimpleRoleInfo> roleInfos;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 签名档
     */
    private String introduce;

    /**
     * 积分
     */
    private Integer score;
    /**
     * 是否禁用
     */
    private Integer isEnable;
}
