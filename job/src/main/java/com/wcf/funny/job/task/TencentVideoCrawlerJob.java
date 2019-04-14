package com.wcf.funny.job.task;

import com.alibaba.fastjson.JSON;
import com.wcf.funny.core.utils.CrawlerUtils;
import com.wcf.funny.job.constant.TaskStatus;
import com.wcf.funny.job.constant.TaskType;
import com.wcf.funny.job.entity.ScheduleTaskInfo;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.job.service.ServerInfoService;
import com.wcf.funny.job.service.TaskLogService;
import com.wcf.funny.video.constant.VideoCacheKey;
import com.wcf.funny.video.service.VideoCacheService;
import com.wcf.funny.video.vo.VideoInfoVo;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function 视频爬虫任务
 **/
@Log4j2
@Component
public class TencentVideoCrawlerJob implements Job {
    @Autowired
    private VideoCacheService cacheService;
    @Autowired
    private TaskLogService taskLogService;
    /**
     * 腾讯视频首页地址
     */
    private static final String HOME_PAGE_PC = "https://v.qq.com/";
    /**
     * 腾讯电视剧地址
     */
    private static final String HOME_PAGE_PHONE_TV = "http://v.qq.com/x/list/tv";
    /**
     * 腾讯电影地址
     */
    private static final String HOME_PAGE_PHONE_MOVIE = "http://v.qq.com/x/list/movie";
    /**
     * 腾讯动漫地址
     */
    private static final String HOME_PAGE_PHONE_CARTOON = "http://v.qq.com/x/list/cartoon";
    /**
     * 腾讯综艺地址
     */
    private static final String HOME_PAGE_PHONE_VARIETY = "http://v.qq.com/x/list/variety";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取任务信息
        String json = context.getMergedJobDataMap().getString("taskInfo");
        ScheduleTaskInfo task = JSON.parseObject(json, ScheduleTaskInfo.class);
        //保存跑马灯视频信息
        cacheService.savesVideos(VideoCacheKey.TENCENT, VideoCacheKey.VIDEO_HOME_CAROUSEL_KEY, carousels());
        //保存综艺视频信息
        cacheService.savesVideos(VideoCacheKey.TENCENT, VideoCacheKey.VIDEO_HOME_VARIETY_SHOW_KEY, crawler(HOME_PAGE_PHONE_VARIETY));
        //保存电视剧信息
        cacheService.savesVideos(VideoCacheKey.TENCENT, VideoCacheKey.VIDEO_HOME_TV_KEY, crawler(HOME_PAGE_PHONE_TV));
        //保存电影信息
        cacheService.savesVideos(VideoCacheKey.TENCENT, VideoCacheKey.VIDEO_HOME_MOVIE_KEY, crawler(HOME_PAGE_PHONE_MOVIE));
        //保存动漫信息
        cacheService.savesVideos(VideoCacheKey.TENCENT, VideoCacheKey.VIDEO_HOME_CARTOON_KEY, crawler(HOME_PAGE_PHONE_CARTOON));
        //如果是单次的任务
        if (TaskType.SINGLE.getInfo().equals(task.getTaskType())) {
            task.setTaskStatus(TaskStatus.FINISHED.getInfo().toString());
        }
        //更新任务信息,内部就将异常处理掉
        try {
            taskLogService.updateTask(task);
        } catch (Exception e) {
            log.error(TaskErrorCode.UPDATE_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述： 爬取腾讯视频跑马信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/4/14 12:46
     * @since v1.0
     **/
    private List<VideoInfoVo> carousels() {
        Document document = CrawlerUtils.getDocWithPC(HOME_PAGE_PC);
        List<VideoInfoVo> list = new ArrayList<>();
        Elements carousels = document.select("div.slider_nav a");
        for (Element carousel : carousels) {
            VideoInfoVo video = new VideoInfoVo();
            String title = carousel.select("div.txt").text();
            String image = carousel.attr("data-bgimage");
            String url = carousel.attr("href");
            video.setValue(url);
            video.setTitle(title);
            video.setImage(image);
            list.add(video);
        }
        return list;
    }

    /**
     * 功能描述：  根据url爬取视频信息
     *
     * @param url
     * @author wangcanfeng
     * @time 2019/4/14 12:52
     * @since v1.0
     **/
    private List<VideoInfoVo> crawler(String url) {
        Document document = CrawlerUtils.getDocWithPC(url);
        List<VideoInfoVo> list = new ArrayList<>();
        Elements elements = document.select("li.list_item a.figure");
        for (Element element : elements) {
            VideoInfoVo video = new VideoInfoVo();
            String source = element.attr("href");
            String title = element.select("img").attr("alt");
            String image = element.select("img").attr("r-lazyload");
            video.setTitle(title);
            video.setImage(image);
            video.setValue(source);
            list.add(video);
        }
        return list;
    }


}
