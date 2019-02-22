package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.ArticleConstant;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.blog.vo.req.ArticleQueryReq;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
    public BaseResponse<List<ArticleSimpleVo>> getArticleSimpleList(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "title", required = false) String title) {
        PageInfo<ArticleSimpleVo> pageInfo;
        if (ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(title)) {
            pageInfo = articleInfoService.getSimpleArticles(currentPage, pageSize);
        } else {
            // 如果是查询全部，则直接将专题设置为null即可
            if (ArticleConstant.CATEGORY_ALL.equals(category)) {
                category = null;
            }
            ArticleQueryReq req = new ArticleQueryReq();
            req.setCategory(category);
            req.setCurrentPage(currentPage);
            req.setPageSize(pageSize);
            if (!ObjectUtils.isEmpty(title)) {
                req.setTitle("%" + title + "%");
            }
            pageInfo = articleInfoService.getSimpleArticlesByReq(req);
        }
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
        // 更新文章的点击次数
        articleInfoService.updateHitsById(vo.getId(), vo.getHits() + 1);
        return new BaseResponse<>(vo);
    }

    /**
     * 功能描述：  给文章点赞
     *
     * @param articleId
     * @param stars
     * @author wangcanfeng
     * @time 2019/2/21 22:23
     * @since v1.0
     **/
    @PutMapping("/article/addStars")
    public BaseResponse addStars(@RequestParam("articleId") Integer articleId, @RequestParam("stars") Integer stars) {
        // 如果点赞数等于0，则不进行操作
        if (!stars.equals(0)) {
            stars++;
            articleInfoService.updateStarsById(articleId, stars);
        }
        return BaseResponse.ok();
    }
}
