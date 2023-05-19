package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-19
 */
@Controller
@RequestMapping("/admin/sys-sign")
public class SysSignController extends BaseController {

    @GetMapping
    public String index(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        return "admin/sign";
    }

    @ResponseBody
    @GetMapping("list")
    public Result list(@RequestParam Map<String, Object> params) {
        return ResultGenerator.genSuccessResult(sysSignService.pageList(params));
    }

    @ResponseBody
    @RequestMapping("/sign")
    public Result sign(@RequestParam Map<String, Object> params) {
        return ResultGenerator.genSuccessResult(sysSignService.sign(params));
    }

}
