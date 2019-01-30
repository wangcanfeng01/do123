package com.wcf.funny.core.reponse;


import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/1/30
 * @function 列表型返回结果
 **/
public class ListResponse<T> extends BaseResponse<List<T>> {
    /**
     * 查询结果总数
     */
    private Integer total;

    /**
     * 功能描述：  返回列表信息
     *
     * @param data
     * @return
     * @author wangcanfeng
     * @time 2019/1/27 22:53
     * @since v1.0
     **/
    public ListResponse(List<T> data) {
        super(data);
        if (ObjectUtils.isEmpty(data)) {
            this.total = 0;
        } else {
            this.total = data.size();
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
