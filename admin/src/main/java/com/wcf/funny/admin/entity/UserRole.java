package com.wcf.funny.admin.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2018/12/30
 * @function 用户角色信息-数据库
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
     * 角色权限菜单列表
     */
    private List<SimpleMenuInfo> menuInfos;
    /**
     * 角色创建者
     */
    private String roleCreator;
    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 更新时间
     */
    private Integer updateTime;
    /**
     * 描述信息
     */
    private String description;

}
