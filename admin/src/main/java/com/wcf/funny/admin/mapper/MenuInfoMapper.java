package com.wcf.funny.admin.mapper;

import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.entity.SimpleMenuInfo;
import com.wcf.funny.admin.mapper.provider.MenuInfoProvider;
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
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:50
     * @since v1.0
     **/
    @Insert("insert into info_menu (menu_name, menu_code, menu_level, parent_node, need_auth, menu_type, path, creator, " +
            " create_time, modify_time, mark)  VALUES (#{menuName}, #{menuCode}, #{menuLevel}, #{pNode}, #{needAuth}, #{menuType}," +
            " #{path}, creator=#{creator}, #{createTime}, #{modifyTime}, #{mark})")
    void insertMenu(MenuInfo info);

    /**
     * 功能描述： 查询菜单列表
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     * @author wangcanfeng
     * @time 2019/1/27 21:51
     * @since v1.0
     **/
    @Select("SELECT a.id, a.menu_name as menuName, a.menu_code as menuCode, a.menu_level as menuLevel, " +
            " a.parent_node as pNode, b.menu_name as pName, a.need_auth as needAuth, a.menu_type as menuType, " +
            "a.path, a.creator,  a.create_time as createTime, a.modify_time as modifyTime, a.mark  " +
            "FROM info_menu as a LEFT JOIN info_menu as b ON a.parent_node=b.id")
    List<MenuInfo> selectMenu();


    /**
     * 功能描述：  查询简单的菜单信息
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:07
     * @since v1.0
     **/
    @Select("SELECT id, menu_name as menuName, path as menuPath FROM info_menu")
    List<SimpleMenuInfo> simpleMenu();

    /**
     * 功能描述：  查询需要权限的菜单信息
     *
     * @param
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:07
     * @since v1.0
     **/
    @Select("SELECT id, menu_name as menuName, path as menuPath FROM info_menu WHERE need_auth=1")
    List<SimpleMenuInfo> authMenu();

    /**
     * 功能描述：  根据id查询简单的菜单列表,无论是否需要权限
     *
     * @param ids
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/2/3 14:44
     * @since v1.0
     **/
    @SelectProvider(type = MenuInfoProvider.class, method = "simpleMenuByIdsSQL")
    List<SimpleMenuInfo> simpleMenuByIds(@Param("ids") String ids);

    /**
     * 功能描述：  根据id查询简单的菜单列表,仅查询需要权限的
     *
     * @param ids
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/2/3 14:44
     * @since v1.0
     **/
    @SelectProvider(type = MenuInfoProvider.class, method = "simpleMenuWithAuthByIdsSQL")
    List<SimpleMenuInfo> simpleMenuWithAuthByIds(@Param("ids") String ids);


    /**
     * 功能描述：根据菜单名称查询菜单信息
     *
     * @param name
     * @return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     * @author wangcanfeng
     * @time 2019/1/27 21:51
     * @since v1.0
     **/
    @Select("SELECT a.id, a.menu_name as menuName, a.menu_level as menuLevel, a.parent_node as pNode, b.parent_name as pName," +
            " a.path, a.creator, a.create_time as createTime, a.modify_time as modifyTime, a.mark " +
            " FROM info_menu as a LEFT JOIN (select id, menu_name as parent_name from info_menu) as b ON a.parent_node=b.id ")
    List<MenuInfo> selectMenuByName(@Param("menuName") String name);

    /**
     * 功能描述：  根据id删除指定菜单信息
     *
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Delete("delete from info_menu where id=#{id}")
    void deleteMenuById(@Param("id") Integer id);

    /**
     * 功能描述：  根据id更新菜单信息
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Update("update info_menu set menu_name=#{menuName}, menu_code=#{menuCode}, menu_level=#{menuLevel}, parent_node=#{pNode}, " +
            " path=#{path}, creator=#{creator}, modify_time=#{modifyTime}, mark=#{mark} where id=#{id}")
    void updateMenuById(MenuInfo info);

    /**
     * 功能描述：  根据id更新菜单信息
     *
     * @param needAuth
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Update("update info_menu set need_auth=#{needAuth} where id=#{id}")
    void updateMenuAuthById(@Param("needAuth") Integer needAuth, @Param("id") Integer id);

    /**
     * 功能描述：  根据id更新菜单为空与否
     *
     * @param menuType
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Update("update info_menu set menu_type=#{menuType}  where id=#{id}")
    void updateMenuTypeById(@Param("menuType") Integer menuType, @Param("id") Integer id);

    /**
     * 功能描述：  根据ids字符串查询菜单map
     *
     * @param ids
     * @return java.util.List<com.wcf.funny.core.entity.CodeAndName>
     * @author wangcanfeng
     * @time 2019/2/12 22:58
     * @since v1.0
     **/
    @Select("SELECT menu_name as name, menu_code as code from info_menu where id in (#{ids})")
    List<CodeAndName> selectMenuMapByIds(String ids);

}
