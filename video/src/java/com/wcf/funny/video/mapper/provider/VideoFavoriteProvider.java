package com.wcf.funny.video.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/4/20
 * @function 视频收藏sql辅助器
 **/
public class VideoFavoriteProvider {

    /**
     * 功能描述：  生成收藏夹简单总体信息查询语句
     *
     * @param collector
     * @param type
     * @param title
     * @author wangcanfeng
     * @time 2019/4/20 16:28
     * @since v1.0
     **/
    public String favoritesSQL(@Param("collector") String collector,
                               @Param("type") String type,
                               @Param("title") String title) {
        String sql = new SQL() {{
            SELECT("id, collector, title, image, play_url as playUrl, type, value, other," +
                    " director, summary, create_time as createTime ");
            FROM("info_video_favorite");
            WHERE("collector=#{collector}");
            if (!ObjectUtils.isEmpty(type)) {
                WHERE("type=#{type}");
            }
            if (!ObjectUtils.isEmpty(title)) {
                WHERE("title like %" + title + "%");
            }
        }}.toString();
        return sql;
    }
}
