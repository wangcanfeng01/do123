package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章管理控制器
 **/
@RestController
@RequestMapping("/ui/blog")
public class ArticleManagementController {
    @Autowired
    private ArticleInfoService articleInfoService;

    /**
     * 功能描述：分页查询文章列表信息，不需要查询文章内容
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 15:23
     * @since v1.0
     **/
    @GetMapping("/management/list")
    public BaseResponse<List<ArticleInfoVo>> getArticleList(@RequestParam("currentPage") Integer currentPage,
                                                            @RequestParam("pageSize") Integer pageSize) {
        // 直接查询不包含文章内容的信息
        PageInfo<ArticleInfoVo> pageInfo = articleInfoService.getArticles(currentPage, pageSize, false);
        return new PageResponse<>(pageInfo);
    }
}
