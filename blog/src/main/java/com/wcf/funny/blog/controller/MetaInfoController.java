package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.CategorySimpleVo;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 标签信息控制层
 **/
@RestController
@RequestMapping("/ui/blog")
public class MetaInfoController {

    @Autowired
    private MetaInfoService metaInfoService;

    @GetMapping("/meta/categories")
    public BaseResponse<List<CategoryVo>> getCategories(@RequestParam("currentPage") Integer currentPage,
                                                        @RequestParam("pageSize") Integer pageSize) {
        PageInfo<CategoryVo> categories = metaInfoService.getCategoryList(currentPage, pageSize);
        return new PageResponse<>(categories);
    }

    @GetMapping("/meta/keywords")
    public BaseResponse<List<KeywordVo>> getKeywords(@RequestParam("currentPage") Integer currentPage,
                                                     @RequestParam("pageSize") Integer pageSize) {
        PageInfo<KeywordVo> keywords = metaInfoService.getKeywordList(currentPage, pageSize);
        return new PageResponse<>(keywords);
    }

    @GetMapping("/meta/hot/categories")
    public BaseResponse<List<CategoryVo>> getHotCategories() {
        // 只查询文章数据最多的前10个专题信息
        int recentNum=5;
        PageInfo<CategoryVo> categories = metaInfoService.getCategoryList(CoreConstant.FIRST_PAGE,recentNum );
        return new PageResponse<>(categories);
    }

    @GetMapping("/meta/categories/simple")
    public BaseResponse<List<CategorySimpleVo>> getCategoriesSimple(){
        List<CategorySimpleVo> pageInfo=metaInfoService.getCategorySimpleList();
        return new ListResponse<>(pageInfo);
    }
}
