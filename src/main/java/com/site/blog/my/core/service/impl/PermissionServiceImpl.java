package com.site.blog.my.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.dao.PermissionMapper;
import com.site.blog.my.core.entity.Permission;
import com.site.blog.my.core.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 夏志勇
 * @since 2023年04月24日 16:38
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> getTerrList() {
        List<Permission> list = list();
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        List<Tree<Integer>> build = TreeUtil.build(list, null, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setName(treeNode.getName());
        });
        return copy(build);
    }

    private List<Permission> copy(List<Tree<Integer>> build) {
        return build.stream().map(item -> {
            Permission sysPermission = new Permission();
            sysPermission.setId(item.getId());
            sysPermission.setName(item.getName().toString());
            if (CollUtil.isNotEmpty(item.getChildren())) {
                sysPermission.setPermissions(copy(item.getChildren()));
            }
            return sysPermission;
        }).collect(Collectors.toList());

    }
}
