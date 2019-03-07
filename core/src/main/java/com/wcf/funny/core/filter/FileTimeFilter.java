package com.wcf.funny.core.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author wangcanfeng
 * @time 2019/3/7
 * @function 文件时间过滤器
 **/
public class FileTimeFilter implements FileFilter {

    /**
     * 起始时间
     */
    private Long start;

    /**
     * 结束时间
     */
    private Long end;

    public FileTimeFilter(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean accept(File pathname) {
        return false;
    }
}
