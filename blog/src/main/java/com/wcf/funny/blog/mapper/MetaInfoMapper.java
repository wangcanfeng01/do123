package com.wcf.funny.blog.mapper;

import com.wcf.funny.blog.entity.CategorySimple;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.mapper.provider.MetaInfoProvider;
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
     * @param type 标签类型
     * @note 根据类型查询meta列表
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Select("SELECT id, name, type, cover, description, modify_time as modifyTime, create_time as createTime, count " +
            " FROM info_metas WHERE type= #{type}" +
            " ORDER BY count desc")
    List<MetaInfo> getMetasByType(String type);

    /**
     * @param type 标签类型
     * @note 根据类型查询meta列表
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Select("SELECT id, name FROM info_metas WHERE type= #{type} ORDER BY count desc")
    List<CategorySimple> getMetasSimpleByType(String type);


    /**
     * @param id
     * @return void
     * @note 根据id删除标签或是关键词
     * @author WCF
     * @time 2018/6/13 22:27
     * @since v1.0
     **/
    @Delete("DELETE FROM info_metas WHERE id = #{id} AND count=0")
    void deleteMetaById(Integer id);

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
    void insertMeta(MetaInfo info);


    /**
     * @param info
     * @return void
     * @note 根据标签id更新信息
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET name=#{name},modify_time=#{modifyTime}, cover=#{cover}, description=#{description} WHERE id = #{id}")
    void updateMetaById(MetaInfo info);

    /**
     * @param names
     * @return void
     * @note 根据标签id减少标签的统计值, 批量更新
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @UpdateProvider(type = MetaInfoProvider.class, method = "reduceCountBatchByNameAndTypeSQL")
    void reduceMetaCountByNameAndTypeBatch(@Param("names") List<String> names,@Param("type") String type);

    /**
     * @param name
     * @return void
     * @note 根据标签名称和类型减少标签的统计值
     * @author WCF
     * @time 2018/6/13 22:25
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET count=count-1, modify_time=#{modifyTime} WHERE name = #{name} and type=#{type}")
    void reduceMetaCountByNameAndType(@Param("modifyTime") Integer time, @Param("name") String name,@Param("type")String type);

    /**
     * 功能描述： 根据标签id更新封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:03
     * @since v1.0
     **/
    @Update("UPDATE info_metas SET cover = #{cover} WHERE id = #{id}")
    void updateMetaCoverById(@Param("cover") String cover, @Param("id") Integer id);


    /**
     * 功能描述：  根据id获取标签信息
     *
     * @param name
     * @author wangcanfeng
     * @time 2019/2/23 16:27
     * @since v1.0
     **/
    @Select("SELECT id, name FROM info_metas WHERE name=#{name} and type=#{type}")
    MetaInfo getMetaByNameAndType(@Param("name") String name, @Param("type") String type);


    /**
     * 功能描述： 根据专题名称和类型增加统计值
     *@author wangcanfeng
     *@time 2019/2/23 19:55
     *@since v1.0
     * @param name
    * @param type
     **/
    @Update("UPDATE info_metas SET count=count+1, modify_time=#{modifyTime} WHERE name = #{name} and type=#{type}")
    void increaseMetaCountByNameAndType(@Param("modifyTime") Integer time,@Param("name")String name,@Param("type")String type);
}
