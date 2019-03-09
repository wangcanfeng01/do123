package com.wcf.funny.home.vo;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.SimpleRoleInfo;
import com.wcf.funny.core.entity.CodeAndName;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 个人信息视图信息
 **/
@Data
public class PersonalInfoVo extends PersonDetailsInfo{
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 头像图片路径
     */
    private String facePath;

    /**
     * 注册时间
     */
    private String registerTime;
    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 用户有权限的角色列表
     */
    private List<SimpleRoleInfo> roleInfos;

    /**
     * 签名档
     */
    private String introduce;

    /**
     * 积分
     */
    private Integer score;

}
