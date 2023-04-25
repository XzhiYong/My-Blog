package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.entity.Permission;

import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月24日 16:37
 */
public interface PermissionService extends IService<Permission> {
    
    List<Permission> getTerrList();
    
}
