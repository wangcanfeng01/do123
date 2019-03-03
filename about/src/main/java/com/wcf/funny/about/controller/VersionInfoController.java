package com.wcf.funny.about.controller;

import com.wcf.funny.about.service.VersionInfoService;
import com.wcf.funny.about.vo.VersionInfoVo;
import com.wcf.funny.about.vo.req.VersionReq;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
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
     * 功能描述：
     *@author wangcanfeng
     *@time 2019/3/3 15:44
     *@since v1.0
     * @param id
     **/
    @DeleteMapping("/deleteVersion/{id}")
    public BaseResponse deleteVersionById(@PathVariable("id") Integer id) {

        return BaseResponse.ok();
    }

    /**
     * 功能描述：
     *@author wangcanfeng
     *@time 2019/3/3 15:44
     *@since v1.0
     * @param req
     **/
    @PutMapping("")
    public BaseResponse updateVersion(@RequestBody VersionReq req) {

        return BaseResponse.ok();
    }


    /**
     * 功能描述：
     *@author wangcanfeng
     *@time 2019/3/3 15:44
     *@since v1.0
     * @param req
     **/
    @PostMapping("")
    public BaseResponse addVersionInfo(@RequestBody VersionReq req) {

        return BaseResponse.ok();
    }
}
