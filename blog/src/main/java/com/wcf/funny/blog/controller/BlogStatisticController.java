package com.wcf.funny.blog.controller;

import com.wcf.funny.blog.constant.ArticleConstant;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.service.ArticleStatisticService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleStatisticVo;
import com.wcf.funny.blog.vo.BlogIndexVo;
import com.wcf.funny.blog.vo.CategoryChartVo;
import com.wcf.funny.core.entity.ChartInfo;
import com.wcf.funny.core.entity.NameAndCount;
import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/25
 * @function 博客统计信息controller
 **/
@RestController
@RequestMapping("/ui/blog/statistic")
public class BlogStatisticController {

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

    /**
     * 功能描述：  获取专题总体分布情况
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 21:53
     * @since v1.0
     **/
    @GetMapping("/categories")
    public BaseResponse<CategoryChartVo> getCategories() {
        List<MetaInfo> metaInfoList = metaInfoService.getCategoryList();
        CategoryChartVo chart = new CategoryChartVo();
        int size = 10;
        String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = "--";
        }
        chart.setItemName(names);
        CategoryChartVo.NameAndValue[] nameAndValues = new CategoryChartVo.NameAndValue[size];
        for (int i = 0; i < size; i++) {
            nameAndValues[i] = new CategoryChartVo.NameAndValue().setName("--").setValue(0);
        }
        chart.setNameAndValues(nameAndValues);
        if (!ObjectUtils.isEmpty(metaInfoList)) {
            int len = metaInfoList.size();
            if (len > size) {
                //当查询到的结果超过10个时，将后面标签进行合并
                int count = 0;
                for (int i = size; i < len; i++) {
                    // 统计值累加
                    count += metaInfoList.get(i).getCount();
                }
                metaInfoList.get(size - 1).setCount(count);
                metaInfoList.get(size - 1).setName("other");
            }
            len = size;
            for (int i = 0; i < len; i++) {
                MetaInfo info = metaInfoList.get(i);
                names[i] = info.getName();
                nameAndValues[i].setName(info.getName());
                nameAndValues[i].setValue(info.getCount());
            }
        }
        return new BaseResponse<>(chart);
    }


    /**
     * 功能描述：查询近期有博客内容修改过的专题
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 22:16
     * @since v1.0
     **/
    @GetMapping("/recentCategory")
    public BaseResponse<ChartInfo> getRecentCategory() {
        int limit = 7;
        List<MetaInfo> metaInfos = metaInfoService.getRecentMetaList(limit, ArticleConstant.CATEGORY_TYPE);
        ChartInfo chartInfo = new ChartInfo();
        String[] axis = new String[limit];
        //预填充空值
        for (int i = 0; i < limit; i++) {
            axis[i] = "--";
        }
        int[] value = new int[limit];
        chartInfo.setAxis(axis);
        chartInfo.setValue(value);
        if (!ObjectUtils.isEmpty(metaInfos)) {
            for (int i = 0; i < limit; i++) {
                axis[i] = metaInfos.get(i).getName();
                value[i] = metaInfos.get(i).getCount();
            }
        }
        return new BaseResponse<>(chartInfo);
    }

    /**
     * 功能描述：查询最受欢迎的文章
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 22:16
     * @since v1.0
     **/
    @GetMapping("/popularArticle")
    public BaseResponse<ChartInfo> getPopularArticle() {
        int limit = 7;
        List<ArticleInfo> articleInfos = articleStatisticService.getPopularArticles();
        Collections.reverse(articleInfos);
        ChartInfo chartInfo = new ChartInfo();
        String[] axis = new String[limit];
        //预填充空值
        for (int i = 0; i < limit; i++) {
            axis[i] = "--";
        }
        int[] value = new int[limit];
        chartInfo.setAxis(axis);
        chartInfo.setValue(value);
        if (!ObjectUtils.isEmpty(articleInfos)) {
            for (int i = 0; i < limit; i++) {
                axis[i] = articleInfos.get(i).getTitle();
                value[i] = articleInfos.get(i).getStars();
            }
        }
        return new BaseResponse<>(chartInfo);
    }

    /**
     * 功能描述： 查询博客各项指数
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/26 23:47
     * @since v1.0
     **/
    @GetMapping("/blogIndex")
    public BaseResponse<long[]> getBlogIndex() {
        BlogIndexVo index = articleStatisticService.getBlogIndex();
        Long pictures=articleStatisticService.getArticlePictures();
        index.setPictures(pictures/2);
        long[] indexes=new long[5];
        indexes[0]=index.getWords()/1000;
        indexes[1]=index.getPictures()/2;
        indexes[2]=index.getHits()/20;
        indexes[3]=index.getStars();
        indexes[4]=index.getComments();
        return new BaseResponse<>(indexes);
    }


    @GetMapping("/recentArticles")
    public BaseResponse<ChartInfo> getRecentArticleCount(){
        ChartInfo chart=new ChartInfo();
        int month=7;
        List<NameAndCount> nameAndCounts=articleStatisticService.getRecentArticleCount(month);
        String[] axis = new String[month];
        //预填充空值
        for (int i = 0; i < month; i++) {
            axis[i] = "--";
        }
        int[] value = new int[month];
        chart.setAxis(axis);
        chart.setValue(value);
        if (!ObjectUtils.isEmpty(nameAndCounts)) {
            for (int i = 0; i < nameAndCounts.size(); i++) {
                axis[i] = nameAndCounts.get(i).getName();
                value[i] = nameAndCounts.get(i).getCount();
            }
        }
        return new BaseResponse<>(chart);
    }

}
