package com.wcf.funny.blog.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/26
 * @function 专题图表视图
 **/
@Data
public class CategoryChartVo {
    /**
     * 项目名称
     */
    private String[] itemName;

    /**
     * 名称和统计值
     */
    private NameAndValue[] nameAndValues;


    public static class NameAndValue {
        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public NameAndValue setName(String name) {
            this.name = name;
            return this;
        }

        public int getValue() {
            return value;
        }

        public NameAndValue setValue(int value) {
            this.value = value;
            return this;
        }
    }
}
