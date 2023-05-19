package com.site.blog.my.core.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.mapper.PermissionMapper;
import com.site.blog.my.core.mapper.RolePermissionMapper;
import com.site.blog.my.core.entity.Permission;
import com.site.blog.my.core.entity.RolePermission;
import com.site.blog.my.core.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月24日 16:38
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> getTerrList() {
        return list();
    }

    @Override
    public boolean setPermission(Map<String, Object> params) {
        List<Integer> roleIds = MapUtil.get(params, "ids", new TypeReference<List<Integer>>() {});
        List<Integer> permissionIds = MapUtil.get(params, "permissionIds", new TypeReference<List<Integer>>() {});

        for (Integer roleId : roleIds) {
            rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
            for (Integer permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                rolePermissionMapper.insert(rolePermission);
            }
        }


        return false;
    }

}
