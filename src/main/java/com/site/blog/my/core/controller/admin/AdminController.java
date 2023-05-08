package com.site.blog.my.core.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private LinkService linkService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @Resource
    private RoleService roleService;


    @GetMapping({"/login"})
    public String login(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("user", user);
        return "admin/index";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        List<SysRole> userIdByRole = roleService.getUserIdByRole(user.getAdminUserId());
        request.setAttribute("role", userIdByRole);
        request.setAttribute("user", user);
        return "admin/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "verifyCode",required = false) String verifyCode,
                        @RequestParam(value = "isVerifyCode", defaultValue = "true") boolean isVerifyCode,
                        HttpSession session) {

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
            if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
                session.setAttribute("errorMsg", "验证码错误");
                return "admin/login";
            }
        }
        //获取一个用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);

        try {
            subject.login(usernamePasswordToken);
            AdminUser adminUser = adminUserService.login(userName, password);
            if (adminUser == null) {
                session.setAttribute("errorMsg", "账号不存在!");
                return "admin/login";
            }
            if (adminUser.getLocked() == 1) {
                session.setAttribute("errorMsg", "账号已被锁定，请联系管理员!");
                return "admin/login";
            }
            //登录成功，使用JWT生成token，返回token和redis中
            adminUser.setLoginCount(adminUser.getLoginCount() + 1);
            adminUser.setLastLoginTime(new Date());
            adminUserService.updateById(adminUser);
            if (session.getAttribute("loginUserName") != null) {
                return "redirect:/admin/index";
            }
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            session.setAttribute("loginUserName", adminUser.getLoginUserName());
            session.setAttribute("user", adminUser);
            return "redirect:/admin/index";
        } catch (UnknownAccountException e) {
            session.setAttribute("msg", "用户名错误");
            return "admin/login";
        } catch (IncorrectCredentialsException e) {
            session.setAttribute("msg", "密码错误");
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
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestBody AdminUser adminUser) {

        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        adminUser.setAdminUserId(loginUserId);
        if (adminUserService.updateById(adminUser)) {
            return "success";
        } else {
            return "修改失败";
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
