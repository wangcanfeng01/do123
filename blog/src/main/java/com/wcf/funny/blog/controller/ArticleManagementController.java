package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.MetaType;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.ErrorResponse;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.service.UploadFileService;
import com.wcf.funny.core.utils.ConvertIdUtils;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import com.wcf.funny.core.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章管理控制器
 **/
@RestController
@RequestMapping("/ui/blog")
public class ArticleManagementController {
    @Autowired
    private ArticleInfoService articleInfoService;

    @Autowired
    private UploadFileService fileService;

    @Autowired
    private MetaInfoService metaInfoService;

    /**
     * 功能描述：分页查询文章列表信息，不需要查询文章内容
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 15:23
     * @since v1.0
     **/
    @GetMapping("/management/list")
    public BaseResponse<List<ArticleInfoVo>> getArticleList(@RequestParam("currentPage") Integer currentPage,
                                                            @RequestParam("pageSize") Integer pageSize) {
        // 直接查询不包含文章内容的信息
        PageInfo<ArticleInfoVo> pageInfo = articleInfoService.getArticles(currentPage, pageSize, false);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：上传文章封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 12:38
     * @since v1.0
     **/
    @PostMapping("/article/addCover")
    @OperationLog(action = LogConstant.ActionType.UPLOAD, object = LogConstant.ActionObject.ARTICLE,
            info = LogConstant.ActionInfo.UPLOAD_ARTICLE_COVER)
    public BaseResponse<String> uploadCover(@RequestParam("file") MultipartFile cover,
                                            @RequestParam("id") Integer id,
                                            @RequestParam(value = "path",required = false) String path) {
        // 先调用工具类，完成文章的封面上传
        PictureUploadInfo info = UploadFileUtils.uploadPic(cover, PictureType.ARTICLE_COVER);
        info.setBelongTo(id);
        info.setUploader(RequestUtils.getUserName());
        info.setUploadTime(FunnyTimeUtils.nowUnix());
        //如果原先存在图片信息，则先删除
        if(!ObjectUtils.isEmpty(path)){
            // 提取路径参数中的uuid,先删除数据库中的记录，再删除文件夹中的图片
            fileService.deletePictureInfo(UploadFileUtils.getFileName(path));
            UploadFileUtils.deleteFileByRelative(path);
        }
        //存储图片信息
        fileService.uploadPictureInfo(info);
        // 更新文章的封面信息
        articleInfoService.updateArticleCoverById(info.getPath(), id);
        return new BaseResponse<>(info.getPath());
    }


    /**
     * @param id
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 通过id删除文章
     * @author WCF
     * @time 2018/6/15 19:07
     * @since v1.0
     **/
    @DeleteMapping("/article/delete/{id}")
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.ARTICLE,
            info = LogConstant.ActionInfo.DELETE_ARTICLE)
    public BaseResponse deleteArticle(@PathVariable("id") Integer id) {
        String userName = RequestUtils.getUserName();
        ArticleInfoVo articleInfo = null;
        // 从数据库中查询文章信息
        articleInfo = articleInfoService.getArticleById(id);
        if (!articleInfo.getAuthor().equals(userName)) {
            throw new ErrorResponse(ArticleErrorCode.ONLY_AUTHOR_CAN_DELETE);
        }
        // 减少关键词的统计值
        if (!ObjectUtils.isEmpty(articleInfo.getKeywords())) {
            List<String> ids = ConvertIdUtils.getStringList(articleInfo.getKeywords());
            // 调用批量执行减少统计值的方法
            metaInfoService.reduceMetaCountByNameAndType(ids, MetaType.KEYWORD.getType());
        }
        // 减少专题的统计值
        if (!ObjectUtils.isEmpty(articleInfo.getCategory())) {
            //减少分类的统计
            metaInfoService.reduceMetaCountByNameAndType(articleInfo.getCategory(), MetaType.CATEGORY.getType());
        }
        // 执行假删除
        articleInfoService.deleteArticleByIdFake(id);
        return BaseResponse.ok();
    }
}
