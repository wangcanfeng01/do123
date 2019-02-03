package com.wcf.funny.admin.vo.req;

import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/2
 * @function 用户请求信息
 **/
@Data
public class UserInfoReq {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 人脸图片
     */
    private String face;
    /**
     * 角色列表
     */
    private List<Integer> role;
    /**
     * 用户等级
     */
    private Integer level;
    /**
     * 签名档
     */
    private String introduce;

}
