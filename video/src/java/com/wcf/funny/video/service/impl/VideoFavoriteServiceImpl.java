package com.wcf.funny.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.video.entity.VideoFavoriteInfo;
import com.wcf.funny.video.exception.errorcode.VideoActionErrorCode;
import com.wcf.funny.video.mapper.VideoFavoriteMapper;
import com.wcf.funny.video.service.VideoFavoriteService;
import com.wcf.funny.video.vo.VideoInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function 视频收藏服务接口实现类
 **/
@Service
public class VideoFavoriteServiceImpl implements VideoFavoriteService {

    @Autowired
    private VideoFavoriteMapper favoriteMapper;

    /**
     * 功能描述：视频收藏信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/4/19 22:45
     * @since v1.0
     **/
    @Override
    public void collect(VideoFavoriteInfo info) {
        try {
            favoriteMapper.collect(info);
        } catch (Exception e) {
            throw new PgSqlException(VideoActionErrorCode.FAVORITE_VIDEO_ERROR, e);
        }
    }

    /**
     * 功能描述： 删除收藏的视频信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/4/19 22:46
     * @since v1.0
     **/
    @Override
    public void cancelCollect(Integer id) {
        try {
            favoriteMapper.cancelCollect(id);
        } catch (Exception e) {
            throw new PgSqlException(VideoActionErrorCode.CANCEL_VIDEO_ERROR, e);
        }
    }

    /**
     * 功能描述： 查询收藏夹的信息
     *
     * @param currentPage 当前页
     * @param pageSize    分页大小
     * @param collector   收藏者
     * @param type        视频类型
     * @param title       视频标题
     * @author wangcanfeng
     * @time 2019/4/19 22:48
     * @since v1.0
     **/
    @Override
    public PageInfo<VideoInfoVo> getFavorites(Integer currentPage, Integer pageSize,
                                              String collector, String type, String title) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<VideoFavoriteInfo> list = favoriteMapper.getFavorites(collector, type, title);
            return convert(list);
        } catch (Exception e) {
            throw new PgSqlException(VideoActionErrorCode.CANCEL_VIDEO_ERROR, e);
        }
    }

    /**
     * 功能描述：视频是否已经被收藏
     *
     * @param videoTitle 视频标题
     * @param collector  收藏者
     * @author wangcanfeng
     * @time 2019/4/19 22:50
     * @since v1.0
     **/
    @Override
    public boolean isExist(String videoTitle, String collector) {
        try {
            VideoFavoriteInfo info = favoriteMapper.getVideoInfo(videoTitle, collector);
            if (ObjectUtils.isEmpty(info)) {
                return false;
            }
        } catch (Exception e) {
            throw new PgSqlException(VideoActionErrorCode.FAVORITE_VIDEO_EXIST, e);
        }
        return true;
    }

    /**
     * 功能描述：数据库信息转成页面信息
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/4/20 16:05
     * @since v1.0
     **/
    private PageInfo<VideoInfoVo> convert(List<VideoFavoriteInfo> list) {
        PageInfo<VideoFavoriteInfo> source = new PageInfo<>(list);
        PageInfo<VideoInfoVo> pageInfo = new PageInfo<>();
        if (ObjectUtils.isEmpty(list)) {
            return pageInfo;
        } else {
            List<VideoInfoVo> vos = new ArrayList<>(list.size());
            list.forEach(info -> {
                VideoInfoVo vo = new VideoInfoVo();
                vo.setId(info.getId());
                vo.setTitle(info.getTitle());
                vo.setImage(info.getImage());
                vo.setPlayUrl(info.getPlayUrl());
                vo.setType(info.getType());
                vo.setValue(info.getValue());
                vo.setOther(info.getOther());
                vo.setDirector(info.getDirector());
                vo.setSummary(info.getSummary());
                vos.add(vo);
            });
            pageInfo.setList(vos);
            pageInfo.setTotal(source.getTotal());
        }
        return pageInfo;
    }
}
