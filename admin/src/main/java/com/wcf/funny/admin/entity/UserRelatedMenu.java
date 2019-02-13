package com.wcf.funny.admin.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/13
 * @function 用户关联的菜单
 **/
@Data
public class UserRelatedMenu {
    /**
     * 用户名
     */
    private String username;
    /**
     * 人脸图片
     */
    private String facePath;
    /**
     * 菜单ids串列表
     */
    private List<String> menuIds;
}
