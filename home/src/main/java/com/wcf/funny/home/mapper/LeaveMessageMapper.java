package com.wcf.funny.home.mapper;

import com.wcf.funny.home.entity.LeaveMessageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 留言sql映射
 **/
@Mapper
public interface LeaveMessageMapper {
    /**
     * 功能描述：  保存留言信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/14 21:02
     * @since v1.0
     **/
    @Insert("INSERT INTO info_leave_message(username, message, email, create_time, is_read)" +
            " VALUES(#{username}, #{message}, #{email}, #{createTime}, #{isRead});")
    void insertMessage(LeaveMessageInfo info);


    /**
     * 功能描述：  获取留言信息列表
     *
     * @author wangcanfeng
     * @time 2019/4/14 21:10
     * @since v1.0
     **/
    @Select("SELECT id, username, message, email, create_time as createTime, is_read as isRead" +
            "FROM info_leave_message order by create_time desc")
    List<LeaveMessageInfo> getMessages();
}
