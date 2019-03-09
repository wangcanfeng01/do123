package com.wcf.funny.admin.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 用户密码请求
 **/
@Data
public class PasswordReq {
    /**
     * 账户原密码
     */
    private String sourcePass;
    /**
     * 账户新密码
     */
    private String password;
    /**
     * 账户id
     */
    private Integer id;
}
