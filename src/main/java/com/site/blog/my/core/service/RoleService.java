package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.SysRole;

import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:06
 */
public interface RoleService extends IService<SysRole> {
    
    PageInfo<SysRole> pageList(Map<String, Object> params);
}
