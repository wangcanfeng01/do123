package com.wcf.funny.admin.controller;

import com.wcf.funny.admin.entity.MenuInfo;
import com.wcf.funny.admin.service.MenuInfoService;
import com.wcf.funny.admin.vo.req.MenuReq;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
