package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.ServerInfo;
import com.wcf.funny.admin.vo.ServerChartVo;
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
    PageInfo<ServerInfoVo> getServerInfos(Integer currentPage, Integer pageSize);

    /**
     * 功能描述：插入服务器运行信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    void insertServerInfo(ServerInfo info);

    /**
     * 功能描述：插入服务器运行信息
     *
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    void insertServerInfo(String type);


    /**
     * 功能描述：根据统计类型查询服务器信息列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    PageInfo<ServerInfoVo> getServerInfosByType(Integer currentPage, Integer pageSize,String type);

    /**
     * 功能描述：  查询服务器24小时内的数据信息
     *@author wangcanfeng
     *@time 2019/3/24 13:28
     *@since v1.0
     * @param
     **/
    ServerChartVo serverChart24();

    /**
     * 功能描述：获取cpu使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    String cpuUsed();

    /**
     * 功能描述：获取硬盘使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    String diskUsed();

    /**
     * 功能描述：获取非堆内存使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    String noHeapUsed();

    /**
     * 功能描述：获取堆内存使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    String heapUsed();
}
