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

    /**
     * 功能描述： 插入新的版本信息
     *
     * @param version
     * @param des
     * @param author
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    void insertVersion(String version, String des, String author);

    /**
     * 功能描述：  根据id删除版本信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    void deleteVersionById(Integer id);

    /**
     * 功能描述：  修改版本信息内容
     *
     * @param version
     * @param des
     * @param author
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 16:25
     * @since v1.0
     **/
    void modifyVersion(String version, String des, String author, Integer id);
}
