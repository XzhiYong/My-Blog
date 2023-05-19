package com.site.blog.my.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.Permission;
import com.site.blog.my.core.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月26日 16:29
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    
    List<Permission> getRoleIdListByPermission(String id);
}
