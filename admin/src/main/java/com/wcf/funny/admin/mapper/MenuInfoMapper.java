package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.MenuInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function 菜单持久化映射
 **/
@Mapper
public interface MenuInfoMapper {

    /**
     * 功能描述：  插入菜单信息
     *@author wangcanfeng
     *@time 2019/1/27 21:50
     *@since v1.0
     * @param info
     *@return void
     **/
    @Insert("insert into info_menu (menu_name, menu_level, path, creator, create_time, modify_time, mark) " +
            "VALUES (#{menuName}, #{menuLevel}, #{path}, creator=#{creator}, #{createTime}, #{modifyTime}, #{mark})")
    void insertMenu(MenuInfo info);

    /**
     * 功能描述： 查询菜单列表
     *@author wangcanfeng
     *@time 2019/1/27 21:51
     *@since v1.0
     * @param
     *@return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     **/
    @Select("SELECT id, menu_name as menuName, menu_level as menuLevel, path, creator, create_time as createTime," +
            " modify_time as modifyTime, mark " +
            " FROM info_menu")
    List<MenuInfo> selectMenu();

    /**
     * 功能描述：根据菜单名称查询菜单信息
     *@author wangcanfeng
     *@time 2019/1/27 21:51
     *@since v1.0
     * @param name
     *@return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     **/
    @Select("SELECT id, menu_name as menuName, menu_level as menuLevel, path, creator, create_time as createTime," +
            " modify_time as modifyTime, mark " +
            " FROM info_menu where menu_name=#{menuName}")
    List<MenuInfo> selectMenuByName(@Param("menuName") String name);

    /**
     * 功能描述：  根据id删除指定菜单信息
     *@author wangcanfeng
     *@time 2019/1/27 21:52
     *@since v1.0
     * @param id
     *@return void
     **/
    @Delete("delete from info_menu where id=#{id}")
    void deleteMenuById(@Param("id") Integer id);

    /**
     * 功能描述：  根据id更新菜单信息
     *@author wangcanfeng
     *@time 2019/1/27 21:52
     *@since v1.0
     * @param info
     *@return void
     **/
    @Update("update info_menu set menu_name=#{menuName},menu_level=#{menuLevel},path=#{path}, creator=#{creator}," +
            " modify_time=#{modifyTime},mark=#{mark} where id=#{id}")
    void updateMenuById(MenuInfo info);
}
