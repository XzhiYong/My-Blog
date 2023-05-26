package com.site.blog.my.core.controller.blog;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.BlogMsg;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        request.setAttribute("pageName", "消息");
        HashMap<String, Object> params = MapUtil.newHashMap(1);
        params.put("userId", user.getAdminUserId());
        request.setAttribute("msgList", blogMsgService.msgList(params));
        setResource(request, user);
        BlogMsg blogMsg = new BlogMsg();
        blogMsg.setState(1);
        blogMsgService.update(blogMsg, new LambdaQueryWrapper<BlogMsg>().eq(BlogMsg::getUId, user.getAdminUserId()));
        return "blog/" + theme + "/msg";
    }

    @GetMapping({"/count"})
    @ResponseBody
    public Long count() {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        return blogMsgService.count(new LambdaQueryWrapper<BlogMsg>().eq(BlogMsg::getUId, user.getAdminUserId()).eq(BlogMsg::getState, 0));
    }

    @GetMapping({"/list"})
    @ResponseBody
    public PageInfo pageList(@RequestParam Map<String, Object> params) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        params.put("userId", user.getAdminUserId());
        return blogMsgService.msgList(params);
    }

    @ResponseBody
    @PostMapping("/message/remove")
    public Result messageRemove(@RequestParam(required = false) Long id, @RequestParam Boolean all) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (all == null) {
            all = false;
        }
        boolean update = blogMsgService.remove(new LambdaQueryWrapper<BlogMsg>().eq(BlogMsg::getUId, user.getAdminUserId()).eq(!all, BlogMsg::getId, id));
        return update ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult("由于未知原因，删除失败");
    }
}
