package com.wcf.funny.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.ArticleConstant;
import com.wcf.funny.blog.entity.CategorySimple;
import com.wcf.funny.blog.entity.MetaChangeInfo;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.exception.errorcode.MetaErrorCode;
import com.wcf.funny.blog.mapper.MetaInfoMapper;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.CategorySimpleVo;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
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
     * 功能描述：  查询专题列表简单信息，用于检索
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     */
    @Override
    public List<CategorySimpleVo> getCategorySimpleList() {
        try {
            List<CategorySimple> metaInfos = metaInfoMapper.getMetasSimpleByType(ArticleConstant.CATEGORY_TYPE);
            if (ObjectUtils.isEmpty(metaInfos)) {
                return Collections.emptyList();
            } else {
                List<CategorySimpleVo> vos = new ArrayList<>();
                metaInfos.forEach(metaInfo -> {
                    CategorySimpleVo vo = new CategorySimpleVo();
                    vo.setName(metaInfo.getName());
                    vo.setId(metaInfo.getId());
                    vos.add(vo);
                });
                return vos;
            }
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
     * 功能描述：  根据id删除标签
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:51
     * @since v1.0
     **/
    @Override
    public void deleteCategoryById(Integer id) {
        try {
            metaInfoMapper.deleteMetaById(id);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.DELETE_CATEGORY_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id删除关键字
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:51
     * @since v1.0
     **/
    @Override
    public void deleteKeywordById(Integer id) {
        try {
            metaInfoMapper.deleteMetaById(id);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.DELETE_KEYWORD_ERROR, e);
        }
    }

    /**
     * 功能描述： 新建一个专题
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:36
     * @since v1.0
     **/
    @Override
    public void addCategory(MetaInfo info) {
        try {
            metaInfoMapper.insertMeta(info);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.INSERT_CATEGORY_ERROR, e);
        }
    }

    /**
     * 功能描述：插入关键字，这个要使用批量的
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:42
     * @since v1.0
     **/
    @Override
    public void addKeyword(List<MetaInfo> info) {

    }

    /**
     * 功能描述：  更新专题信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:43
     * @since v1.0
     **/
    @Override
    public void updateCategory(MetaInfo info) {
        try {
            metaInfoMapper.updateMetaById(info);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.UPDATE_CATEGORY_ERROR, e);
        }
    }

    /**
     * 功能描述： 根据标签id更新封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:03
     * @since v1.0
     **/
    @Override
    public void updateMetaCoverById(String cover, Integer id) {
        try {
            metaInfoMapper.updateMetaCoverById(cover, id);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.UPDATE_CATEGORY_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据名称和类型批量减少标签的统计值
     *
     * @param names
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 16:12
     * @since v1.0
     **/
    @Override
    public void reduceMetaCountByNameAndType(List<String> names, String type) {
        try {
            metaInfoMapper.reduceMetaCountByNameAndTypeBatch(names, type);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.REDUCE_META_COUNT_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据名称单个减少标签的统计值
     *
     * @param name
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 16:19
     * @since v1.0
     **/
    @Override
    public void reduceMetaCountByNameAndType(String name, String type) {
        try {
            metaInfoMapper.reduceMetaCountByNameAndType(FunnyTimeUtils.nowUnix(), name, type);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.REDUCE_META_COUNT_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id获取标签信息
     *
     * @param name
     * @author wangcanfeng
     * @time 2019/2/23 16:27
     * @since v1.0
     **/
    @Override
    public MetaInfo getMetaByNameAndType(String name, String type) {
        try {
            return metaInfoMapper.getMetaByNameAndType(name, type);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.SELECT_META_ERROR, e);
        }
    }


    /**
     * 功能描述：  根据id获取标签信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/14 22:36
     * @since v1.0
     **/
    @Override
    public MetaInfo getMetaById(Integer id) {
        try {
            return metaInfoMapper.getMetaById(id);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.SELECT_META_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据名称和类型，增加标签的统计值
     *
     * @param name
     * @param type
     * @author wangcanfeng
     * @time 2019/2/26 23:10
     * @since v1.0
     **/
    @Override
    public void increaseMetaByNameAndType(String name, String type) {
        try {
            metaInfoMapper.increaseMetaCountByNameAndType(FunnyTimeUtils.nowUnix(), name, type);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.INCREASE_META_COUNT_ERROR, e);
        }
    }

    /**
     * 功能描述： 批量改变标签的计数值
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/2/26 23:16
     * @since v1.0
     **/
    @Override
    public void changeMetaInfo(List<MetaChangeInfo> list) {
        try {
            if (ObjectUtils.isEmpty(list)) {
                return;
            }
            metaInfoMapper.changeMetaCount(list);
        } catch (Exception e) {
            throw new PgSqlException(MetaErrorCode.UPDATE_META_ERROR, e);
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

    /**
     * 功能描述：  将专题转成视图信息
     *
     * @param categorySimples
     * @author wangcanfeng
     * @time 2019/2/22 21:06
     * @since v1.0
     **/
    private PageInfo<CategorySimpleVo> convertCategorySimple(List<CategorySimple> categorySimples) {
        // 只是为了获取list中包含的总条数信息
        PageInfo<CategorySimple> pageInfo = new PageInfo<>(categorySimples);
        PageInfo<CategorySimpleVo> voPageInfo = new PageInfo<>();
        if (ObjectUtils.isEmpty(categorySimples)) {
            return voPageInfo;
        } else {
            List<CategorySimpleVo> vos = new ArrayList<>();
            categorySimples.forEach(categorySimple -> {
                CategorySimpleVo vo = new CategorySimpleVo();
                vo.setId(categorySimple.getId());
                vo.setName(categorySimple.getName());
                vos.add(vo);
            });
            voPageInfo.setList(vos);
            voPageInfo.setTotal(pageInfo.getTotal());
        }
        return voPageInfo;
    }
}
