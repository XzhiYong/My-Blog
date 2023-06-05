package com.site.blog.my.core.controller.blog;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.UserMusic;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ShiroUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Result synchronizationMusic(@RequestParam String csrf, @RequestParam String music, @RequestParam String userKey) {
        return userMusicService.synchronizationMusic(csrf, music,userKey);
    }

    @GetMapping("/list")
    public List<Map<String, String>> list() {
        List<UserMusic> list = userMusicService.list(new QueryWrapper<UserMusic>().eq("user_id", ShiroUtil.getProfile().getAdminUserId()));
        List<Map<String, String>> collect = list.stream().map(item -> {
            Map<String, String> param = MapUtil.newHashMap(4);
            param.put("title", item.getTitle());
            param.put("author", item.getAuthor());
            param.put("url", item.getUrl());
            param.put("pic", item.getPic());
            return param;
        }).collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }


}
