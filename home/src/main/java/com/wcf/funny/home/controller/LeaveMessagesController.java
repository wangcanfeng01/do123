package com.wcf.funny.home.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.home.constant.HomeConstant;
import com.wcf.funny.home.entity.LeaveMessageInfo;
import com.wcf.funny.home.service.LeaveMessageService;
import com.wcf.funny.home.vo.LeaveMessageVo;
import com.wcf.funny.home.vo.req.LeaveMessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言信息controller
 **/
@RestController
@RequestMapping("/ui/messages")
public class LeaveMessagesController {

    @Autowired
    private LeaveMessageService leaveMessageService;

    /**
     * 功能描述：分页获取留言信息
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/4/14 21:16
     * @since v1.0
     **/
    @GetMapping("/list")
    public BaseResponse<List<LeaveMessageVo>> getMessages(@RequestParam("currentPage") Integer currentPage,
                                                          @RequestParam("pageSize") Integer pageSize) {
        PageInfo<LeaveMessageVo> pageInfo = leaveMessageService.getMessages(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/4/14 21:16
     * @since v1.0
     **/
    @PostMapping("/leave")
    public BaseResponse leaveMessages(@RequestBody LeaveMessageReq req) {
        LeaveMessageInfo info = new LeaveMessageInfo();
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setEmail(req.getAddress());
        info.setIsRead(HomeConstant.NON_READ);
        info.setMessage(req.getInfo());
        info.setUsername(req.getUsername());
        leaveMessageService.insertMessage(info);
        return BaseResponse.ok();
    }
}
