package com.wcf.funny.home.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言视图信息
 **/
@Data
public class LeaveMessageVo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 留言内容
     */
    private String message;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 留言时间
     */
    private String time;

    /**
     * 是否已读
     */
    private Integer isRead;
}
