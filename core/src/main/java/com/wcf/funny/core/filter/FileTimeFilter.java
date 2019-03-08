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

    public FileTimeFilter() {
        //默认的查询时间段设置
        this.end = System.currentTimeMillis();
        this.start = 0l;
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            // 当它是一个文件夹时不做过滤
            return true;
        } else {
            // 当在结束时间和起始时间范围内时返回true
            if (pathname.lastModified() >= start && pathname.lastModified() <= end) {
                return true;
            }
        }
        return false;
    }
}
