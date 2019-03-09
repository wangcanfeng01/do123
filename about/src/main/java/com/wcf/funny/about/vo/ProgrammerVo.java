package com.wcf.funny.about.vo;

import com.wcf.funny.core.entity.NameAndType;
import lombok.Data;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 开发者视图信息
 **/
@Data
public class ProgrammerVo {
    /**
     *  头像路径
     */
    private String facePath;
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
    private String workArea;

    /**
     * 联系方式
     */
    private String telephone;
    /**
     * 思维导图地址
     */
    private String mind;

    /**
     * 个人标签列表
     */
    private List<NameAndType> tags;
}
