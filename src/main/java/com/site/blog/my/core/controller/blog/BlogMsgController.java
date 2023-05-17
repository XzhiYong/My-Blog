package com.site.blog.my.core.controller.blog;

import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 夏志勇
 * @since 2023年05月17日 16:12
 */
@Controller
@RequestMapping("/msg")
public class BlogMsgController extends BaseController {

    @GetMapping({"/index"})
    public String msg(HttpServletRequest request) {

        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        request.setAttribute("configurations", configService.getAllConfigs());
        request.setAttribute("msgList", configService.getAllConfigs());

        return "blog/" + theme + "/msg";
    }
}
