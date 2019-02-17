package com.wcf.funny.blog.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;


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
    PageInfo<CategoryVo> getCategoryList(Integer currentPage,Integer pageSize);

    /**
     * 功能描述：  查询关键字列表
     *
     * @author wangcanfeng
     * @time 2019/2/17 0:08
     * @since v1.0
     **/
    PageInfo<KeywordVo> getKeywordList(Integer currentPage,Integer pageSize);

}
