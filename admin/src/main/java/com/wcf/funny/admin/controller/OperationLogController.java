package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.vo.OpsLogVo;
import com.wcf.funny.core.entity.OperationLogInfo;
import com.wcf.funny.core.service.OperationLogService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.I18Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 操作日志相关的控制器
 **/
@RestController
@RequestMapping("/ui/ops")
public class OperationLogController {

    @Autowired
    private OperationLogService logService;

    /**
     * 功能描述：  无条件分页查询日志信息
     *
     * @param currentPage
     * @param pageSize
     * @return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.vo.OpsLogVo>>
     * @author wangcanfeng
     * @time 2019/2/4 12:27
     * @since v1.0
     **/
    @GetMapping("/logList")
    public BaseResponse<List<OpsLogVo>> getOpsLogList(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        PageInfo<OpsLogVo> logs = convertPageInfo(logService.getLogs(currentPage, pageSize));
        return new PageResponse<>(logs);
    }

    /**
     * 功能描述：  带时间条件查询操作日志信息
     *
     * @param currentPage
     * @param pageSize
     * @return com.wcf.funny.core.reponse.BaseResponse<java.util.List<com.wcf.funny.admin.vo.OpsLogVo>>
     * @author wangcanfeng
     * @time 2019/2/4 12:27
     * @since v1.0
     **/
    @GetMapping("/logList/time")
    public BaseResponse<List<OpsLogVo>> getOpsLogListByTime(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam("start") String start, @RequestParam("end") String end) {
        Integer startTime = FunnyTimeUtils.getUnixTime(start);
        Integer endTime = FunnyTimeUtils.getUnixTime(end);
        PageInfo<OpsLogVo> logs = convertPageInfo(logService.getLogsByTime(currentPage, pageSize, startTime, endTime));
        return new PageResponse<>(logs);
    }

    /**
     * 功能描述：  将数据库中查询的结果转成需要展示的样式
     *
     * @param logs
     * @return com.github.pagehelper.PageInfo<com.wcf.funny.admin.vo.OpsLogVo>
     * @author wangcanfeng
     * @time 2019/2/3 21:07
     * @since v1.0
     **/
    private PageInfo<OpsLogVo> convertPageInfo(PageInfo<OperationLogInfo> logs) {
        List<OperationLogInfo> logList = logs.getList();
        List<OpsLogVo> opsVos = new ArrayList<>(logList.size());
        for (OperationLogInfo logInfo : logList) {
            OpsLogVo vo = new OpsLogVo();
            vo.setId(logInfo.getId());
            vo.setActionType(I18Utils.getOpsTypeInfo(logInfo.getActionType()));
            vo.setAuthorName(logInfo.getAuthorName());
            vo.setActionObject(I18Utils.getOpsObejctInfo(logInfo.getActionObject()));
            vo.setIp(logInfo.getIp());
            vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(logInfo.getCreateTime()));
            vo.setActionInfo(I18Utils.getOpsInfo(logInfo.getActionInfo()));
            vo.setActionResult(I18Utils.getOpsResultInfo(logInfo.getActionResult()));
            vo.setDetails(logInfo.getDetails());
            vo.setFacePath(logInfo.getFacePath());
            opsVos.add(vo);
        }
        PageInfo<OpsLogVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(logs.getTotal());
        pageInfo.setList(opsVos);
        return pageInfo;
    }
}
