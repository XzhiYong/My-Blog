package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.RoleService;
import com.site.blog.my.core.util.MD5Util;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月27日 9:09
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "users");
        request.setAttribute("users", adminUserService.list());
        request.setAttribute("roles", roleService.list());
        return "admin/user";

    }

    @ResponseBody
    @GetMapping("list")
    public Result pageList(@RequestParam Map<String, Object> params) {
        return ResultGenerator.genSuccessResult(adminUserService.pageList(params));

    }

    @PostMapping
    @ResponseBody
    public Result saveOrUpdate(@RequestBody AdminUser adminUser) {

        if (adminUser.getAdminUserId() == null) {
            adminUser.setCreateTime(new Date());
            adminUser.setLoginPassword(MD5Util.MD5Encode(adminUser.getLoginPassword(), "UTF-8"));
        }else {
            adminUser.setLoginPassword(null);
        }
        return ResultGenerator.genSuccessResult(adminUserService.saveOrUpdate(adminUser));

    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(adminUserService.removeById(id));

    }

    @ResponseBody
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(adminUserService.getById(id));

    }

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5Encode("admin", "UTF-8"));
        
    }
}
