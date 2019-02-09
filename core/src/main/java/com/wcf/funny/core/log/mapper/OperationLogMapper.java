package com.wcf.funny.core.log.mapper;

import com.wcf.funny.core.log.entity.OperationLogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function
 **/
@Mapper
public interface OperationLogMapper {
    /**
     * @param info
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/13 22:31
     * @since v1.0
     **/
    @Insert("INSERT INTO info_operation_log(author_name, ip, create_time, action_type, action_object, action_result," +
            " details,action_info) VALUES(#{authorName},#{ip},#{createTime},#{actionType},#{actionObject},#{actionResult}," +
            " #{details},#{actionInfo})")
    void insertLog(OperationLogInfo info) throws Exception;


    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 查询操作日志
     * @author WCF
     * @time 2018/6/13 22:32
     * @since v1.0
     **/
    @Select("SELECT a.id, a.author_name as authorName, a.ip, a.create_time as createTime, a.action_type as actionType, " +
            "a.action_object as actionObject, a.action_result as actionResult, a.details, a.action_info as actionInfo, " +
            "b.face_path as facePath FROM info_operation_log as a LEFT JOIN info_user as b ON a.author_name=b.name" +
            " ORDER BY create_time DESC")
    List<OperationLogInfo> getLogsList() throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 查询操作日志
     * @author WCF
     * @time 2018/6/13 22:32
     * @since v1.0
     **/
    @Select("SELECT a.id, a.author_name as authorName, a.ip, a.create_time as createTime, a.action_type as actionType, " +
            "a.action_object as actionObject, a.action_result as actionResult, a.details, a.action_info as actionInfo, " +
            "b.face_path as facePath FROM info_operation_log as a LEFT JOIN info_user as b ON a.author_name=b.name" +
            " WHERE create_time >=#{start} AND create_time <=#{end} ORDER BY create_time DESC")
    List<OperationLogInfo> getLogsListByTime(@Param("start") Integer start,@Param("end") Integer end);
}
