package com.wcf.funny.core.utils;

import org.pegdown.PegDownProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/2/19
 * @function 文章相关的接口工具
 **/
public class ArticleUtils {

    /**
     *@note markdown文字转成html
     *@author WCF
     *@time 2018/6/10 20:52
     *@since v1.0
     * @param markdown
     *@return java.lang.String
     **/
    public static String mdToHtml(String markdown){
        if (ObjectUtils.isEmpty(markdown)) {
            return "";
        }
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String content=pdp.markdownToHtml(markdown);
        return content;
    }
}
