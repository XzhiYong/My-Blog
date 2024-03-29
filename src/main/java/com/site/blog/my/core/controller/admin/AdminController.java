package com.site.blog.my.core.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.extra.servlet.ServletUtil;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import com.site.blog.my.core.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link http://13blog.site
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping({"/login"})
    public String login(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("user", user);
        return "admin/index";
    }

    @GetMapping({"/note"})
    public String note() {
        return "admin/note";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        AdminUser user = ShiroUtil.getProfile();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("user", user);
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        List<SysRole> userIdByRole = roleService.getUserIdByRole(user.getAdminUserId());
        request.setAttribute("role", userIdByRole);
        return "admin/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "verifyCode", required = false) String verifyCode,
                        @RequestParam(value = "isVerifyCode", defaultValue = "true") boolean isVerifyCode,
                        HttpSession session, HttpServletRequest request) {

        if (!StringUtils.hasText(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        if (isVerifyCode) {
            ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
            String code = shearCaptcha.getCode();
            log.info("系统验证码:" + code);
            log.info("用户验证码:" + verifyCode);
            if (!code.equals(verifyCode)) {
                session.setAttribute("errorMsg", "验证码错误");
                return "admin/login";
            }
        }
        try {
            AdminUser adminUser = adminUserService.login(userName, password);
            int i = adminUser.getLoginCount() == null ? 0 : adminUser.getLoginCount();
            int count = i + 1;
            adminUser.setLoginCount(count);
            adminUser.setLastLoginTime(new Date());
            String clientIp = ServletUtil.getClientIP(request);
            adminUser.setIp(clientIp);
            adminUserService.updateById(adminUser);
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            session.setAttribute("loginUserName", adminUser.getLoginUserName());
            session.setAttribute("user", adminUser);
            return "redirect:/index";
        } catch (UnknownAccountException uae) {
            session.setAttribute("errorMsg", "账号不存在!");
            return "admin/login";
        } catch (IncorrectCredentialsException ice) {
            session.setAttribute("errorMsg", "密码不正确!");
            return "admin/login";
        } catch (LockedAccountException lae) {
            session.setAttribute("errorMsg", "账号被锁定!");
            return "admin/login";
        } catch (AuthenticationException ae) {
            session.setAttribute("errorMsg", "用户名或密码不正确!");
            return "admin/login";
        }
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("user", user);
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (!StringUtils.hasText(originalPassword) || !StringUtils.hasText(newPassword)) {
            return "参数不能为空";
        }
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        if (adminUserService.updatePassword(user.getAdminUserId(), originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/user/update")
    @ResponseBody
    public Result userUpdate(@RequestBody AdminUser adminUser) {

        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        adminUser.setAdminUserId(user.getAdminUserId());
        if (adminUserService.updateById(adminUser)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUserName");
        request.getSession().removeAttribute("errorMsg");
        request.getSession().removeAttribute("token");
        request.getSession().removeAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "admin/login";
    }
}
