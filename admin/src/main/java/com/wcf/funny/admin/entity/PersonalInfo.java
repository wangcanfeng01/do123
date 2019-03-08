package com.wcf.funny.admin.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人信息对象
 **/
@Data
public class PersonalInfo extends PersonDetailsInfo{
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 头像图片路径
     */
    private String facePath;
    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 用户有权限的角色列表
     */
    private List<SimpleRoleInfo> roleInfos;

    /**
     * 签名档
     */
    private String introduce;

    /**
     * 积分
     */
    private Integer score;

}
