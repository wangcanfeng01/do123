package com.wcf.funny.core.utils;

import com.wcf.funny.core.exception.CommonException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 爬虫工具
 **/
@Log4j2
public class CrawlerUtils {

    private static final String UA_PHONE = "Mozilla/5.0 (Linux; Android 4.3; Nexus 10 Build/JSS15Q) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Safari/537.36";
    private static final String UA_PC = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";

    private static final int TIME_OUT = 10 * 1000;

    /**
     * 功能描述：  pc端爬虫
     *
     * @param url
     * @author wangcanfeng
     * @time 2019/4/14 12:35
     * @since v1.0
     **/
    public static Document getDocWithPC(String url) {

        try {
            return Jsoup.connect(url).userAgent(UA_PC).timeout(TIME_OUT).ignoreContentType(true).get();
        } catch (IOException e) {
            throw new CommonException(CommonCode.URL_REQUEST_FAILED, e);
        }
    }

    /**
     * 功能描述：  手机端爬虫
     *
     * @param url
     * @author wangcanfeng
     * @time 2019/4/14 12:35
     * @since v1.0
     **/
    public static Document getDocWithPhone(String url) {

        try {
            return Jsoup.connect(url).userAgent(UA_PHONE).timeout(TIME_OUT).ignoreContentType(true).validateTLSCertificates(false).get();
        } catch (IOException e) {
            throw new CommonException(CommonCode.URL_REQUEST_FAILED, e);
        }
    }
}
