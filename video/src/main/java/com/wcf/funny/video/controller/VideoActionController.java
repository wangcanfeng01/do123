package com.wcf.funny.video.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.I18Utils;
import com.wcf.funny.core.utils.RequestUtils;
import com.wcf.funny.video.constant.VideoType;
import com.wcf.funny.video.entity.VideoFavoriteInfo;
import com.wcf.funny.video.exception.errorcode.VideoActionErrorCode;
import com.wcf.funny.video.service.VideoFavoriteService;
import com.wcf.funny.video.vo.VideoInfoVo;
import com.wcf.funny.video.vo.req.VideoFavoriteReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/4/19
 * @function 视频内容操作
 **/
@RestController
@RequestMapping("/ui/video/action")
public class VideoActionController {

    @Autowired
    private VideoFavoriteService favoriteService;

    /**
     * 功能描述：  收藏监控点信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/4/20 13:39
     * @since v1.0
     **/
    @PostMapping("/collection")
    @OperationLog(action = LogConstant.ActionType.COLLECT, object = LogConstant.ActionObject.VIDEO,
            info = LogConstant.ActionInfo.COLLECT_VIDEO)
    public BaseResponse collectVideo(@RequestBody VideoFavoriteReq req) {
        boolean exist = favoriteService.isExist(req.getTitle(), RequestUtils.getUserName());
        if (exist) {
            //如果视频信息已经存在，则提示已经被收藏
            return BaseResponse.error(VideoActionErrorCode.FAVORITE_VIDEO_EXIST);
        }
        VideoFavoriteInfo info = new VideoFavoriteInfo();
        info.setCollector(RequestUtils.getUserName());
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setImage(req.getImage());
        info.setDirector(req.getDirector());
        info.setOther(req.getOther());
        info.setPlayUrl(req.getPlayUrl());
        info.setSummary(req.getSummary());
        info.setTitle(req.getTitle());
        info.setType(req.getType());
        info.setValue(req.getValue());
        favoriteService.collect(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  取消收藏
     *
     * @param id 收藏夹id
     * @author wangcanfeng
     * @time 2019/4/20 13:40
     * @since v1.0
     **/
    @PostMapping("/cancel/collection")
    @OperationLog(action = LogConstant.ActionType.CANCEL, object = LogConstant.ActionObject.VIDEO,
            info = LogConstant.ActionInfo.CANCEL_COLLECT_VIDEO)
    public BaseResponse cancelCollect(@RequestParam("videoId") Integer id) {
        favoriteService.cancelCollect(id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  分页查询收藏视频信息
     *
     * @param currentPage 当前页码
     * @param pageSize    分页大小
     * @param type        视频类型
     * @param title       视频名称，支持模糊查询
     * @author wangcanfeng
     * @time 2019/4/20 13:44
     * @since v1.0
     **/
    @GetMapping("/favorite/list")
    public BaseResponse<List<VideoInfoVo>> favoriteList(@RequestParam("currentPage") Integer currentPage,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam(value = "type", required = false) String type,
                                                        @RequestParam(value = "title", required = false) String title) {
        String username = RequestUtils.getUserName();
        PageInfo<VideoInfoVo> favorites = favoriteService.getFavorites(currentPage, pageSize, username, type, title);
        return new PageResponse<>(favorites);
    }

    /**
     * 功能描述：  获取类型列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/4/20 17:19
     * @since v1.0
     **/
    @GetMapping("/video/types")
    public BaseResponse<List<CodeAndName>> getTypeList() {
        List<CodeAndName> codeAndNames = new ArrayList<>();
        VideoType[] types = VideoType.values();
        for (VideoType type : types) {
            CodeAndName codeAndName = new CodeAndName();
            codeAndName.setName(I18Utils.getInfoTranslation(type));
            codeAndName.setCode(type.getInfo().toString());
            codeAndNames.add(codeAndName);
        }
        return new ListResponse<>(codeAndNames);
    }
}
