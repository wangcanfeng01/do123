package com.wcf.funny.blog.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.blog.constant.MetaType;
import com.wcf.funny.blog.entity.MetaInfo;
import com.wcf.funny.blog.exception.errorcode.MetaErrorCode;
import com.wcf.funny.blog.service.MetaInfoService;
import com.wcf.funny.blog.vo.CategorySimpleVo;
import com.wcf.funny.blog.vo.CategoryVo;
import com.wcf.funny.blog.vo.KeywordVo;
import com.wcf.funny.blog.vo.req.CategoryReq;
import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.constant.PictureType;
import com.wcf.funny.core.entity.PictureUploadInfo;
import com.wcf.funny.core.exception.ErrorResponse;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.service.UploadFileService;
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
 * @function 标签信息控制层
 **/
@RestController
@RequestMapping("/ui/blog/meta")
public class MetaInfoController {

    @Autowired
    private MetaInfoService metaInfoService;

    @Autowired
    private UploadFileService fileService;

    /**
     * 功能描述：分页查询专题列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @GetMapping("/categories")
    public BaseResponse<List<CategoryVo>> getCategories(@RequestParam("currentPage") Integer currentPage,
                                                        @RequestParam("pageSize") Integer pageSize) {
        PageInfo<CategoryVo> categories = metaInfoService.getCategoryList(currentPage, pageSize);
        return new PageResponse<>(categories);
    }

    /**
     * 功能描述：  分页查询关键字列表
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @GetMapping("/keywords")
    public BaseResponse<List<KeywordVo>> getKeywords(@RequestParam("currentPage") Integer currentPage,
                                                     @RequestParam("pageSize") Integer pageSize) {
        PageInfo<KeywordVo> keywords = metaInfoService.getKeywordList(currentPage, pageSize);
        return new PageResponse<>(keywords);
    }

    /**
     * 功能描述：查询最热门的专题，也就是文章最多的专题
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @GetMapping("/hot/categories")
    public BaseResponse<List<CategoryVo>> getHotCategories() {
        // 只查询文章数据最多的前10个专题信息
        int recentNum = 5;
        PageInfo<CategoryVo> categories = metaInfoService.getCategoryList(CoreConstant.FIRST_PAGE, recentNum);
        return new PageResponse<>(categories);
    }

    /**
     * 功能描述：查询简单的专题信息，只需要id和名称
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @GetMapping("/categories/simple")
    public BaseResponse<List<CategorySimpleVo>> getCategoriesSimple() {

        List<CategorySimpleVo> pageInfo = metaInfoService.getCategorySimpleList();
        return new ListResponse<>(pageInfo);
    }

    /**
     * 功能描述：删除专题，只有当专题下面的数字为0时可以删除
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @DeleteMapping("/categoryDelete/{id}/{count}")
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.CATEGORY,
            info = LogConstant.ActionInfo.DELETE_CATEGORY)
    public BaseResponse deleteCategoryById(@PathVariable("id") Integer id, @PathVariable Integer count) {
        if (!count.equals(0)) {
            throw new ErrorResponse(MetaErrorCode.CATEGORY_DELETE_DISABLE);
        }
        //查询标签信息,如果原先有封面先删除封面
        MetaInfo info = metaInfoService.getMetaById(id);
        if (!ObjectUtils.isEmpty(info) && !ObjectUtils.isEmpty(info.getCover())) {
            UploadFileUtils.deleteFileByRelative(info.getCover());
            String uuid=UploadFileUtils.getFileName(info.getCover());
            fileService.deletePictureInfo(uuid);
        }
        // 只有当专题下面的统计值大于0的时候才真正执行删除操作
        metaInfoService.deleteCategoryById(id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：删除关键字，只有当专题下面的数字为0时可以删除
     * 除了提示信息和专题接口不一样，其他都是一样的
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 10:44
     * @since v1.0
     **/
    @DeleteMapping("/keywordDelete/{id}/{count}")
    @OperationLog(action = LogConstant.ActionType.DELETE, object = LogConstant.ActionObject.KEYWORD,
            info = LogConstant.ActionInfo.DELETE_KEYWORD)
    public BaseResponse deleteKeywordById(@PathVariable("id") Integer id, @PathVariable Integer count) {
        if (!count.equals(0)) {
            throw new ErrorResponse(MetaErrorCode.KEYWORD_DELETE_DISABLE);
        }
        // 只有当专题下面的统计值大于0的时候才真正执行删除操作
        metaInfoService.deleteKeywordById(id);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  更新专题信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/2/23 12:01
     * @since v1.0
     **/
    @PutMapping("/modify/category")
    @OperationLog(action = LogConstant.ActionType.UPDATE, object = LogConstant.ActionObject.CATEGORY,
            info = LogConstant.ActionInfo.MODIFY_CATEGORY)
    public BaseResponse updateCategory(@RequestBody CategoryReq req) {
        MetaInfo info = new MetaInfo();
        info.setId(req.getId());
        info.setCover(req.getCover());
        info.setModifyTime(FunnyTimeUtils.nowUnix());
        info.setName(req.getName());
        info.setDescription(req.getDescription());
        metaInfoService.updateCategory(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  添加专题信息
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/2/23 12:03
     * @since v1.0
     **/
    @PostMapping("/add/category")
    @OperationLog(action = LogConstant.ActionType.ADD, object = LogConstant.ActionObject.CATEGORY,
            info = LogConstant.ActionInfo.ADD_CATEGORY)
    public BaseResponse addCategory(@RequestBody CategoryReq req) {
        // 先查询一下数据库中是否已经存在该专题信息，如果已经存在，则提示异常信息
        MetaInfo old = metaInfoService.getMetaByNameAndType(req.getName(), MetaType.CATEGORY.getType());
        if (!ObjectUtils.isEmpty(old)) {
            throw new ErrorResponse(MetaErrorCode.CATEGORY_ALREADY_EXIST);
        }
        MetaInfo info = new MetaInfo();
        info.setCreateTime(FunnyTimeUtils.nowUnix());
        info.setModifyTime(FunnyTimeUtils.nowUnix());
        info.setCount(0);
        info.setName(req.getName());
        info.setDescription(req.getDescription());
        info.setType(MetaType.CATEGORY.getType());
        metaInfoService.addCategory(info);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：上传专题封面
     *
     * @param cover
     * @param id
     * @author wangcanfeng
     * @time 2019/2/23 12:38
     * @since v1.0
     **/
    @PostMapping("/uploadCover")
    @OperationLog(action = LogConstant.ActionType.UPLOAD, object = LogConstant.ActionObject.CATEGORY,
            info = LogConstant.ActionInfo.UPLOAD_CATEGORY_COVER)
    public BaseResponse<String> uploadCover(@RequestParam("file") MultipartFile cover,
                                            @RequestParam("id") Integer id,
                                            @RequestParam("path") String path) {
        // 先调用工具类，完成专题的封面上传
        PictureUploadInfo info = UploadFileUtils.uploadPic(cover, PictureType.CATEGORY_COVER);
        info.setBelongTo(id);
        info.setUploader(RequestUtils.getUserName());
        info.setUploadTime(FunnyTimeUtils.nowUnix());
        //如果原先存在图片信息，则先删除
        if (!ObjectUtils.isEmpty(path)) {
            // 提取路径参数中的uuid,先删除数据库中的记录，再删除文件夹中的图片
            fileService.deletePictureInfo(UploadFileUtils.getFileName(path));
            UploadFileUtils.deleteFileByRelative(path);
        }
        fileService.uploadPictureInfo(info);
        metaInfoService.updateMetaCoverById(info.getPath(), id);
        return new BaseResponse<>(info.getPath());
    }
}
