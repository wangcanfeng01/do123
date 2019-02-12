package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.entity.SimpleMenuInfo;
import com.wcf.funny.admin.vo.MenuVo;

import java.util.List;


/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function 菜单的service层
 **/
public interface MenuInfoService {
    /**
     * 功能描述：  插入菜单信息
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:50
     * @since v1.0
     **/
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
    PageInfo<MenuVo> selectMenu(Integer pageSize, Integer currentPage);

    /**
     * 功能描述：  菜单的简易信息用来展示
     *
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:05
     * @since v1.0
     **/
    List<SimpleMenuInfo> simpleMenuList();

    /**
     * 功能描述：  查询需要配置权限的菜单信息
     *
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:05
     * @since v1.0
     **/
    List<SimpleMenuInfo> simpleMenuAuthList();

    /**
     * 功能描述：根据菜单名称查询菜单信息
     *
     * @param name
     * @return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     * @author wangcanfeng
     * @time 2019/1/27 21:51
     * @since v1.0
     **/
    PageInfo<MenuVo> selectMenuByName(String name);

    /**
     * 功能描述：  根据id删除指定菜单信息
     *
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    void deleteMenuById(Integer id);

    /**
     * 功能描述：  根据id更新菜单信息
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    void updateMenuById(MenuInfo info);

    /**
     * 功能描述：  根据id更新菜单是否需要权限
     *
     * @param auth
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    void updateMenuAuthById(Integer auth, Integer id);

    /**
     * 功能描述：  根据id更新菜单是否为空
     *
     * @param type
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    void updateMenuTypeById(Integer type, Integer id);

    /**
     * 功能描述：  根据ids字符串查询菜单map
     *
     * @param ids
     * @return java.util.List<com.wcf.funny.core.entity.CodeAndName>
     * @author wangcanfeng
     * @time 2019/2/12 22:58
     * @since v1.0
     **/
    List<CodeAndName> selectMenuMapByIds(String ids);
}
