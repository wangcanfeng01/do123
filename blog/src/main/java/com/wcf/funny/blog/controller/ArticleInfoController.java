package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.blog.constant.*;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleEditVo;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticlePicInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.blog.vo.req.ArticleEditReq;
import com.wcf.funny.blog.vo.req.ArticleQueryReq;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ErrorResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.service.UploadFileService;
import com.wcf.funny.core.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function 文章信息控制器
 **/
@RestController
@RequestMapping("/ui/blog")
public class ArticleInfoController {

    @Autowired
    private ArticleInfoService articleInfoService;
    @Autowired
    private MetaInfoService metaInfoService;

    @Autowired
    private UploadFileService fileService;

    /**
     * 功能描述：  仅仅用于文章列表展示信息的查询
     *
     * @param currentPage
     * @param pageSize
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/17 15:22
     * @since v1.0
     **/
    @GetMapping("/articleList/simple")
    public BaseResponse<List<ArticleSimpleVo>> getArticleSimpleList(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "title", required = false) String title) {
        PageInfo<ArticleSimpleVo> pageInfo;
        if (ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(title)) {
            pageInfo = articleInfoService.getSimpleArticles(currentPage, pageSize);
        } else {
            // 如果是查询全部，则直接将专题设置为null即可
            if (ArticleConstant.CATEGORY_ALL.equals(category)) {
                category = null;
            }
            ArticleQueryReq req = new ArticleQueryReq();
            req.setCategory(category);
            req.setCurrentPage(currentPage);
            req.setPageSize(pageSize);
            if (!ObjectUtils.isEmpty(title)) {
                req.setTitle("%" + title + "%");
            }
            pageInfo = articleInfoService.getSimpleArticlesByReq(req);
        }
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  查询近期内的文章信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/17 16:11
     * @since v1.0
     **/
    @GetMapping("/recentArticles")
    public BaseResponse<List<ArticleInfoVo>> getRecentList() {
        PageInfo<ArticleInfoVo> pageInfo = articleInfoService.getRecentList();
        return new PageResponse<>(pageInfo);
    }

    /**
     * @param slug
     * @return java.lang.String
     * @note 文章展示页面
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping(value = "/article/{slug}")
    public BaseResponse<ArticleInfoVo> getArticle(@PathVariable("slug") String slug) {
        ArticleInfoVo vo = articleInfoService.getArticleBySlug(slug);
        // 更新文章的点击次数
        articleInfoService.updateHitsById(vo.getId(), vo.getHits() + 1);
        return new BaseResponse<>(vo);
    }

    /**
     * 功能描述：  给文章点赞
     *
     * @param articleId
     * @param stars
     * @author wangcanfeng
     * @time 2019/2/21 22:23
     * @since v1.0
     **/
    @PutMapping("/article/addStars")
    public BaseResponse addStars(@RequestParam("articleId") Integer articleId, @RequestParam("stars") Integer stars) {
        articleInfoService.updateStarsById(articleId, stars);
        return BaseResponse.ok();
    }


    /**
     * 功能描述： 进入文章编辑页面时的初始查询，当slug是空时，创建新的文章，当slug有值是，从数据库中返回已有的文章信息
     *
     * @param slug
     * @author wangcanfeng
     * @time 2019/2/23 20:45
     * @since v1.0
     **/
    @GetMapping("/article/write")
    public BaseResponse writeArticle(@RequestParam(value = "slug", required = false) String slug) {
        ArticleEditVo vo = null;
        // 当slug参数是空的时候,则直接创建新的文章
        if (ObjectUtils.isEmpty(slug)) {
            ArticleInfo info = new ArticleInfo();
            // 默认设置可以评论
            info.setAllowComment(CommentEnableStatus.ALLOW_COMMENT.getStatus());
            // 默认设置公开
            info.setAllowSee(ArticlePrivateStatus.PUBLIC.getStatus());
            // 默认为草稿
            info.setStatus(ArticlePublishStatus.DRAFT.getStatus());
            info.setCreateTime(FunnyTimeUtils.nowUnix());
            info.setText("");
            info.setWords(0);
            info.setHits(0);
            info.setStars(0);
            info.setCommentsNum(0);
            info.setDeleteFlag(ArticleConstant.NOT_DELETE);
            info.setTitle(FunnyTimeUtils.now());
            info.setSlug(UuidUtils.generateShortUuid());
            info.setCover(null);
            info.setModifyTime(FunnyTimeUtils.nowUnix());
            info.setAuthor(RequestUtils.getUserName());
            info.setKeywords(null);
            info.setCategory(ArticleConstant.DEFAULT_CATEGORY);
            // 数据库操作后会给文章对象设置id
            vo = articleInfoService.createNewArticle(info);
            // 给默认专题的计数值+1
//            metaInfoService.increaseMetaByNameAndType(ArticleConstant.DEFAULT_CATEGORY, MetaType.CATEGORY.getType());
        } else {
            // 当slug存在时，查询文章信息
            vo = articleInfoService.getArticleEditBySlug(slug);
            if (ObjectUtils.isEmpty(RequestUtils.getUserName())) {
                // 如果当前用户未登录，直接提示异常信息
                return ErrorResponse.error(UserErrorCode.LOGIN_USER_INFO_ERROR);
            }
            if (ObjectUtils.isEmpty(vo)) {
                return ErrorResponse.error(ArticleErrorCode.ARTICLE_IS_NOT_EXIST);
            }
            // 如果当前用户不是作者，提示不能修改
            if (!Objects.equals(RequestUtils.getUserName(), vo.getAuthor())) {
                return ErrorResponse.error(ArticleErrorCode.ONLY_AUTHOR_CAN_MODIFY);
            }
        }
        return new BaseResponse<>(vo);
    }


    /**
     * 功能描述：  根据类型修改文章
     *
     * @param req
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 22:04
     * @since v1.0
     **/
    @PutMapping("/article/modify/{type}")
    public BaseResponse modifyArticle(@RequestBody ArticleEditReq req, @PathVariable("type") String type) {
        // 判断文章提交的类型，是保存还是发布，保存的话不修改文章的状态
        ArticleInfo info = new ArticleInfo();
        if (ArticlePublishStatus.PUBLISH.getStatus().equals(type)) {
            info.setStatus(type);
        } else if (ArticlePublishStatus.DRAFT.getStatus().equals(type)) {
            info.setStatus(type);
        } else {
            return ErrorResponse.error(ArticleErrorCode.UNSUPPROTED_POST_METHOD);
        }
        //根据id查询原文信息
        ArticleInfoVo source = articleInfoService.getArticleById(req.getId());
        if (ObjectUtils.isEmpty(source)) {
            // 提示原文不存在
            return ErrorResponse.error(ArticleErrorCode.ARTICLE_IS_NOT_EXIST);
        }
        // 如果当前用户不是作者，提示不能修改
        if (!Objects.equals(RequestUtils.getUserName(), source.getAuthor())) {
            return ErrorResponse.error(ArticleErrorCode.ONLY_AUTHOR_CAN_MODIFY);
        }
        info.setKeywords(ConvertIdUtils.getKeywordString(req.getKeywords()));
        info.setCategory(req.getCategory());
        info.setModifyTime(FunnyTimeUtils.nowUnix());
        info.setTitle(req.getTitle());
        // 设置是否可见
        if (req.getAllowSee()) {
            info.setAllowSee(ArticlePrivateStatus.PUBLIC.getStatus());
        } else {
            info.setAllowSee(ArticlePrivateStatus.PRIVATE.getStatus());
        }
        // 是否允许评论
        if (req.getAllowComment()) {
            info.setAllowComment(CommentEnableStatus.ALLOW_COMMENT.getStatus());
        } else {
            info.setAllowComment(CommentEnableStatus.NO_COMMENT.getStatus());
        }
        // 统计文章内字数
        info.setWords(countArticleWords(req.getText()));
        // 更新标签的计数值
        changeMetaCount(source, req);
        // 修改数据库中的文章信息
        articleInfoService.modifyArticleInfo(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：上传文章中的图片
     *
     * @param picture
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 12:38
     * @since v1.0
     **/
    @PostMapping("/articlePic/upload/{id}")
    public BaseResponse<ArticlePicInfoVo> uploadPicInfo(@RequestParam("image") MultipartFile picture, @PathVariable(value = "id") Integer id) {
        // 先调用工具类，完成专题的封面上传
        PictureUploadInfo info = UploadFileUtils.uploadFace(picture, PictureType.ARTICLE_INFO);
        info.setBelongTo(id);
        info.setUploader(RequestUtils.getUserName());
        info.setUploadTime(FunnyTimeUtils.nowUnix());
        fileService.uploadPictureInfo(info);
        ArticlePicInfoVo vo = new ArticlePicInfoVo();
        vo.setPath(info.getPath());
        vo.setUuid(info.getUuid());
        return new BaseResponse<>(vo);
    }

    @DeleteMapping("articlePic/delete/{fileName}")
    public BaseResponse deletePicInfo(@PathVariable("fileName") Integer fileName) {
        return BaseResponse.ok();
    }


    /**
     * 功能描述：改变标签的计数值
     *
     * @param source 原始信息
     * @param req    当前请求信息
     * @author wangcanfeng
     * @time 2019/2/23 22:44
     * @since v1.0
     **/
    private void changeMetaCount(ArticleInfoVo source, ArticleEditReq req) {
        //采用批量更新的方式，把关键词和专题的计数一起更新
    }

    private Integer countArticleWords(String text) {
        return 100;
    }
}
