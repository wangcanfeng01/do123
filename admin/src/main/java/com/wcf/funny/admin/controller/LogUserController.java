package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.service.LoginUserService;
import com.wcf.funny.admin.vo.LoginUserVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/6
 * @function 访客信息controller
 **/
@RestController
@RequestMapping("/ui/visitor")
public class LogUserController {

    @Autowired
    private LoginUserService loginUserService;

    /**
     * 功能描述：获取访客记录信息
     *
     * @param currentPage
     * @param pageSize
     * @param startTime
     * @param endTime
     * @author wangcanfeng
     * @time 2019/3/6 22:49
     * @since v1.0
     **/
    @GetMapping("/select")
    public BaseResponse<List<LoginUserVo>> getLoginUser(@RequestParam("currentPage") Integer currentPage,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam(value = "start", required = false) String startTime,
                                                        @RequestParam(value = "end", required = false) String endTime) {
        PageInfo<LoginUserVo> pageInfo;
        if (ObjectUtils.isEmpty(startTime)) {
            pageInfo = loginUserService.getLoginUserList(currentPage, pageSize);
        } else {
            Integer end;
            Integer start=FunnyTimeUtils.getUnixTime(startTime);
            if (ObjectUtils.isEmpty(endTime)) {
                end = FunnyTimeUtils.nowUnix();
            }else {
                end=FunnyTimeUtils.getUnixTime(endTime);
            }
            pageInfo = loginUserService.getLoginUserList(currentPage, pageSize, start, end);
        }
        return new PageResponse<>(pageInfo);
    }
}
