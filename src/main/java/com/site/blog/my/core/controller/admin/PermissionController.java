package com.site.blog.my.core.controller.admin;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.Permission;
import com.site.blog.my.core.entity.RolePermission;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 夏志勇
 * @since 2023年04月24日 16:35
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {

    @PostMapping("/setPermission")
    @ResponseBody
    public Result setPermission(@RequestBody Map<String, Object> params) {
        return ResultGenerator.genSuccessResult(permissionService.setPermission(params));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<Map<String, Object>> list(@PathVariable String id) {

        List<Permission> terrList = permissionService.getTerrList();

        List<RolePermission> rolePermissions = permissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, id));

        List<Map<String, Object>> collect = terrList.stream().map(item -> {
            Map<String, Object> params = new HashMap<>();
            params.put("id", item.getId());
            params.put("pId", item.getPId());
            params.put("name", item.getName());
            return params;
        }).collect(Collectors.toList());
        for (RolePermission rolePermission : rolePermissions) {
            Integer permissionId = rolePermission.getPermissionId();
            for (Map<String, Object> stringObjectMap : collect) {
                if (Objects.equals(MapUtil.getInt(stringObjectMap, "id"), permissionId)) {
                    stringObjectMap.put("checked", true);
                }
            }

        }
        return collect;
    }

}
