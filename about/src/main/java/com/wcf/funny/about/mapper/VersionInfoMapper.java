package com.wcf.funny.about.mapper;

import com.wcf.funny.about.entity.VersionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本信息数据库接口
 **/
@Mapper
public interface VersionInfoMapper {

    /**
     * 功能描述：  获取版本信息列表，倒序列
     *
     * @author wangcanfeng
     * @time 2019/3/3 15:09
     * @since v1.0
     **/
    @Select("SELECT id, version, publish_time, description, author, modify_time " +
            "FROM info_version author=#{author} ORDER BY publish_time desc")
    List<VersionInfo> getVersionListDesc(String author);


    /**
     * 功能描述：  获取版本信息列表，正序
     *
     * @author wangcanfeng
     * @time 2019/3/3 15:09
     * @since v1.0
     **/
    @Select("SELECT id, version, publish_time, description, author, modify_time " +
            "FROM info_version author=#{author} ORDER BY publish_time")
    List<VersionInfo> getVersionList(String author);


}
