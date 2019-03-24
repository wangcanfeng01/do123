package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.TaskInterval;
import com.wcf.funny.admin.service.ServerInfoService;
import com.wcf.funny.admin.vo.ServerChartVo;
import com.wcf.funny.admin.vo.ServerIndexVo;
import com.wcf.funny.admin.vo.ServerInfoVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


    /**
     * 功能描述： 查询近24小时的服务器资源使用情况
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/24 12:26
     * @since v1.0
     **/
    @GetMapping("/24hours")
    public BaseResponse<ServerChartVo> getServerList() {
        ServerChartVo chart = serverInfoService.serverChart24();
        return new BaseResponse<>(chart);
    }

    /**
     * 功能描述：  查询当前服务器指标信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/24 12:58
     * @since v1.0
     **/
    @GetMapping("/nowIndex")
    public BaseResponse<ServerIndexVo> getIndex() {

        ServerIndexVo vo = new ServerIndexVo();
        // cpu用量
        String cpu = serverInfoService.cpuUsed();
        Double cpuUsed = Double.valueOf(cpu);
        cpuUsed = cpuUsed * 100;
        vo.setCpuPercentage(cpuUsed);

        // 硬盘用量
        String disk = serverInfoService.diskUsed();
        Double diskUsed = Double.valueOf(disk);
        diskUsed = diskUsed * 100;
        vo.setDiskPercentage(diskUsed);
        // 内存总量
        BigDecimal total = new BigDecimal("40.96");
        // 计算堆内存占总内存量
        String heap = serverInfoService.heapUsed();
        BigDecimal bp = new BigDecimal(heap);
        BigDecimal heapUsed = bp.divide(total, 5, RoundingMode.HALF_UP);
        vo.setHeapPercentage(heapUsed.doubleValue());
        // 计算非堆内存占总内存量
        String noheap = serverInfoService.noHeapUsed();
        BigDecimal bnp = new BigDecimal(noheap);
        BigDecimal bnpUsed = bnp.divide(total, 5, RoundingMode.HALF_UP);
        vo.setNoheapPercentage(bnpUsed.doubleValue());
        return new BaseResponse<>(vo);
    }

}
