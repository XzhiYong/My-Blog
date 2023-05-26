package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-26
 */
@RestController
@RequestMapping("/user-resource")
public class UserResourceController extends BaseController {

    @PostMapping("/start")
    @ResponseBody
    public Result start(@RequestParam Integer id) {
        return ResultGenerator.genSuccessResult(userResourceService.start(id));
    }

    @PostMapping("/close")
    @ResponseBody
    public Result close(@RequestParam Integer id) {
        return ResultGenerator.genSuccessResult(userResourceService.close(id));
    }

    @PostMapping("/redemption")
    @ResponseBody
    public Result redemption(@RequestParam Integer id) {
        return userResourceService.redemption(id);
    }
}
