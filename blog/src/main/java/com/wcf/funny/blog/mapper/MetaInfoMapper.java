package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.MetaInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/16
 * @function 关键词和分类信息的表操作
 **/
@Mapper
public interface MetaInfoMapper {
    /**
     * @param info
     * @return void
     * @note 插入新的meta
     * @author WCF
     * @time 2018/6/13 22:24
     * @since v1.0
     **/
    @Insert("INSERT INTO info_metas(name, type, cover, description, modify_time,create_time, count)" +
            "VALUES(#{name},#{type},#{cover},#{description},#{modifyTime},#{createTime},#{count});")
    void insertMeta(MetaInfo info) throws Exception;

    /**
     * @param type 标签类型
     * @note 根据类型查询meta列表
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Select("SELECT id, name, type, cover, description, modify_time as modifyTime, create_time as createTime, count " +
            " FROM info_metas WHERE type= #{type}" +
            " ORDER BY count")
    List<MetaInfo> getMetasByType(String type) throws Exception;

    /**
     * @param name
     * @param type
     * @return com.wcf.hellohome.read.model.WcfMetaInfo
     * @note 根据类型查询meta
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Select("SELECT id, name, type, cover, description, modify_time as modifyTime, create_time as createTime, count " +
            " FROM info_metas WHERE type= #{type} AND name=#{name}")
    MetaInfo getMetasByNameAndType(@Param("name") String name, @Param("type") String type) throws Exception;

    /**
     * @param info
     * @return void
     * @note 根据标签id更新信息
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET name=#{name},modify_time=#{modifyTime},description=#{description} WHERE id = #{id}")
    void updateMetaById(MetaInfo info) throws Exception;
    /**
     * @param info
     * @return void
     * @note 根据标签id更新信息
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET name=#{name},modify_time=#{modifyTime}," +
            "description=#{description},cover=#{cover} WHERE id = #{id}")
    void updateMetaById2(MetaInfo info) throws Exception;

    /**
     * @param time
     * @param count
     * @param id
     * @return void
     * @note 根据id更新统计值
     * @author WCF
     * @time 2018/6/13 22:26
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET count = #{count},modify_time=#{time} WHERE id = #{id}")
    void updateMetaCountById(@Param("time") Integer time, @Param("count") Integer count, @Param("id") Integer id) throws Exception;


    /**
     * @param id
     * @return void
     * @note 根据id删除标签或是关键词
     * @author WCF
     * @time 2018/6/13 22:27
     * @since v1.0
     **/
    @Delete("DELETE FROM info_metas WHERE id = #{id} AND count=0")
    void deleteMetaById(Integer id) throws Exception;
}
