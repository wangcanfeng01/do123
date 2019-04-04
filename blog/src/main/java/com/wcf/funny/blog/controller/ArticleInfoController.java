package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.blog.constant.*;
import com.wcf.funny.blog.entity.ArticleInfo;
import com.wcf.funny.blog.entity.MetaChangeInfo;
import com.wcf.funny.blog.exception.errorcode.ArticleErrorCode;
import com.wcf.funny.blog.service.ArticleInfoService;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.ArticleEditVo;
import com.wcf.funny.blog.vo.ArticleInfoVo;
import com.wcf.funny.blog.vo.ArticlePicInfoVo;
import com.wcf.funny.blog.vo.ArticleSimpleVo;
import com.wcf.funny.blog.vo.req.ArticleEditReq;
import com.wcf.funny.blog.vo.req.ArticleQueryReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.ErrorResponse;
import com.wcf.funny.core.exception.errorcode.FileUploadErrorCode;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.service.UploadFileService;
import com.wcf.funny.core.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @OperationLog(action = LogConstant.ActionType.STAR, object = LogConstant.ActionObject.ARTICLE, info = LogConstant.ActionInfo.STAR_ARTICLE)
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
    @OperationLog(action = LogConstant.ActionType.STAR, object = LogConstant.ActionObject.ARTICLE, info = LogConstant.ActionInfo.MODIFY_ARTICLE)
    public BaseResponse writeArticle(@RequestParam(value = "slug", required = false) String slug) {
        if (ObjectUtils.isEmpty(RequestUtils.getUserName())) {
            // 如果当前用户未登录，直接提示异常信息
            throw new  ErrorResponse(UserErrorCode.LOGIN_USER_INFO_ERROR);
        }
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
            metaInfoService.increaseMetaByNameAndType(ArticleConstant.DEFAULT_CATEGORY, MetaType.CATEGORY.getType());
        } else {
            // 当slug存在时，查询文章信息
            vo = articleInfoService.getArticleEditBySlug(slug);
            if (ObjectUtils.isEmpty(vo)) {
                throw new  ErrorResponse(ArticleErrorCode.ARTICLE_IS_NOT_EXIST);
            }
            // 如果当前用户不是作者，提示不能修改
            if (!Objects.equals(RequestUtils.getUserName(), vo.getAuthor())) {
                throw new  ErrorResponse(ArticleErrorCode.ONLY_AUTHOR_CAN_MODIFY);
            }
        }
        return new BaseResponse<>(vo);
    }


    /**
     * 功能描述：  根据类型修改文章,类型为保存或者发布
     *
     * @param req
     * @param type
     * @author wangcanfeng
     * @time 2019/2/23 22:04
     * @since v1.0
     **/
    @PutMapping("/article/modify/{type}")
    @OperationLog(action = LogConstant.ActionType.SAVE, object = LogConstant.ActionObject.ARTICLE,
            info = LogConstant.ActionInfo.MODIFY_ARTICLE)
    public BaseResponse modifyArticle(@RequestBody ArticleEditReq req, @PathVariable("type") String type) {
        // 判断文章提交的类型，是保存还是发布，保存的话不修改文章的状态
        ArticleInfo info = new ArticleInfo();
        info.setId(req.getId());
        if (ArticlePublishStatus.PUBLISH.getStatus().equals(type)) {
            info.setStatus(type);
            //调用静态方法，修改注解中的操作类型
            RequestUtils.setActionType(LogConstant.ActionType.PUBLISH);
        }
        //根据id查询原文信息
        ArticleInfoVo source = articleInfoService.getArticleById(req.getId());
        if (ObjectUtils.isEmpty(source)) {
            // 提示原文不存在
            throw new ErrorResponse(ArticleErrorCode.ARTICLE_IS_NOT_EXIST);
        }
        // 如果当前用户不是作者，提示不能修改
        if (!Objects.equals(RequestUtils.getUserName(), source.getAuthor())) {
            throw new  ErrorResponse(ArticleErrorCode.ONLY_AUTHOR_CAN_MODIFY);
        }
        // 判断专题信息是否为空
        if (ObjectUtils.isEmpty(req.getCategory())) {
            throw new  ErrorResponse(ArticleErrorCode.CATEGORY_NULL_ERROR);
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
        info.setText(req.getText());
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
     * @param id      文章的id
     * @author wangcanfeng
     * @time 2019/2/23 12:38
     * @since v1.0
     **/
    @PostMapping("/articlePic/upload/{id}")
    @OperationLog(action = LogConstant.ActionType.UPLOAD, object = LogConstant.ActionObject.ARTICLE,
            info = LogConstant.ActionInfo.UPLOAD_ARTICLE_PICTURE)
    public BaseResponse<ArticlePicInfoVo> uploadPicInfo(@RequestParam("image") MultipartFile picture,
                                                        @PathVariable(value = "id") Integer id) {
        // 先调用工具类，完成专题的封面上传
        PictureUploadInfo info = UploadFileUtils.uploadPic(picture, PictureType.ARTICLE_INFO);
        info.setBelongTo(id);
        info.setUploader(RequestUtils.getUserName());
        info.setUploadTime(FunnyTimeUtils.nowUnix());
        fileService.uploadPictureInfo(info);
        ArticlePicInfoVo vo = new ArticlePicInfoVo();
        vo.setPath(info.getPath());
        vo.setUuid(info.getUuid());
        return new BaseResponse<>(vo);
    }

    @DeleteMapping("articlePic/delete")
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.ARTICLE,
            info = LogConstant.ActionInfo.DELETE_ARTICLE_PICTURE)
    public BaseResponse deletePicInfo(@RequestParam("path") String path) {
        //先删除数据库中的图片，再删除实体图片
        if (ObjectUtils.isEmpty(path)) {
            throw new  ErrorResponse(FileUploadErrorCode.FILE_PATH_NULL);
        }
        String[] infos = path.split("/");
        int len = infos.length;
        if (len < 1) {
            len = 1;
        }
        // 提取路径参数中的uuid
        fileService.deletePictureInfo(infos[len - 1]);
        UploadFileUtils.deleteFileByRelative(path);
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
        List<MetaChangeInfo> changeInfos = new ArrayList<>();
        //采用批量更新的方式，把关键词和专题的计数一起更新
        //先更新专题的统计值
        String sourceCategory = source.getCategory();
        String reqCategory = req.getCategory();
        if (ObjectUtils.isEmpty(sourceCategory)) {
            //如果原专题名称为空,直接给新的专题计数值+1
            MetaChangeInfo info = setInfo(MetaType.CATEGORY, IncreaseType.INCREASE, reqCategory);
            changeInfos.add(info);
        } else {
            if (!sourceCategory.equals(reqCategory)) {
                // 如果原专题不是空的，且两次专题不一样，则给原标签-1，当前标签的计数值+1
                MetaChangeInfo sourceInfo = setInfo(MetaType.CATEGORY, IncreaseType.DECREASE, sourceCategory);
                changeInfos.add(sourceInfo);
                MetaChangeInfo reqInfo = setInfo(MetaType.CATEGORY, IncreaseType.INCREASE, reqCategory);
                changeInfos.add(reqInfo);
            }
        }
        // 再更新关键字的统计值
        String sourceKeywords = source.getKeywords();
        List<String> reqKeywords = req.getKeywords();
        if (ObjectUtils.isEmpty(sourceKeywords)) {
            if (ObjectUtils.isEmpty(reqKeywords)) {
                // 如果都是空的，则不做处理
                return;
            } else {
                // 如果原关键字是空的，当前请求不是空的，则执行新增或更新统计值,加入到更新列表
                reqKeywords.forEach(reqKeyword -> {
                    MetaChangeInfo info = setInfo(MetaType.KEYWORD, IncreaseType.NEW_INCREASE, reqKeyword);
                    changeInfos.add(info);
                });
            }
        } else {
            List<String> srcKeywords = ConvertIdUtils.getStringList(sourceKeywords);
            if (ObjectUtils.isEmpty(reqKeywords)) {
                // 如果原关键字不是空的，当前请求的关键字是空的，执行统计值减少，加入到更新列表
                srcKeywords.forEach(keyword -> {
                    MetaChangeInfo info = setInfo(MetaType.KEYWORD, IncreaseType.DECREASE, keyword);
                    changeInfos.add(info);
                });
            } else {
                // 如果都不是空的，则进行比较
                // 处理当前请求关键字
                // 互相遍历
                srcKeywords.forEach(keyword -> {
                    if (!reqKeywords.contains(keyword)) {
                        // 原关键字串中包含，而当前串中没有包含，则减少统计值，加入到更新列表
                        MetaChangeInfo info = setInfo(MetaType.KEYWORD, IncreaseType.DECREASE, keyword);
                        changeInfos.add(info);
                    }
                });
                reqKeywords.forEach(keyword -> {
                    if (!srcKeywords.contains(keyword)) {
                        //原串中不包含，而当前串中包含，加入到更新列表
                        MetaChangeInfo info = setInfo(MetaType.KEYWORD, IncreaseType.NEW_INCREASE, keyword);
                        changeInfos.add(info);
                    }
                });
            }
        }
        metaInfoService.changeMetaInfo(changeInfos);
    }

    /**
     * 功能描述：  简单计算字符串中的字数，统计字数上会有稍微的出入，总体上不影响
     *
     * @param text
     * @author wangcanfeng
     * @time 2019/2/26 21:05
     * @since v1.0
     **/
    private Integer countArticleWords(String text) {
        if (ObjectUtils.isEmpty(text)) {
            return 0;
        }
        // 移除多余的空格
        text = text.replaceAll("[ ]+", " ");
        // 获取字符串总长度
        int total = text.length();
        // 去掉中文字符
        String en = text.replaceAll("[\\u4e00-\\u9fa5]", "");
        if (ObjectUtils.isEmpty(en)) {
            return total;
        }
        // 根据空格切割字符串，得到单词数组
        String[] words = en.split(" ");
        // 计算汉字的个数
        int count = total - en.length();
        // 汉字和单词个数相加
        count += words.length;
        return count;
    }

    /**
     * 功能描述：  设置标签更新信息
     *
     * @param type
     * @param increaseType
     * @param name
     * @author wangcanfeng
     * @time 2019/2/27 21:11
     * @since v1.0
     **/
    private MetaChangeInfo setInfo(MetaType type, IncreaseType increaseType, String name) {
        MetaChangeInfo info = new MetaChangeInfo();
        info.setType(type.getType());
        info.setDecreaseOrIncrease(increaseType);
        info.setName(name);
        return info;
    }

}
