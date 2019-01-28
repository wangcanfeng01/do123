package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.admin.vo.MenuVo;
import com.wcf.funny.admin.vo.req.MenuReq;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function 菜单信息控制器
 **/
@RestController
@RequestMapping("/ui/menu")
public class MenuInfoController {

    @Autowired
    private MenuInfoService menuInfoService;

    /**
     * 功能描述：  新增一个菜单
     *
     * @param req
     * @return com.wcf.funny.core.reponse.BaseResponse
     * @author wangcanfeng
     * @time 2019/1/28 22:49
     * @since v1.0
     **/
    @PostMapping("/add")
    public BaseResponse addNewMenu(@RequestBody MenuReq req) {
        MenuInfo info = new MenuInfo();
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setCreator(RequestUtils.getUserName());
        info.setMark(req.getMark());
        info.setMenuLevel(req.getLevel());
        info.setMenuName(req.getName());
        info.setModifyTime(FunnyTimeUtils.nowUnix());
        info.setPath(req.getPath());
        menuInfoService.insertMenu(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  查询菜单列表
     *
     * @param pageSize
     * @param currentPage
     * @return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.vo.MenuVo>>
     * @author wangcanfeng
     * @time 2019/1/28 22:49
     * @since v1.0
     **/
    @GetMapping("/menuList")
    public BaseResponse<List<MenuVo>> getMenuList(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage) {
        PageInfo<MenuVo> pageInfo = menuInfoService.selectMenu(pageSize, currentPage);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  根据id修改菜单信息
     *@author wangcanfeng
     *@time 2019/1/28 23:11
     *@since v1.0
     * @param req
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @PostMapping("/modify")
    public BaseResponse updateMenu(@RequestBody MenuReq req) {
        MenuInfo info = new MenuInfo();
        info.setId(req.getId());
        info.setCreator(RequestUtils.getUserName());
        info.setMark(req.getMark());
        info.setMenuLevel(req.getLevel());
        info.setMenuName(req.getName());
        info.setModifyTime(FunnyTimeUtils.nowUnix());
        info.setPath(req.getPath());
        menuInfoService.updateMenuById(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  根据ID删除菜单
     *@author wangcanfeng
     *@time 2019/1/28 23:11
     *@since v1.0
     * @param id
     *@return com.wcf.funny.core.reponse.BaseResponse
     **/
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteMenu(@PathVariable("id") Integer id) {
        menuInfoService.deleteMenuById(id);
        return BaseResponse.ok();
    }
}
