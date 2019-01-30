package com.wcf.funny.admin.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/1/28
 * @function 菜单返回页面信息
 **/
@Data
public class MenuVo {
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
    private Integer parentNode;
    /**
     * 父菜单的名称
     */
    private String parentName;
    /**
     * 菜单层级
     */
    private Integer menuLevel;
    /**
     * 菜单路径
     */
    private String menuPath;
    /**
     * 菜单创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 备注信息
     */
    private String mark;
}
