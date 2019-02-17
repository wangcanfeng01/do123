package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.ArticleConstant;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.exception.errorcode.MetaErrorCode;
import com.wcf.funny.blog.mapper.MetaInfoMapper;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/16
 * @function 标签信息查询服务
 **/
@Service
public class MetaInfoServiceImpl implements MetaInfoService {

    @Autowired
    private MetaInfoMapper metaInfoMapper;

    /**
     * 功能描述：  查询专题列表
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    @Override
    public PageInfo<CategoryVo> getCategoryList(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<MetaInfo> metaInfos = metaInfoMapper.getMetasByType(ArticleConstant.CATEGORY_TYPE);
            PageInfo<MetaInfo> pageInfo = new PageInfo<>(metaInfos);
            return convertCategoryPage(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.SELECT_CATEGORY_ERROR, e);
        }
    }

    /**
     * 功能描述：  查询关键字列表
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    @Override
    public PageInfo<KeywordVo> getKeywordList(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<MetaInfo> metaInfos = metaInfoMapper.getMetasByType(ArticleConstant.KEYWORD_TYPE);
            PageInfo<MetaInfo> pageInfo = new PageInfo<>(metaInfos);
            return convertKeywordPage(pageInfo);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.SELECT_KEYWORD_ERROR, e);
        }
    }

    /**
     * 功能描述：  将数据库中的信息转成专题的视图信息
     *
     * @param metaInfoPageInfo
     * @author wangcanfeng
     * @time 2019/2/17 0:22
     * @since v1.0
     **/
    private PageInfo<CategoryVo> convertCategoryPage(PageInfo<MetaInfo> metaInfoPageInfo) {
        PageInfo<CategoryVo> categories = new PageInfo<>();
        if (ObjectUtils.isEmpty(metaInfoPageInfo.getList())) {
            return categories;
        } else {
            List<MetaInfo> metaInfos = metaInfoPageInfo.getList();
            List<CategoryVo> categoryVos = new ArrayList<>();
            metaInfos.forEach(metaInfo -> {
                CategoryVo vo = new CategoryVo();
                vo.setCount(metaInfo.getCount());
                vo.setCover(metaInfo.getCover());
                vo.setDescription(metaInfo.getDescription());
                vo.setId(metaInfo.getId());
                vo.setName(metaInfo.getName());
                vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(metaInfo.getCreateTime()));
                categoryVos.add(vo);
            });
            categories.setList(categoryVos);
            categories.setTotal(metaInfoPageInfo.getTotal());
        }
        return categories;
    }

    /**
     * 功能描述：  将数据库中的信息转成专题的视图信息
     *
     * @param metaInfoPageInfo
     * @author wangcanfeng
     * @time 2019/2/17 0:22
     * @since v1.0
     **/
    private PageInfo<KeywordVo> convertKeywordPage(PageInfo<MetaInfo> metaInfoPageInfo) {
        PageInfo<KeywordVo> keywords = new PageInfo<>();
        if (ObjectUtils.isEmpty(metaInfoPageInfo.getList())) {
            return keywords;
        } else {
            List<MetaInfo> metaInfos = metaInfoPageInfo.getList();
            List<KeywordVo> keywordVos = new ArrayList<>();
            metaInfos.forEach(metaInfo -> {
                KeywordVo vo = new KeywordVo();
                vo.setCount(metaInfo.getCount());
                vo.setId(metaInfo.getId());
                vo.setName(metaInfo.getName());
                keywordVos.add(vo);
            });
            keywords.setList(keywordVos);
            keywords.setTotal(metaInfoPageInfo.getTotal());
        }
        return keywords;
    }
}
