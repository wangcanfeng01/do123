package com.wcf.funny.admin.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 编辑用户请求信息
 **/
@Data
public class UserBaseReq {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 头像路径
     */
    private String facePath;
    /**
     * 自我简介
     */
    private String introduce;
}
