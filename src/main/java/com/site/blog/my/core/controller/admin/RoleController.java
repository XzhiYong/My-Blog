package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.service.PermissionService;
import com.site.blog.my.core.service.RoleService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:03
 */
@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;


    @GetMapping
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "roles");
        request.setAttribute("roles", roleService.list());
        request.setAttribute("menus", permissionService.getTerrList());
        return "admin/role";
    }


    @ResponseBody
    @GetMapping("list")
    public Result list(@RequestParam Map<String, Object> params) {

        return ResultGenerator.genSuccessResult(roleService.pageList(params));
    }

    @ResponseBody
    @PostMapping("save")
    public Result save(@RequestParam String roleName) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        SysRole sysRole = new SysRole();
        sysRole.setName(roleName);
        sysRole.setCreateBy(user.getLoginUserName());

        return ResultGenerator.genSuccessResult(roleService.save(sysRole));
    }

    @ResponseBody
    @PostMapping("delete")
    public Result delete(@RequestBody List<String> ids) {

        return ResultGenerator.genSuccessResult(roleService.removeBatchByIds(ids));
    }


}
