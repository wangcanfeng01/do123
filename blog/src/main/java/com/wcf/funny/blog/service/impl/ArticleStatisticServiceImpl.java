package com.wcf.funny.blog.service.impl;

import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.mapper.ArticleStatisticMapper;
import com.wcf.funny.blog.service.ArticleStatisticService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.core.exception.PgSqlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 文章统计接口实现类
 **/
@Service
public class ArticleStatisticServiceImpl implements ArticleStatisticService {

    @Autowired
    private ArticleStatisticMapper articleStatisticMapper;


    /**
     * 功能描述：文章统计信息
     *
     * @author wangcanfeng
     * @time 2019/3/25 23:24
     * @since v1.0
     **/
    @Override
    public ArticleStatisticVo getArticleStatistic() {
        try {
            return articleStatisticMapper.getArticleStatistic();
        } catch (Exception e) {
            throw new PgSqlException(ArticleErrorCode.SELECT_ARTICLE_ERROR, e);
        }
    }

}
