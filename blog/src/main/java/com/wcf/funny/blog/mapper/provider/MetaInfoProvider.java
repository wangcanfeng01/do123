package com.wcf.funny.blog.mapper.provider;

import com.wcf.funny.blog.constant.IncreaseType;
import com.wcf.funny.blog.entity.MetaChangeInfo;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 标签sql语句辅助器
 **/
public class MetaInfoProvider {

    /**
     * 功能描述： 批量减少标签的统计值
     *
     * @param names
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 16:07
     * @since v1.0
     **/
    public String reduceCountBatchByNameAndTypeSQL(@Param("names") List<String> names, @Param("type") String type) {
        StringBuilder sb = new StringBuilder();
        names.forEach(name -> sb.append("update info_metas SET count=count-1,modify_time=").
                append(FunnyTimeUtils.nowUnix())
                .append(" WHERE name='").append(name).append("' and type='").append(type).append("';\n"));
        return sb.toString();
    }

    /**
     * 功能描述：批量改变标签统计值的sql
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/2/26 22:13
     * @since v1.0
     **/
    public String changeMetaCountSQL(@Param("metas")List<MetaChangeInfo> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(info -> {
                    //如果是新增的类型，则使用insertOrUpdate语句
                    if (IncreaseType.NEW_INCREASE.equals(info.getDecreaseOrIncrease())) {
                        sb.append("insert into info_metas(name, type, cover, description, modify_time,create_time, count) values('")
                                .append(info.getName()).append("','keyword','','',").append(FunnyTimeUtils.nowUnix()).append(",")
                                .append(FunnyTimeUtils.nowUnix()).append(",1)").append("ON  DUPLICATE KEY update modify_time=")
                                .append(FunnyTimeUtils.nowUnix()).append (", count=count+1;\n");
                    } else {
                        sb.append("update info_metas SET count=count").append(info.getDecreaseOrIncrease().getType())
                                .append(", modify_time=").append(FunnyTimeUtils.nowUnix()).append(" WHERE name='").append(info.getName())
                                .append("' and type='").append(info.getType()).append("';\n");
                    }

                }
        );
        return sb.toString();
    }


}
