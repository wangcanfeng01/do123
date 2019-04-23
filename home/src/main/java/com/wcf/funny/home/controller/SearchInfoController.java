package com.wcf.funny.home.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.home.service.SearchInfoService;
import com.wcf.funny.home.vo.SearchInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/9
 * @function 查询信息页面控制器
 **/
@RestController
@RequestMapping("/ui/search")
public class SearchInfoController {

    @Autowired
    private SearchInfoService searchInfoService;

    /**
     * 功能描述：根据检索内容关键字查询结果
     *
     * @param keyword     关键词
     * @param type        类型
     * @param currentPage 当前页码
     * @param pageSize    分页大小
     * @author wangcanfeng
     * @time 2019/4/9 23:06
     * @since v1.0
     **/
    @GetMapping("/information")
    public BaseResponse<List<SearchInfoVo>> searchInfo(@RequestParam("keyword") String keyword,
                                                       @RequestParam(value = "type", required = false) String type,
                                                       @RequestParam("currentPage") Integer currentPage,
                                                       @RequestParam("pageSize") Integer pageSize) {
        PageInfo<SearchInfoVo> pageInfo = searchInfoService.searchInfo(keyword, currentPage, pageSize, type);
        return PageResponse.page(pageInfo);
    }


    /**
     * 功能描述：根据检索内容关键字查询博客结果
     *
     * @param keyword 关键词
     * @author wangcanfeng
     * @time 2019/4/9 23:06
     * @since v1.0
     **/
    @GetMapping("/blogs")
    public BaseResponse<List<SearchInfoVo>> searchBlogs(@RequestParam("keyword") String keyword) {
        List<SearchInfoVo> list = searchInfoService.searchBlogs(keyword);
        return ListResponse.list(list);
    }

    /**
     * 功能描述：根据检索内容关键字查询视频结果
     *
     * @param keyword 关键词
     * @author wangcanfeng
     * @time 2019/4/9 23:06
     * @since v1.0
     **/
    @GetMapping("/videos")
    public BaseResponse<List<SearchInfoVo>> searchVideos(@RequestParam("keyword") String keyword) {
        List<SearchInfoVo> list = searchInfoService.searchVideos(keyword);
        return ListResponse.list(list);
    }
}
