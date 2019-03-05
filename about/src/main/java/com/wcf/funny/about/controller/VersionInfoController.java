package com.wcf.funny.about.controller;

import com.wcf.funny.about.service.VersionInfoService;
import com.wcf.funny.about.vo.VersionInfoVo;
import com.wcf.funny.about.vo.req.VersionReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本信息controller
 **/
@RestController
@RequestMapping("/ui/about")
public class VersionInfoController {

    @Autowired
    private VersionInfoService versionInfoService;

    /**
     * 功能描述： 根据排序要求查询版本信息列表
     *
     * @param order
     * @author wangcanfeng
     * @time 2019/3/3 15:26
     * @since v1.0
     **/
    @GetMapping("/versionList")
    public BaseResponse<List<VersionInfoVo>> getVersionList(@RequestParam("author") String author,
                                                            @RequestParam("order") Integer order) {
        List<VersionInfoVo> voList = versionInfoService.getVersionList(author, order);
        return new ListResponse<>(voList);
    }

    /**
     * 功能描述：根据id删除版本信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 15:44
     * @since v1.0
     **/
    @DeleteMapping("/version/delete/{id}")
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.VERSION,
            info = LogConstant.ActionInfo.DELETE_VERSION_INFO)
    public BaseResponse deleteVersionById(@PathVariable("id") Integer id) {
        versionInfoService.deleteVersionById(id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：修改版本信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/3 15:44
     * @since v1.0
     **/
    @PutMapping("/modify/version")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.VERSION,
            info = LogConstant.ActionInfo.MODIFY_VERSION_INFO)
    public BaseResponse updateVersion(@RequestBody VersionReq req) {
        String username = RequestUtils.getUserName();
        versionInfoService.modifyVersion(req.getVersion(), req.getDescription(), username, req.getId());
        return BaseResponse.ok();
    }


    /**
     * 功能描述：新增版本信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/3 15:44
     * @since v1.0
     **/
    @PostMapping("add/version")
    @OperationLog(action = LogConstant.ActionType.ADD, object = LogConstant.ActionObject.VERSION,
            info = LogConstant.ActionInfo.ADD_VERSION_INFO)
    public BaseResponse addVersionInfo(@RequestBody VersionReq req) {
        String username = RequestUtils.getUserName();
        versionInfoService.insertVersion(req.getVersion(), req.getDescription(), username);
        return BaseResponse.ok();
    }
}
