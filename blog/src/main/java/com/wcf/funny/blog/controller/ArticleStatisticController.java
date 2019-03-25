package com.wcf.funny.blog.controller;

import com.wcf.funny.blog.service.ArticleStatisticService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 博客统计信息controller
 **/
@RestController
@RequestMapping("/ui/blog/statistic")
public class ArticleStatisticController {

    @Autowired
    private ArticleStatisticService articleStatisticService;

    @Autowired
    private MetaInfoService metaInfoService;

    /**
     * 功能描述：获取博客总体统计信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/25 23:32
     * @since v1.0
     **/
    @GetMapping("/article")
    public BaseResponse<ArticleStatisticVo> getArticleStatistic() {
        ArticleStatisticVo vo = articleStatisticService.getArticleStatistic();
        return new BaseResponse<>(vo);
    }

    @GetMapping("/categories")
    public BaseResponse getCategories(){
    }
}
