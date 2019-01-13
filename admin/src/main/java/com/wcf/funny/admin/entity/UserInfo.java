package com.wcf.funny.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 头像图片名称
     */
    private String faceName;
    /**
     * 登录的时间
     */
    private String registerTime;
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 签名档
     */
    private String introduce;

    /**
     * 积分
     */
    private Integer score;
}
