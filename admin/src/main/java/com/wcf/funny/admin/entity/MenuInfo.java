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
     * 菜单名称
     */
    private String menuName;
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
