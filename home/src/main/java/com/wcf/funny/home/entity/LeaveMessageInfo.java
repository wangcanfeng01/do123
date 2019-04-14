package com.wcf.funny.home.entity;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言入库信息
 **/
@Data
public class LeaveMessageInfo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 是否已读
     */
    private Integer isRead;
}
