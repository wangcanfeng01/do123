package com.wcf.funny.about.service;

import com.wcf.funny.about.entity.VersionInfo;
import com.wcf.funny.about.vo.VersionInfoVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本信息接口
 **/
public interface VersionInfoService {

    /**
     * 功能描述：  获取版本信息列表，倒序列
     *
     * @author wangcanfeng
     * @time 2019/3/3 15:09
     * @since v1.0
     **/
    List<VersionInfoVo> getVersionList(String author, Integer order);
}
