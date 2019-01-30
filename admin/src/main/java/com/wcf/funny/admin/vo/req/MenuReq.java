package com.wcf.funny.admin.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function 菜单请求信息
 **/
@Data
public class MenuReq {
    /**
     * id
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父菜单的id
     */
    private Integer parent;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单层级
     */
    private Integer level;
    /**
     * 备注信息
     */
    private String mark;
}
