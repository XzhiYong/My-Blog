package com.site.blog.my.core.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysUserRole;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.RoleService;
import com.site.blog.my.core.util.MD5Util;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
public class AdminUserController extends BaseController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        request.setAttribute("path", "users");
        request.setAttribute("users", adminUserService.list());
        request.setAttribute("roles", roleService.list());
        return "admin/user";
    }

    @GetMapping("password")
    public String password() {
        return "admin/password";
    }

    @ResponseBody
    @PostMapping("password/update")
    public Result updatePassword(@RequestBody AdminUser adminUser) {
        return adminUserService.updateMobilePassword(adminUser);
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
            if (adminUserService.findByUsername(adminUser.getLoginUserName()) != null ||
                adminUserService.findByUsername(adminUser.getMobile()) != null
            ) {
                ResultGenerator.genFailResult("账号或手机号已注册");
            }
            adminUserService.findByUsername(adminUser.getMobile());
            adminUser.setCreateTime(new Date());
            adminUser.setLoginPassword(MD5Util.MD5Encode(adminUser.getLoginPassword(), "UTF-8"));
        } else {
            adminUser.setLoginPassword(null);
        }
        return ResultGenerator.genSuccessResult(adminUserService.saveOrUpdate(adminUser));

    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        return ResultGenerator.genSuccessResult(adminUserService.removeById(id));

    }

    @ResponseBody
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        return ResultGenerator.genSuccessResult(adminUserService.getById(id));

    }

    @ResponseBody
    @PostMapping("/repass")
    public Result repass(@RequestParam String nowPass, @RequestParam String pass, @RequestParam String repass) {
        if (StringUtils.isAnyBlank(nowPass, pass, repass)) {
            return ResultGenerator.genFailResult("密码不能为空");
        }
        if (!StringUtils.equals(pass, repass)) {
            return ResultGenerator.genFailResult("两次输入的密码不一致");
        }
        AdminUser principal = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        AdminUser user = adminUserService.getById(principal.getAdminUserId());
        String md5Pass = MD5Util.MD5Encode(nowPass, "UTF-8");
        if (!StringUtils.equals(md5Pass, user.getLoginPassword())) {
            return ResultGenerator.genFailResult("输入的密码不正确");
        }
        //重新分配盐
        principal.setLoginPassword(MD5Util.MD5Encode(pass, "UTF-8"));
        adminUserService.updateById(principal);
        return ResultGenerator.genSuccessResult();
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5Encode("admin", "UTF-8"));

    }
}
