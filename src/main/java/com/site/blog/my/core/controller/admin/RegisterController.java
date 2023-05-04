package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.util.SendSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 夏志勇
 * @since 2023年05月04日 10:47
 */
@Controller
@RequestMapping("/admin/register")
public class RegisterController {
    @Autowired
    private SendSmsUtil sendSmsUtil;

    @GetMapping
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "roles");
        return "admin/register";
    }

    @GetMapping("/verificationCode")
    public Boolean verificationCode(@RequestParam String mobile) {
        return sendSmsUtil.verificationCode(mobile);
    }

}
