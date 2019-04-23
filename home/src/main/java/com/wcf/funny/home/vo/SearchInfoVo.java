package com.wcf.funny.home.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/4/21
 * @function 查询结果视图信息
 **/
@Data
public class SearchInfoVo {
    /**
     * 查询结果标题
     */
    private String title;
    /**
     * 图片
     */
    private String image;
    /**
     * 简介
     */
    private String summary;
    /**
     * 作者或演员表
     */
    private String director;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 资源路径
     */
    private String url;
}
