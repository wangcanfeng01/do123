package com.wcf.funny.home.service.impl;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.core.utils.CrawlerUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.home.constant.HomeConstant;
import com.wcf.funny.home.service.SearchInfoService;
import com.wcf.funny.home.vo.SearchInfoVo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/21
 * @function 查询服务接口实现
 **/
@Service
public class SearchInfoServiceImpl implements SearchInfoService {

    private final static String TENCENT_SEARCH = "https://v.qq.com/x/search/?q=";

    private final static String TENCENT_HOME = "https://v.qq.com";

    @Autowired
    private ArticleInfoService articleInfoService;

    /**
     * 功能描述：根据关键字查询视频信息，视频信息来自互联网
     *
     * @param keyword 关键字
     * @author wangcanfeng
     * @time 2019/4/21 21:11
     * @since v1.0
     **/
    @Override
    public List<SearchInfoVo> searchVideos(String keyword) {
        Document document = CrawlerUtils.getDocWithPC(TENCENT_SEARCH + keyword);
        List<SearchInfoVo> list = new LinkedList<>();
        Elements searchInfos = document.select("div.result_item.result_item_v");
        for (Element search : searchInfos) {
            SearchInfoVo video = new SearchInfoVo();
            String title = search.select("h2.result_title").text();
            String image = search.select("img.figure_pic").attr("src");
            String summary = search.select("div.info_item.info_item_desc").text();
            String url = search.select("a.figure.result_figure").attr("href");
            String director = search.select("div.info_item.info_item_even").text();
            video.setTitle(title);
            video.setType(HomeConstant.SOURCE_VIDEO);
            video.setSummary(summary);
            video.setUrl(url);
            video.setDirector(director);
            video.setImage(image);
            list.add(video);
        }
        return list;
    }

    /**
     * 功能描述：根据关键字查询博客信息
     *
     * @param keyword 关键字
     * @author wangcanfeng
     * @time 2019/4/21 21:11
     * @since v1.0
     **/
    @Override
    public List<SearchInfoVo> searchBlogs(String keyword) {
        List<ArticleInfo> articleInfos = articleInfoService.searchArticleLikeTitle(keyword);
        if (ObjectUtils.isEmpty(articleInfos)) {
            return new ArrayList<>();
        }
        // 预先设置好list的大小，避免扩容
        List<SearchInfoVo> search = new ArrayList<>(articleInfos.size());
        articleInfos.forEach(article -> {
            SearchInfoVo vo = new SearchInfoVo();
            vo.setTitle(article.getTitle());
            vo.setDirector(article.getAuthor() + " | " + FunnyTimeUtils.getTimeByUnixTime(article.getModifyTime()));
            String summary = article.getText();
            // 取前200个字
            if (!ObjectUtils.isEmpty(summary) && summary.length() > 200) {
                summary = summary.substring(0, 200);
            }
            vo.setSummary(summary);
            vo.setImage(article.getCover());
            vo.setType(HomeConstant.SOURCE_BLOG);
            vo.setUrl("/blog/article?slug=" + article.getSlug());
            search.add(vo);
        });
        return search;
    }

    /**
     * 功能描述：  根据关键字查询信息,做的是内存分页
     *
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @param type
     * @author wangcanfeng
     * @time 2019/4/21 21:18
     * @since v1.0
     **/
    @Override
    public PageInfo<SearchInfoVo> searchInfo(String keyword, Integer currentPage, Integer pageSize, String type) {
        List<SearchInfoVo> list = new LinkedList<>();
        List<SearchInfoVo> blogs;
        List<SearchInfoVo> videos;
        switch (type) {
            case HomeConstant.SOURCE_VIDEO: {
                videos = searchVideos(keyword);
                list.addAll(videos);
                break;
            }
            case HomeConstant.SOURCE_BLOG: {
                blogs = searchBlogs(keyword);
                list.addAll(blogs);
                break;
            }
            case HomeConstant.SOURCE_FILE: {

                break;
            }
            default: {
                videos = searchVideos(keyword);
                blogs = searchBlogs(keyword);
                list.addAll(blogs);
                list.addAll(videos);
            }
        }
        List<SearchInfoVo> result;
        PageInfo<SearchInfoVo> pageInfo = new PageInfo<>();
        // 处理分页
        if (!ObjectUtils.isEmpty(list) && list.size() > (pageSize * currentPage)) {
            result = list.subList(pageSize * (currentPage - 1), pageSize);
        } else {
            result = list;
        }
        pageInfo.setList(result);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

}
