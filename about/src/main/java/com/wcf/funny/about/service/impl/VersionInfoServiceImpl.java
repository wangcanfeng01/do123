package com.wcf.funny.about.service.impl;

import com.wcf.funny.about.exception.errorcode.VersionErrorCode;
import com.wcf.funny.about.entity.VersionInfo;
import com.wcf.funny.about.mapper.VersionInfoMapper;
import com.wcf.funny.about.service.VersionInfoService;
import com.wcf.funny.about.vo.VersionInfoVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/3
 * @function 版本信息服务接口实现
 **/
@Service
public class VersionInfoServiceImpl implements VersionInfoService {
    @Autowired
    private VersionInfoMapper versionInfoMapper;

    /**
     * 功能描述：  获取版本信息列表，倒序列
     *
     * @param author
     * @param order
     * @author wangcanfeng
     * @time 2019/3/3 15:09
     * @since v1.0
     */
    @Override
    public List<VersionInfoVo> getVersionList(String author, Integer order) {
        List<VersionInfo> list;
        try {
            if (order < 0) {
                list = versionInfoMapper.getVersionListDesc(author);
            } else {
                list = versionInfoMapper.getVersionList(author);
            }
            return convertList(list);
        } catch (Exception e) {
            throw new PgSqlException(VersionErrorCode.SELECT_VERSION_ERROR, e);
        }
    }

    /**
     * 功能描述： 插入新的版本信息
     *
     * @param version
     * @param des
     * @param author
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    @Override
    public void insertVersion(String version, String des, String author) {
        try {
            VersionInfo info = new VersionInfo();
            info.setAuthor(author);
            info.setDescription(des);
            info.setModifyTime(FunnyTimeUtils.nowUnix());
            info.setPublishTime(FunnyTimeUtils.nowUnix());
            info.setVersion(version);
            versionInfoMapper.insertVersion(info);
        } catch (Exception e) {
            throw new PgSqlException(VersionErrorCode.INSERT_VERSION_ERROR, e);
        }
    }

    /**
     * 功能描述：  根据id删除版本信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 16:23
     * @since v1.0
     **/
    @Override
    public void deleteVersionById(Integer id) {
        try {
            versionInfoMapper.deleteVersionById(id);
        } catch (Exception e) {
            throw new PgSqlException(VersionErrorCode.DELETE_VERSION_ERROR, e);
        }
    }

    /**
     * 功能描述：  修改版本信息内容
     *
     * @param version
     * @param des
     * @param author
     * @param id
     * @author wangcanfeng
     * @time 2019/3/3 16:25
     * @since v1.0
     **/
    @Override
    public void modifyVersion(String version, String des, String author, Integer id) {
        try {
            VersionInfo info = new VersionInfo();
            info.setId(id);
            info.setAuthor(author);
            info.setDescription(des);
            info.setModifyTime(FunnyTimeUtils.nowUnix());
            info.setVersion(version);
            versionInfoMapper.modifyVersion(info);
        } catch (Exception e) {
            throw new PgSqlException(VersionErrorCode.UPDATE_VERSION_ERROR, e);
        }
    }

    /**
     * 功能描述：  将版本信息转成视图信息
     *
     * @param versionInfos
     * @author wangcanfeng
     * @time 2019/3/3 15:31
     * @since v1.0
     **/
    private List<VersionInfoVo> convertList(List<VersionInfo> versionInfos) {
        if (ObjectUtils.isEmpty(versionInfos)) {
            return Collections.emptyList();
        } else {
            List<VersionInfoVo> vos = new ArrayList<>(versionInfos.size());
            versionInfos.forEach(versionInfo -> {
                VersionInfoVo vo = new VersionInfoVo();
                vo.setId(versionInfo.getId());
                vo.setAuthor(versionInfo.getAuthor());
                vo.setDescription(versionInfo.getDescription());
                vo.setModifyTime(FunnyTimeUtils.getTimeByUnixTime(versionInfo.getModifyTime()));
                vo.setPublishTime(FunnyTimeUtils.getTimeByUnixTime(versionInfo.getPublishTime()));
                vo.setVersion(versionInfo.getVersion());
                vos.add(vo);
            });
            return vos;
        }
    }
}
