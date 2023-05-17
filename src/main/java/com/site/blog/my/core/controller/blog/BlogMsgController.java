package com.site.blog.my.core.controller.blog;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.BlogMsg;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
        request.setAttribute("pageName", "消息");
        HashMap<String, Object> params = MapUtil.newHashMap(1);
        params.put("userId", user.getAdminUserId());
        request.setAttribute("msgList", blogMsgService.msgList(params));
        BlogMsg blogMsg=new BlogMsg();
        blogMsg.setState(1);
        blogMsgService.update(blogMsg,new LambdaQueryWrapper<BlogMsg>().eq(BlogMsg::getUId, user.getAdminUserId()));
        return "blog/" + theme + "/msg";
    }
}
