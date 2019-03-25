package com.wcf.funny.blog.service;

import com.wcf.funny.blog.vo.ArticleStatisticVo;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计接口
 **/
public interface ArticleStatisticService {

    /**
     * 功能描述：文章统计信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    ArticleStatisticVo getArticleStatistic();
}
