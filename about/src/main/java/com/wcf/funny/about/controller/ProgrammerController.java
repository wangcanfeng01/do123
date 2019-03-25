package com.wcf.funny.about.controller;

import com.wcf.funny.about.vo.ProgrammerVo;
import com.wcf.funny.admin.entity.PersonDetailsInfo;
import com.wcf.funny.admin.service.PersonDetailsService;
import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.utils.ConvertIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 开发者controller
 **/
@RestController
@RequestMapping("/ui/about")
public class ProgrammerController {

    @Autowired
    private PersonDetailsService personDetailsService;

    /**
     * 功能描述：  查询开发者列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/9 21:30
     * @since v1.0
     **/
    @GetMapping("/personDetails")
    public BaseResponse<List<ProgrammerVo>> getProgrammers() {
        List<PersonDetailsInfo> list = personDetailsService.getProgrammerDetails();
        return new ListResponse<>(convertVo(list));
    }

    /**
     * 功能描述：
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/3/9 21:30
     * @since v1.0
     **/
    private List<ProgrammerVo> convertVo(List<PersonDetailsInfo> list) {
        List<ProgrammerVo> vos = new ArrayList<>();
        list.forEach(details -> {
            ProgrammerVo vo = new ProgrammerVo();
            vo.setFacePath(details.getFacePath());
            vo.setEmail(details.getEmail());
            vo.setMind(details.getMind());
            vo.setPersonName(details.getPersonName());
            vo.setTags(ConvertIdUtils.convertTagsToList(details.getTags()));
            vo.setTelephone(details.getTelephone());
            vo.setWorkArea(details.getWorkArea());
            vo.setUsername(details.getUsername());
            vo.setResume(details.getResume());
            vos.add(vo);
        });
        return vos;
    }
}
