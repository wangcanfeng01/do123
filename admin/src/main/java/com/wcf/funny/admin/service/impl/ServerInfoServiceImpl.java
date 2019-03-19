package com.wcf.funny.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.AdminConstant;
import com.wcf.funny.admin.entity.ServerInfo;
import com.wcf.funny.admin.exception.errorcode.ServerInfoErrorCode;
import com.wcf.funny.admin.mapper.ServerInfoMapper;
import com.wcf.funny.admin.service.ServerInfoService;
import com.wcf.funny.admin.vo.ServerInfoVo;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/13
 * @function 服务器信息查询接口实现类
 **/
@Service
@Log4j2
public class ServerInfoServiceImpl implements ServerInfoService {

    @Autowired
    private ServerInfoMapper serverInfoMapper;

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Autowired
    private HealthEndpoint healthEndpoint;

    /**
     * 功能描述：查询服务器信息列表
     *
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    @Override
    public PageInfo<ServerInfoVo> getServerInfos(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<ServerInfo> list = serverInfoMapper.getServerInfos();
            return convertPage(list);
        } catch (Exception e) {
            throw new PgSqlException(ServerInfoErrorCode.SELECT_SERVER_ERROR, e);
        }
    }

    /**
     * 功能描述：插入服务器运行信息
     *
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    @Override
    public void insertServerInfo(ServerInfo info) {
        try {
            serverInfoMapper.insertServerInfo(info);
        } catch (Exception e) {
            throw new PgSqlException(ServerInfoErrorCode.INSERT_SERVER_ERROR, e);
        }
    }


    /**
     * 功能描述：插入服务器运行信息
     *
     * @author wangcanfeng
     * @time 2019/3/13 23:19
     * @since v1.0
     **/
    @Override
    public void insertServerInfo() {
        ServerInfo info = new ServerInfo();
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setHeapUsed(heapUsed());
        info.setNoHeapUsed(noHeapUsed());
        info.setDiskUsed(diskUsed());
        info.setCpuUsed(cpuUsed());
        insertServerInfo(info);
    }

    /**
     * 功能描述：  转换成页面信息
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/3/14 23:02
     * @since v1.0
     **/
    private PageInfo<ServerInfoVo> convertPage(List<ServerInfo> list) {
        PageInfo<ServerInfo> pageInfo = new PageInfo<>(list);
        PageInfo<ServerInfoVo> pageVo = new PageInfo<>();
        if (ObjectUtils.isEmpty(list)) {
            return pageVo;
        } else {
            List<ServerInfoVo> vos = new ArrayList<>(list.size());
            pageInfo.getList().forEach(info -> {
                ServerInfoVo vo = new ServerInfoVo();
                vo.setCpuUsed(info.getCpuUsed());
                vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(info.getCreateTime()));
                vo.setDiskUsed(info.getDiskUsed());
                vo.setHeapUsed(info.getHeapUsed());
                vo.setId(info.getId());
                vo.setNoHeapUsed(info.getNoHeapUsed());
            });
            pageVo.setTotal(pageInfo.getTotal());
            pageVo.setList(vos);
        }

        return pageVo;
    }

    /**
     * 功能描述：获取cpu使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    public String cpuUsed() {
        Double used = getMetricStatistic(AdminConstant.SYSTEM_CPU_USED, null);
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(used);
    }

    /**
     * 功能描述：获取硬盘使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    public String diskUsed() {
        Health health = healthEndpoint.healthForComponent(AdminConstant.DISK_SPACE);
        Object totalStr = health.getDetails().get("total");
        Object freeStr = health.getDetails().get("free");
        if (ObjectUtils.isEmpty(totalStr) || ObjectUtils.isEmpty(freeStr)) {
            log.error("diskSpace information obtain failed");
            return "0.00";
        }
        Double total = Double.valueOf(totalStr.toString());
        if (total.equals(0.0)) {
            return "0.00";
        }
        Double free = Double.valueOf(freeStr.toString());
        DecimalFormat format = new DecimalFormat("0.00");
        Double used = (total - free) / total;
        return format.format(used);
    }

    /**
     * 功能描述：获取非堆内存使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    public String noHeapUsed() {
        String tag = "area:nonheap";
        Double used = getMetricStatistic(AdminConstant.JVM_MEMORY_USED, tag);
        used = used / CoreConstant.MB;
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(used);
    }

    /**
     * 功能描述：获取堆内存使用量
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/14 23:13
     * @since v1.0
     **/
    public String heapUsed() {
        String tag = "area:heap";
        Double used = getMetricStatistic(AdminConstant.JVM_MEMORY_USED, tag);
        used = used / CoreConstant.MB;
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(used);
    }

    /**
     * 功能描述：  根据metric的名称和标签获取metric的统计值
     *
     * @param metricName
     * @param tag
     * @author wangcanfeng
     * @time 2019/3/15 21:08
     * @since v1.0
     **/
    private Double getMetricStatistic(String metricName, String tag) {
        Double result;
        List<String> tags = null;
        if (!ObjectUtils.isEmpty(tag)) {
            tags = new ArrayList<>();
            tags.add(tag);
        }
        MetricsEndpoint.MetricResponse response = metricsEndpoint.metric(metricName, tags);
        if (ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getMeasurements())) {
            log.error("can not get the statistic info, the response or the measurements is null");
            return 0.0;
        } else {
            result = response.getMeasurements().get(0).getValue();
        }
        return result;
    }

}
