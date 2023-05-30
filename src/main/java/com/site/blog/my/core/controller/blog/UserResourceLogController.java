package com.site.blog.my.core.controller.blog;


import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.UserResourceLog;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/user-resource-log")
public class UserResourceLogController extends BaseController {

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param) {
        PageInfo<UserResourceLog> list1 = userResourceLogService.getList(param);
        param.put("resultCode", 200);
        param.put("message", "SUCCESS");
        param.put("data", list1.getList());
        param.put("total", list1.getTotal());
        return param;
    }

}
