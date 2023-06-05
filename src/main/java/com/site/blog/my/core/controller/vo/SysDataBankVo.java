package com.site.blog.my.core.controller.vo;

import com.site.blog.my.core.entity.SysDataBank;
import lombok.Data;

import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年06月05日 11:47
 */
@Data
public class SysDataBankVo {

    private List<SysDataBank> studyList;

    private List<SysDataBank> imgList;

    private List<SysDataBank> officeList;

    private List<SysDataBank> recreationList;

    private List<SysDataBank> designList;

    private List<SysDataBank> searchList;
    
    private List<SysDataBank> toolList;
}
