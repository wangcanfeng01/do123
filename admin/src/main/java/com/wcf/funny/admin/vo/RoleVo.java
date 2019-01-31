package com.wcf.funny.admin.vo;

import com.wcf.funny.admin.entity.SimpleMenuInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/30
 * @function 角色信息视图
 **/
@Data
public class RoleVo {
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
     * 角色权限的菜单id列表
     */
    private List<Integer> roleAuth;
    /**
     * 角色权限菜单列表
     */
    private List<SimpleMenuInfo> menuInfos;
    /**
     * 角色创建者
     */
    private String creator;
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
