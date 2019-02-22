package com.wcf.funny.blog.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/2/22
 * @function 文章数据库操作sql生成辅助器
 **/
public class ArticleInfoProvider {

    public String getArticleInfoSimpleByParamsSQL(@Param("category")String category,@Param("title")String title){
        String sql = new SQL() {{
            SELECT("id, title, title_simple as slug, cover, modify_time as modifyTime," +
                    " author, keywords, status, categories as category");
            FROM("info_article ");
            WHERE("delete_flag=0");
            if(!ObjectUtils.isEmpty(category)){
                WHERE("categories=#{category}");
            }
            if(!ObjectUtils.isEmpty(title)){
                WHERE("title like #{title}");
            }
            ORDER_BY("modify_time DESC");
        }}.toString();
        return sql;
    }
}