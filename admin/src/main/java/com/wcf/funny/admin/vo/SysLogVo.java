package com.wcf.funny.admin.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/7
 * @function 系统日志视图信息
 **/
@Data
public class SysLogVo {
    /**
     * 日志名称
     */
    private String name;
    /**
     * 日志文件大小
     */
    private Integer size;
    /**
     * 最后修改时间
     */
    private String lastModifyTime;
}
