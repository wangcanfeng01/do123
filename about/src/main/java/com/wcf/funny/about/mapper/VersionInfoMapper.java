package com.wcf.funny.about.mapper;

import com.wcf.funny.about.entity.VersionInfo;
import org.apache.ibatis.annotations.*;

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
    @Select("SELECT id, version, publish_time as publishTime, description, author, modify_time as modifyTime " +
            " FROM info_version WHERE author=#{author} ORDER BY publish_time desc")
    List<VersionInfo> getVersionListDesc(String author);


    /**
     * 功能描述：  获取版本信息列表，正序
     *
     * @author wangcanfeng
     * @time 2019/3/3 15:09
     * @since v1.0
     **/
    @Select("SELECT id, version, publish_time as publishTime, description, author, modify_time as modifyTime " +
            " FROM info_version WHERE author=#{author} ORDER BY publish_time")
    List<VersionInfo> getVersionList(String author);


    /**
     * 功能描述： 插入新的版本信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    @Insert("insert into info_version (version, publish_time, description, author, modify_time) " +
            " VALUES (#{version}, #{publishTime}, #{description}, #{author}, #{modifyTime});")
    void insertVersion(VersionInfo info);

    /**
     * 功能描述：  根据id删除版本信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    @Delete("delete from info_version where id=#{id}; ")
    void deleteVersionById(Integer id);

    /**
     * 功能描述：  修改版本信息内容
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/3 16:25
     * @since v1.0
     **/
    @Update("update info_version set version=#{version}, description=#{description}," +
            " author=#{author}, modify_time=#{modifyTime} where id=#{id}")
    void modifyVersion(VersionInfo info);
}
