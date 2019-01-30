package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/1/30
 * @function 简单的菜单信息
 **/
@Data
public class SimpleMenuInfo {
    /**
     * id
     */
   private Integer id;

    /**
     * 菜单名称
     */
   private String menuName;

    /**
     * 菜单路径
     */
   private String menuPath;

}
