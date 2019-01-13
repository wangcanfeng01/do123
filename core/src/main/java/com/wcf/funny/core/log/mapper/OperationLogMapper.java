package com.wcf.funny.core.log.mapper;

import com.wcf.funny.core.log.entity.OperationLogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
    @Insert("INSERT INTO info_operation_log(author_name, ip, create_time, action_type, object, action_result, details)" +
            "VALUES(#{authorName},#{ip},#{createTime},#{actionType},#{object},#{actionResult},#{details})")
    void insertLog(OperationLogInfo info) throws Exception;


    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 查询操作日志
     * @author WCF
     * @time 2018/6/13 22:32
     * @since v1.0
     **/
    @Select("SELECT id, author_name as authorName, ip, create_time as createTime, action_type as actionType, " +
            "object, action_result as action_result, details " +
            "FROM info_operation_log " +
            "ORDER BY create_time DESC")
    List<OperationLogInfo> getLogsList() throws Exception;
}
