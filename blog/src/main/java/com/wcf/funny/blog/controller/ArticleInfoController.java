package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章信息控制器
 **/
@RestController
@RequestMapping("/ui/blog")
public class ArticleInfoController {

    @Autowired
    private ArticleInfoService articleInfoService;

    /**
     * 功能描述：  仅仅用于文章列表展示信息的查询
     *
     * @param currentPage
     * @param pageSize
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 15:22
     * @since v1.0
     **/
    @GetMapping("/articleList/simple")
    public BaseResponse<List<ArticleSimpleVo>> getArticleSimpleList(@RequestParam("currentPage") Integer currentPage,
                                                                    @RequestParam("pageSize") Integer pageSize) {
        PageInfo<ArticleSimpleVo> pageInfo = articleInfoService.getSimpleArticles(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  查询近期内的文章信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/17 16:11
     * @since v1.0
     **/
    @GetMapping("/recentArticles")
    public BaseResponse<List<ArticleInfoVo>> getRecentList() {
        PageInfo<ArticleInfoVo> pageInfo = articleInfoService.getRecentList();
        return new PageResponse<>(pageInfo);
    }

    /**
     * @param slug
     * @return java.lang.String
     * @note 文章展示页面
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping(value = "/article/{slug}")
    public BaseResponse<ArticleInfoVo> getArticle(@PathVariable("slug") String slug) {
        ArticleInfoVo vo = articleInfoService.getArticleBySlug(slug);
        return new BaseResponse<>(vo);
    }


}
