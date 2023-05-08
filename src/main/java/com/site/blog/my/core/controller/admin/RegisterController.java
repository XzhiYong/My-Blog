package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.SendSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 夏志勇
 * @since 2023年05月04日 10:47
 */
@Controller
@RequestMapping("/admin/register")
public class RegisterController {
    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "roles");
        return "admin/register";
    }

    @ResponseBody
    @GetMapping("/verificationCode")
    public Boolean verificationCode(@RequestParam String mobile) {
        return sendSmsUtil.verificationCode(mobile);
    }

    @ResponseBody
    @PostMapping
    public Result register(@RequestBody AdminUser adminUser,
                           HttpSession session) {
       return adminUserService.register(adminUser,session);
    }

}
