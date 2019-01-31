package com.wcf.funny.admin.vo.req;

import com.wcf.funny.admin.entity.SimpleMenuInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/31
 * @function 角色请求信息
 **/
@Data
public class RoleReq {
    /**
     * id
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String type;
    /**
     * 角色权限菜单列表
     */
    private List<Integer> auth;
    /**
     * 角色描述
     */
    private String desc;
}
