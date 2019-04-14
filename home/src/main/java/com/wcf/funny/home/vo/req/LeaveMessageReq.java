package com.wcf.funny.home.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言信息请求
 **/
@Data
public class LeaveMessageReq {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱地址
     */
    private String address;
    /**
     * 留言内容
     */
    private String info;
}
