package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.entity.CategorySimple;
import com.wcf.funny.blog.entity.MetaChangeInfo;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.vo.CategorySimpleVo;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;

import java.util.List;


/**
 * @author wangcanfeng
 * @time 2019/2/16
 * @function 标签信息查询服务
 **/
public interface MetaInfoService {
    /**
     * 功能描述：  查询专题列表
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    PageInfo<CategoryVo> getCategoryList(Integer currentPage, Integer pageSize);

    /**
     * 功能描述：  查询专题列表简单信息，用于检索
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    List<CategorySimpleVo> getCategorySimpleList();

    /**
     * 功能描述：  查询关键字列表
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    PageInfo<KeywordVo> getKeywordList(Integer currentPage, Integer pageSize);

    /**
     * 功能描述：  根据id删除专题
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:51
     * @since v1.0
     **/
    void deleteCategoryById(Integer id);

    /**
     * 功能描述：  根据id删除关键字
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:51
     * @since v1.0
     **/
    void deleteKeywordById(Integer id);

    /**
     * 功能描述： 新建一个专题
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:36
     * @since v1.0
     **/
    void addCategory(MetaInfo info);

    /**
     * 功能描述：插入关键字，这个要使用批量的
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:42
     * @since v1.0
     **/
    void addKeyword(List<MetaInfo> info);

    /**
     * 功能描述：  更新专题信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/2/23 11:43
     * @since v1.0
     **/
    void updateCategory(MetaInfo info);

    /**
     * 功能描述： 根据标签id更新封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 15:03
     * @since v1.0
     **/
    void updateMetaCoverById(String cover, Integer id);

    /**
     * 功能描述：  根据id批量减少标签的统计值
     *
     * @param names
     * @author wangcanfeng
     * @time 2019/2/23 16:12
     * @since v1.0
     **/
    void reduceMetaCountByNameAndType(List<String> names, String type);

    /**
     * 功能描述： 根据名称减少标签的统计值
     *
     * @param name
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 16:19
     * @since v1.0
     **/
    void reduceMetaCountByNameAndType(String name, String type);

    /**
     * 功能描述：  根据名称获取标签信息
     *
     * @param name
     * @author wangcanfeng
     * @time 2019/2/23 16:27
     * @since v1.0
     **/
    MetaInfo getMetaByNameAndType(String name, String type);

    /**
     * 功能描述：  根据id获取标签信息
     *@author wangcanfeng
     *@time 2019/3/14 22:36
     *@since v1.0
     * @param id
     **/
    MetaInfo getMetaById(Integer id);


    /**
     * 功能描述：  根据名称和类型，增加标签的统计值
     *
     * @param name
     * @param type
     * @author wangcanfeng
     * @time 2019/2/26 23:10
     * @since v1.0
     **/
    void increaseMetaByNameAndType(String name, String type);

    /**
     * 功能描述： 批量改变标签的计数值
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/2/26 23:16
     * @since v1.0
     **/
    void changeMetaInfo(List<MetaChangeInfo> list);

}
