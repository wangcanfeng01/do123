package com.wcf.funny.home.vo;

import com.wcf.funny.admin.entity.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人信息视图信息
 **/
@Data
public class PersonalInfoVo {
    /**
     * 用户id
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
    /**
     * 真实名称
     */
    private String personName;
    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 工作地址
     */
    private String wordArea;

    /**
     * 联系方式
     */
    private String telephone;
    /**
     * 思维导图地址
     */
    private String mind;

    /**
     * 个人标签
     */
    private String tags;
}
