package com.wcf.funny.admin.vo;

import com.wcf.funny.admin.entity.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 用户信息
 **/
@Data
public class UserInfoVo {
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
     * 注册的时间
     */
    private String registerTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 用户角色
     */
    private List<Integer> userAuth;

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
     * 是否禁用
     */
    private String isEnable;
}
