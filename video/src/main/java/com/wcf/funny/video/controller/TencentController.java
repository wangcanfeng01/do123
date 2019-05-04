package com.wcf.funny.video.controller;

import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.video.constant.VideoCacheKey;
import com.wcf.funny.video.service.VideoCacheService;
import com.wcf.funny.video.vo.VideoHomeVo;
import com.wcf.funny.video.vo.VideoInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author wangcanfeng
 * @time 2019/4/12
 * @function 腾讯视频控制器
 **/
@RestController
@RequestMapping("/ui/video/tencent")
public class TencentController {

    @Autowired
    private VideoCacheService cacheService;

    /**
     * 功能描述：腾讯视频首页信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/4/12 23:19
     * @since v1.0
     **/
    @GetMapping("/home")
    public BaseResponse<VideoHomeVo> videoHome() {
        VideoHomeVo vo = new VideoHomeVo();
        String client = VideoCacheKey.TENCENT;
        vo.setCarousels(cacheService.carousels(client));
        // 对视频信息进行截取
        int count = 10;
        List<VideoInfoVo> varietyShows = cacheService.varietyShow(client).subList(0, count);
        vo.setVarietyShows(varietyShows);
        List<VideoInfoVo> tvHots = cacheService.tvHots(client).subList(0, count);
        vo.setTvHots(tvHots);
        List<VideoInfoVo> cartoons = cacheService.cartoons(client).subList(0, count);
        vo.setCartoons(cartoons);
        List<VideoInfoVo> movies = cacheService.movies(client).subList(0, count);
        vo.setMovies(movies);
        return new BaseResponse<>(vo);
    }

    /**
     * 功能描述：  换一批视频
     *
     * @param key
     * @author wangcanfeng
     * @time 2019/4/13 20:56
     * @since v1.0
     **/
    @GetMapping("/change")
    public BaseResponse<List<VideoInfoVo>> change(@RequestParam("key") String key, @RequestParam("index") Integer index) {
        //取出指定个数的缓存中的视频信息
        List<VideoInfoVo> list = cacheService.changeVideo(VideoCacheKey.TENCENT, key, index);
        return new ListResponse<>(list);
    }

}
