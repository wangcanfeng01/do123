package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.ServerInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/13
 * @function 服务器信息查询接口
 **/
@Mapper
public interface ServerInfoMapper {

    /**
     * 功能描述：查询服务器信息列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    @Select(" SELECT id, create_time as createTime, heap_used as heapUsed, noheap_used as noHeapUsed," +
            " disk_used as diskUsed, cpu_used as cpuUsed, statistic_type as statisticType" +
            " FROM info_server ORDER BY id desc")
    List<ServerInfo> getServerInfos();

    /**
     * 功能描述：插入服务器运行信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    @Insert("insert into info_server (create_time, heap_used, noheap_used, disk_used, cpu_used, statistic_type)" +
            " VALUES (#{createTime}, #{heapUsed}, #{noHeapUsed}, #{diskUsed}, #{cpuUsed}, #{statisticType})")
    void insertServerInfo(ServerInfo info);
}
