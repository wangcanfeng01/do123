package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.exception.errorcode.MenuErrorCode;
import com.wcf.funny.admin.mapper.MenuInfoMapper;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.core.exception.PgSqlException;
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
    public PageInfo<MenuInfo> selectMenu() {
        try {
            List<MenuInfo> menuInfos = menuInfoMapper.selectMenu();
            if (ObjectUtils.isEmpty(menuInfos)) {
                menuInfos = new ArrayList<>();
            }
            return new PageInfo<>(menuInfos);
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
    public PageInfo<MenuInfo> selectMenuByName(String name) {
        try {
            List<MenuInfo> menuInfos = menuInfoMapper.selectMenuByName(name);
            if (ObjectUtils.isEmpty(menuInfos)) {
                menuInfos = new ArrayList<>();
            }
            return new PageInfo<>(menuInfos);
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
}
