package com.site.blog.my.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.UserMusic;
import com.site.blog.my.core.mapper.UserMusicMapper;
import com.site.blog.my.core.service.UserMusicService;
import com.site.blog.my.core.util.MusicUtil;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import com.site.blog.my.core.util.ShiroUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
@Service
public class UserMusicServiceImpl extends ServiceImpl<UserMusicMapper, UserMusic> implements UserMusicService {


    @Override
    public Result synchronizationMusic(String csrf, String music) {
        AdminUser profile = ShiroUtil.getProfile();
        JSONObject jsonObject = null;
        for (int i = 0; i < 100; i++) {
            try {
                jsonObject = MusicUtil.GET_Date(Constants.DETAIL_ID, StrUtil.format(Constants.MUSIC_USER_KEY, csrf, music));
                if (jsonObject != null) {
                    break;
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject == null) {
            return ResultGenerator.genFailResult("同步失败!");
        }
        List<JSONObject> detailMusic = jsonObject.getBeanList("DetailMusic", JSONObject.class);
        List<UserMusic> userMusics = new ArrayList<>();
        for (JSONObject musicVo : detailMusic) {
            UserMusic userMusic = new UserMusic();
            userMusic.setUserId(profile.getAdminUserId());
            userMusic.setAuthor(musicVo.getStr("Singer"));
            userMusic.setPic(musicVo.getStr("image"));
            userMusic.setTitle(musicVo.getStr("name"));
            userMusic.setUrl(StrUtil.format(Constants.MUSIC_URL, musicVo.getStr("id")));
            userMusics.add(userMusic);
        }
        return ResultGenerator.genSuccessResult(saveBatch(userMusics));
    }
}
