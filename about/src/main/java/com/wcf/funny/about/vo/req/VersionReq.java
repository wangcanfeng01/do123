package com.wcf.funny.about.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本请求信息
 **/
@Data
public class VersionReq {
    /**
     * id序列号
     */
    private Integer id;
    /**
     * 版本号
     */
    private String version;
    /**
     * 详细信息
     */
    private String description;
}
