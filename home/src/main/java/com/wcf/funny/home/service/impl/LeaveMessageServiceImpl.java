package com.wcf.funny.home.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.home.entity.LeaveMessageInfo;
import com.wcf.funny.home.exception.errorcode.MessageErrorCode;
import com.wcf.funny.home.mapper.LeaveMessageMapper;
import com.wcf.funny.home.service.LeaveMessageService;
import com.wcf.funny.home.vo.LeaveMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言服务接口实现类
 **/
@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    private LeaveMessageMapper messageMapper;

    /**
     * 功能描述：  保存留言信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/14 21:02
     * @since v1.0
     **/
    @Override
    public void insertMessage(LeaveMessageInfo info) {
        try {
            messageMapper.insertMessage(info);
        } catch (Exception e) {
            throw new PgSqlException(MessageErrorCode.INSERT_MESSAGE_FAILED, e);
        }
    }

    /**
     * 功能描述：  获取留言信息列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/4/14 21:10
     * @since v1.0
     **/
    @Override
    public PageInfo<LeaveMessageVo> getMessages(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<LeaveMessageInfo> leaveMessageInfos = messageMapper.getMessages();
            return convert(leaveMessageInfos);
        } catch (Exception e) {
            throw new PgSqlException(MessageErrorCode.SELECT_MESSAGE_FAILED, e);
        }
    }


    /**
     * 功能描述：  将数据库信息转成视图信息
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/4/14 21:50
     * @since v1.0
     **/
    private PageInfo<LeaveMessageVo> convert(List<LeaveMessageInfo> list) {
        PageInfo<LeaveMessageInfo> source = new PageInfo<>(list);
        PageInfo<LeaveMessageVo> pageInfo = new PageInfo<>();
        if (ObjectUtils.isEmpty(list)) {
            return pageInfo;
        } else {
            List<LeaveMessageVo> vos = new ArrayList<>();
            source.getList().forEach(info -> {
                LeaveMessageVo vo = new LeaveMessageVo();
                vo.setEmail(info.getEmail());
                vo.setId(info.getId());
                vo.setIsRead(info.getIsRead());
                vo.setTime(FunnyTimeUtils.getTimeByUnixTime(info.getCreateTime()));
                vo.setUsername(info.getUsername());
                vo.setMessage(info.getMessage());
                vos.add(vo);
            });
            pageInfo.setTotal(source.getTotal());
            pageInfo.setList(vos);
            return pageInfo;
        }
    }
}
