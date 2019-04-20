package com.wcf.funny.video.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wcf.funny.video.constant.VideoCacheKey;
import com.wcf.funny.video.service.VideoCacheService;
import com.wcf.funny.video.vo.VideoInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/13
 * @function 视频缓存信息接口实现类
 **/
@Service
public class VideoCacheServiceImpl implements VideoCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 功能描述：  将爬虫获取到的视频信息存入缓存
     *
     * @param client
     * @param key
     * @param list
     * @author wangcanfeng
     * @time 2019/4/13 22:31
     * @since v1.0
     **/
    @Override
    public void savesVideos(String client, String key, List<VideoInfoVo> list) {
        String json = JSONObject.toJSONString(list);
        stringRedisTemplate.opsForValue().set(client + "_" + key, json);
    }

    /**
     * 功能描述： 跑马灯视频
     *
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> carousels(String client) {
        String key = client + "_" + VideoCacheKey.VIDEO_HOME_CAROUSEL_KEY;
        return getCache(key);
    }

    /**
     * 功能描述：  综艺视频
     *
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> varietyShow(String client) {
        String key = client + "_" + VideoCacheKey.VIDEO_HOME_VARIETY_SHOW_KEY;
        return getCache(key);
    }

    /**
     * 功能描述：  热门电视剧
     *
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> tvHots(String client) {
        String key = client + "_" + VideoCacheKey.VIDEO_HOME_TV_KEY;
        return getCache(key);
    }

    /**
     * 功能描述：  动漫视频
     *
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> cartoons(String client) {
        String key = client + "_" + VideoCacheKey.VIDEO_HOME_CARTOON_KEY;
        return getCache(key);
    }

    /**
     * 功能描述：  电影
     *
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> movies(String client) {
        String key = client + "_" + VideoCacheKey.VIDEO_HOME_MOVIE_KEY;
        return getCache(key);
    }

    /**
     * 功能描述：  换一批视频
     *
     * @param key
     * @param client
     * @param index
     * @author wangcanfeng
     * @time 2019/4/13 21:49
     * @since v1.0
     **/
    @Override
    public List<VideoInfoVo> changeVideo(String client, String key, Integer index) {
        List<VideoInfoVo> list = getCache(client + "_" + key);
        List<VideoInfoVo> result = new LinkedList<>();
        //默认将前10个视频信息挪到后面
        int count = 10;
        // 默认能切换3次
        int round = 3;
        if (!ObjectUtils.isEmpty(list) && list.size() > count) {
            //获取除3的余数
            int remainder = index % round;
            //将第11个视频开始的信息挪到前面
            for (int i = count*remainder; i < (remainder+1)*count; i++) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * 功能描述： 从缓存中获取视频信息
     *
     * @param key
     * @author wangcanfeng
     * @time 2019/4/13 23:08
     * @since v1.0
     **/
    private List<VideoInfoVo> getCache(String key) {
        String json = stringRedisTemplate.opsForValue().get(key);
        List<VideoInfoVo> list = JSONObject.parseArray(json, VideoInfoVo.class);
        return list;
    }
}
