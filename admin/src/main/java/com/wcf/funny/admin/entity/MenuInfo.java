package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function 菜单信息
 **/
@Data
public class MenuInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单的id
     */
    private Integer pNode;
    /**
     * 父菜单的名称
     */
    private String pName;
    /**
     * 菜单层级
     */
    private Integer menuLevel;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单创建者
     */
    private String creator;

    /**
     * 菜单是否需要权限
     */
    private Integer needAuth;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 修改时间
     */
    private Integer modifyTime;
    /**
     * 备注信息
     */
    private String mark;
}
