package com.site.blog.my.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.mapper.PermissionMapper;
import com.site.blog.my.core.mapper.RoleMapper;
import com.site.blog.my.core.mapper.RolePermissionMapper;
import com.site.blog.my.core.mapper.SysUserRoleMapper;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.entity.SysUserRole;
import com.site.blog.my.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public PageInfo<SysRole> pageList(Map<String, Object> params) {

        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "limit", 10));
        List<SysRole> list = list();
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, List<SysRole>> getUserRole(Integer id) {
        Map<String, List<SysRole>> roleMap = new HashMap<>();
        //已经授权的角色
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        List<String> roleIds = sysUserRoles
            .stream()
            .map(SysUserRole::getRoleId)
            .collect(Collectors.toList());
        List<SysRole> roles = baseMapper.getByIds(roleIds);
        if (CollUtil.isEmpty(roleIds)) {
            roleMap.put("roles", new ArrayList<>());

        } else {
            roleMap.put("roles", roles);

        }

        List<SysRole> byNotIds = baseMapper.getByNotIds(roleIds);
        roleMap.put("notRoles", byNotIds);

        return roleMap;
    }

    @Override
    public List<SysRole> getUserIdByRole(Integer id) {
        //已经授权的角色
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        List<String> roleIds = sysUserRoles
            .stream()
            .map(SysUserRole::getRoleId)
            .collect(Collectors.toList());
        List<SysRole> byIds = baseMapper.getByIds(roleIds);
        List<SysRole> collect = byIds.stream().peek(item -> {
            item.setPermissions(rolePermissionMapper.getRoleIdListByPermission(item.getId()));
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean saveUserRole(Map<String, Object> params) {
        String userId = MapUtil.getStr(params, "userId");
        List<String> roleIds = MapUtil.get(params, "roleIds", new TypeReference<List<String>>() {});
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().ge(SysUserRole::getUserId, userId));

        for (String roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(Integer.valueOf(userId));
            sysUserRole.setCreateTime(new Date());
            sysUserRoleMapper.insert(sysUserRole);
        }
        return true;

    }
}
