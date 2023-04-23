package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.service.RoleService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:03
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

    @Resource
    private RoleService roleService;


    @GetMapping("/roles")
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "roles");
        request.setAttribute("roles", roleService.list());
        return "admin/role";
    }


    @GetMapping("/roles/list")
    public Result list(@RequestParam Map<String, Object> params) {
  
        return ResultGenerator.genSuccessResult(roleService.pageList(params));
    }

}
