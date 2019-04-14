package com.wcf.funny.home.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.home.entity.LeaveMessageInfo;
import com.wcf.funny.home.vo.LeaveMessageVo;


/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言服务接口
 **/
public interface LeaveMessageService {

    /**
     * 功能描述：  保存留言信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/14 21:02
     * @since v1.0
     **/
    void insertMessage(LeaveMessageInfo info);

    /**
     * 功能描述：  获取留言信息列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/4/14 21:10
     * @since v1.0
     **/
    PageInfo<LeaveMessageVo> getMessages(Integer currentPage, Integer pageSize);
}
