package com.wcf.funny.admin.vo;

import lombok.Data;

import java.util.Map;

@Data
public class UserMenuAuthVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 人脸图片
     */
    private String facePath;
    /**
     * 菜单名称和编码的map
     */
    private Map<String, String> menuMap;
}
