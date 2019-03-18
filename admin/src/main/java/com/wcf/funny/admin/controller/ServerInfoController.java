package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.service.ServerInfoService;
import com.wcf.funny.admin.vo.ServerInfoVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/18
 * @function 服务信息controller
 **/
@RestController
@RequestMapping("/ui/server")
public class ServerInfoController {

    @Autowired
    private ServerInfoService serverInfoService;

    /**
     * 功能描述：查询服务器信息列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/18 22:27
     * @since v1.0
     **/
    @GetMapping("/list")
    public BaseResponse<List<ServerInfoVo>> getServerList(@RequestParam("currentPage") Integer currentPage,
                                                          @RequestParam("pageSize") Integer pageSize) {
        PageInfo<ServerInfoVo> pageInfo = serverInfoService.getServerInfos(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
    }
}
