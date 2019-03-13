package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.ServerInfo;
import com.wcf.funny.admin.vo.ServerInfoVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/13
 * @function 服务器信息查询接口
 **/
public interface ServerInfoService {
    /**
     * 功能描述：查询服务器信息列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    PageInfo<ServerInfoVo> getServerInfos(Integer currentPage,Integer pageSize);

    /**
     * 功能描述：插入服务器运行信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    void insertServerInfo(ServerInfo info);
}
