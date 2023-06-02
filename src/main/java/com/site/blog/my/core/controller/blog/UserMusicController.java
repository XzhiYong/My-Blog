package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/user-music")
public class UserMusicController extends BaseController {

    @GetMapping
    public Result synchronizationMusic(@RequestParam String csrf, @RequestParam String music) {
        return userMusicService.synchronizationMusic(csrf, music);
    }

}
