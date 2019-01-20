package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2018/12/30
 * @function 用户角色信息
 **/
@Data
public class UserRole {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色类型
     */
    private String roleType;
    /**
     * 角色权限
     */
    private String roleAuth;
    /**
     * 角色创建者
     */
    private String roleCreator;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 描述信息
     */
    private String description;

}
