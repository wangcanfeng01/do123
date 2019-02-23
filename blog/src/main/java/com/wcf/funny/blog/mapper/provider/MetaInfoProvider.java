package com.wcf.funny.blog.mapper.provider;

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
    public String reduceCountBatchByNameAndTypeSQL(@Param("names") List<String> names,@Param("type") String type) {
        StringBuilder sb = new StringBuilder();
        names.forEach(name -> sb.append("update info_metas SET count=count-1,modify_time=").
                append(FunnyTimeUtils.nowUnix())
                .append(" WHERE name='").append(name).append("' and type='").append(type).append("';\n"));
        return sb.toString();
    }


}
