package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/8
 * @function 用户详情数据库操作接口
 **/
@Mapper
public interface PersonDetailsMapper {

    /**
     * 功能描述：插入详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:08
     * @since v1.0
     **/
    @Insert("")
    void insertDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述： 更新用户详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:39
     * @since v1.0
     **/
    @Update("")
    void updateDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述：根据用户名称查询用户所有详细信息
     *
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    @Select("")
    List<PersonalInfo> getPersonByName(String username);

    /**
     * 功能描述：  获取详情列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/8 22:29
     * @since v1.0
     **/
    @Select("")
    List<PersonDetailsInfo> getProgrammerDetails();
}
