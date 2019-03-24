package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.entity.PersonalInfo;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into info_user_details (person_name, username, email, work_area, telephone, mind, tags, resume)" +
            "  VALUES (#{personName}, #{username}, #{email}, #{workArea}, #{telephone}, #{mind}, #{tags}, #{resume})")
    void insertDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述： 更新用户详细信息
     *
     * @param detailsInfo
     * @author wangcanfeng
     * @time 2019/3/8 22:39
     * @since v1.0
     **/
    @Update("update info_user_details set person_name=#{personName}, email=#{email}, work_area=#{workArea}, telephone=#{telephone}," +
            "  mind=#{mind}, tags=#{tags}, resume=#{resume} where username=#{username}")
    void updateDetails(PersonDetailsInfo detailsInfo);

    /**
     * 功能描述：根据用户名称查询用户所有详细信息
     *
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    @Select(" SELECT id, person_name as personName, username, email, work_area as workArea, telephone," +
            " mind, tags, resume FROM info_user_details WHERE username=#{username}")
    PersonDetailsInfo getPersonDetailByName(String username);

    /**
     * 功能描述：根据用户名称修改用户简历
     *
     * @author wangcanfeng
     * @time 2019/3/8 22:27
     * @since v1.0
     **/
    @Select("update info_user_details set resume=#{resume} where username=#{username}")
    void updateResumeByName(@Param("username") String username,@Param("resume") String resume);

    /**
     * 功能描述：  获取开发者详情列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/8 22:29
     * @since v1.0
     **/
    @Select(" SELECT a.id, a.person_name as personName, a.username, a.email, a.work_area as workArea, a.telephone," +
            " a.mind, a.tags, a.resume, b.face_path as facePath FROM info_user_details as a LEFT JOIN info_user as b ON a.username=b.name" +
            " WHERE b.user_type='programmer'")
    List<PersonDetailsInfo> getProgrammerDetails();
}
