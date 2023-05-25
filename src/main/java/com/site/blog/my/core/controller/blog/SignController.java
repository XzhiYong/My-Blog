package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/sign")
public class SignController extends BaseController {

    @ResponseBody
    @PostMapping("/in")
    public Result signIn(HttpServletRequest request) {
        return signService.saveSignIn(request);
    }

}
