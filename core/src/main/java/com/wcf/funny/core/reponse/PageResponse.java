package com.wcf.funny.core.reponse;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/28
 * @function 分页的返回结果
 **/
public class PageResponse<T> extends BaseResponse<List<T>> {
    /**
     * 查询结果总数
     */
    private Long total;

    /**
     * 功能描述：  返回列表信息
     *
     * @param data
     * @return
     * @author wangcanfeng
     * @time 2019/1/27 22:53
     * @since v1.0
     **/
    public PageResponse(PageInfo<T> data) {
        super(data.getList());
        this.total = data.getTotal();
    }

    public static <T> PageResponse<T> page(PageInfo<T> data){
        return new PageResponse<>(data);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
