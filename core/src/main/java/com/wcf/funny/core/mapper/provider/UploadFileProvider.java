package com.wcf.funny.core.mapper.provider;

import com.wcf.funny.core.constant.PictureType;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangcanfeng
 * @time 2019/2/28
 * @function 图片上传sql辅助器
 **/
public class UploadFileProvider {

    /**
     * 功能描述：根据图片类型和归属id，查询归属名的sql
     *
     * @param type
     * @param id
     * @author wangcanfeng
     * @time 2019/2/28 22:58
     * @since v1.0
     **/
    public String getPictureBelongToSQL(@Param("type") PictureType type, @Param("id") Integer id) {
        String sql = "";
        switch (type) {
            case FACE: {
                sql = "SELECT name from info_user where id=#{id}";
                break;
            }
            case ARTICLE_INFO: {
                sql = "SELECT title from info_article where id=#{id}";
                break;
            }
            case ARTICLE_COVER: {
                sql = "SELECT title from info_article where id=#{id}";
                break;
            }
            case CATEGORY_COVER: {
                sql = "SELECT name from info_metas where id=#{id} and type='category'";
                break;
            }
        }
        return sql;
    }
}
