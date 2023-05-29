package com.site.blog.my.core.controller.blog;


import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.SignLog;
import com.site.blog.my.core.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @GetMapping("/log")
    public Map<String,Object> signInLog(@RequestParam Map<String,Object> param) {
        PageInfo<SignLog> data = signService.signInLog(param);
        Map<String,Object> result=new HashMap<>();
        result.put("resultCode",200);
        result.put("message","SUCCESS");
        List<SignLog> list = data.getList();
        result.put("data", list);
        result.put("total",data.getTotal());
        return result;
    }

}
