package com.wcf.funny.admin.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function
 **/
@Data
public class PersonDetailsInfo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实名称
     */
    private String personName;
    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 工作地址
     */
    private String wordArea;

    /**
     * 联系方式
     */
    private String telephone;
    /**
     * 思维导图地址
     */
    private String mind;

    /**
     * 个人标签
     */
    private String tags;
}
