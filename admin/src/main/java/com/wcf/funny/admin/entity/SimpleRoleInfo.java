package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 简单的角色信息
 **/
@Data
public class SimpleRoleInfo {
    /**
     * id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private String roleType;
}
