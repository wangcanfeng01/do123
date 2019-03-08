package com.wcf.funny.home.vo.req;

import com.wcf.funny.admin.entity.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人信息请求对象
 **/
@Data
public class PersonnalInfoReq {
    /**
     * 用户名
     */
    private String username;

    /**
     * 头像图片路径
     */
    private String facePath;
    /**
     * 签名档
     */
    private String introduce;
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
