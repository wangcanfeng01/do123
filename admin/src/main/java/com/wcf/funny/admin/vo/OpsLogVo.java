package com.wcf.funny.admin.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 操作日志展示的视图信息
 **/
@Data
public class OpsLogVo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 操作类型
     */
    private String actionType;

    /**
     * 操作名
     */
    private String authorName;

    /**
     * 人脸图片路径
     */
    private String facePath;

    /**
     * 操作对象
     */
    private String actionObject;

    /**
     * 操作者ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private String createTime;
    /**
     * 操作内容信息
     */
    private String actionInfo;
    /**
     * 操作详情
     */
    private String details;

    /**
     * 操作结果 1表示成功  0表示失败
     */
    private String actionResult;
}
