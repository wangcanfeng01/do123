package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.entity.SimpleMenuInfo;
import com.wcf.funny.admin.exception.errorcode.MenuErrorCode;
import com.wcf.funny.admin.mapper.MenuInfoMapper;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.admin.vo.MenuVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function
 **/
@Service
public class MenuInfoServiceImpl implements MenuInfoService {

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    /**
     * 功能描述：  插入菜单信息
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:50
     * @since v1.0
     **/
    @Override
    public void insertMenu(MenuInfo info) {
        try {
            menuInfoMapper.insertMenu(info);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.INSERT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述： 查询菜单列表
     *
     * @return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     * @author wangcanfeng
     * @time 2019/1/27 21:51
     * @since v1.0
     **/
    @Override
    public PageInfo<MenuVo> selectMenu(Integer pageSize, Integer currentPage) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<MenuInfo> menuInfos = menuInfoMapper.selectMenu();
            if (ObjectUtils.isEmpty(menuInfos)) {
                menuInfos = new ArrayList<>();
            }
            PageInfo<MenuInfo> pageInfo = new PageInfo<>(menuInfos);
            return convertPageInfo(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.SELECT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  菜单的简易信息用来展示
     *
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:05
     * @since v1.0
     **/
    @Override
    public List<SimpleMenuInfo> simpleMenuList() {
        try {
            return menuInfoMapper.simpleMenu();
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.SELECT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询需要配置权限的菜单信息
     *
     * @return java.util.List<com.wcf.funny.admin.entity.SimpleMenuInfo>
     * @author wangcanfeng
     * @time 2019/1/30 22:05
     * @since v1.0
     **/
    @Override
    public List<SimpleMenuInfo> simpleMenuAuthList() {
        try {
            return menuInfoMapper.authMenu();
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.SELECT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：根据菜单名称查询菜单信息
     *
     * @param name
     * @return java.util.List<com.wcf.funny.admin.entity.MenuInfo>
     * @author wangcanfeng
     * @time 2019/1/27 21:51
     * @since v1.0
     **/
    @Override
    public PageInfo<MenuVo> selectMenuByName(String name) {
        try {
            List<MenuInfo> menuInfos = menuInfoMapper.selectMenuByName(name);
            if (ObjectUtils.isEmpty(menuInfos)) {
                menuInfos = new ArrayList<>();
            }
            PageInfo<MenuInfo> pageInfo = new PageInfo<>(menuInfos);
            return convertPageInfo(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.SELECT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id删除指定菜单信息
     *
     * @param id
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Override
    public void deleteMenuById(Integer id) {
        try {
            menuInfoMapper.deleteMenuById(id);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.DELETE_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id更新菜单信息
     *
     * @param info
     * @return void
     * @author wangcanfeng
     * @time 2019/1/27 21:52
     * @since v1.0
     **/
    @Override
    public void updateMenuById(MenuInfo info) {
        try {
            menuInfoMapper.updateMenuById(info);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.UPDATE_MENU_ERROR, e);
        }
    }

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
    @Override
    public void updateMenuAuthById(Integer auth, Integer id) {
        try {
            menuInfoMapper.updateMenuAuthById(auth, id);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.UPDATE_MENU_ERROR, e);
        }
    }

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
    @Override
    public void updateMenuTypeById(Integer type, Integer id) {
        try {
            menuInfoMapper.updateMenuTypeById(type, id);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.UPDATE_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据ids字符串和菜单层级查询菜单map以及不需要的权限的菜单
     *
     * @param ids
     * @param level
     * @return java.util.List<com.wcf.funny.core.entity.CodeAndName>
     * @author wangcanfeng
     * @time 2019/2/12 22:58
     * @since v1.0
     **/
    @Override
    public List<CodeAndName> selectMenuMap(String ids, Integer level) {
        try {
            return menuInfoMapper.selectMenuMap(ids, level);
        } catch (Exception e) {
            throw new PgSqlException(MenuErrorCode.SELECT_MENU_ERROR, e);
        }
    }

    /**
     * 功能描述：  将数据库信息转换成视图信息
     *
     * @param menus
     * @return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.MenuVo>
     * @author wangcanfeng
     * @time 2019/1/28 21:28
     * @since v1.0
     **/
    private PageInfo<MenuVo> convertPageInfo(PageInfo<MenuInfo> menus) {
        List<MenuInfo> menuList = menus.getList();
        List<MenuVo> menuVos = new ArrayList<>(menuList.size());
        for (MenuInfo menu : menuList) {
            MenuVo vo = new MenuVo();
            vo.setId(menu.getId());
            vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(menu.getCreateTime()));
            vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(menu.getModifyTime()));
            vo.setCreator(menu.getCreator());
            vo.setMark(menu.getMark());
            vo.setMenuLevel(menu.getMenuLevel());
            vo.setMenuName(menu.getMenuName());
            vo.setMenuPath(menu.getPath());
            vo.setParentNode(menu.getPNode());
            vo.setParentName(menu.getPName());
            vo.setMenuCode(menu.getMenuCode());
            if (ObjectUtils.isEmpty(menu.getNeedAuth()) || menu.getNeedAuth() == 0) {
                vo.setNeedAuth("否");
            } else {
                vo.setNeedAuth("是");
            }
            if (ObjectUtils.isEmpty(menu.getMenuType()) || menu.getMenuType() == 0) {
                vo.setMenuType("空");
            } else {
                vo.setMenuType("非空");
            }
            menuVos.add(vo);
        }
        PageInfo<MenuVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(menus.getTotal());
        pageInfo.setList(menuVos);
        return pageInfo;
    }
}
