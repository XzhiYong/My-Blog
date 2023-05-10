package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.service.BlogCoverService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月10日 16:13
 */
@Controller
@RequestMapping("/admin/cover")
public class BlogCoverController {

    @Autowired
    private BlogCoverService blogCoverService;

    @GetMapping
    public String index(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        return "admin/blog_cover";
    }


    @GetMapping("list")
    @ResponseBody
    public Result pageList(@RequestParam Map<String, Object> params) {
        return ResultGenerator.genSuccessResult(blogCoverService.pageList(params));
    }


    @GetMapping("getIds")
    @ResponseBody
    public Result getIds() {
        return ResultGenerator.genSuccessResult(blogCoverService.getIds());
    }


    @ResponseBody
    @PostMapping("delete")
    public Result delete(@RequestBody List<String> ids) {
        return ResultGenerator.genSuccessResult(blogCoverService.removeBatchByIds(ids));
    }
}
