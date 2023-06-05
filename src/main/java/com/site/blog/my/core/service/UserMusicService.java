package com.site.blog.my.core.service;

import com.site.blog.my.core.entity.UserMusic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
public interface UserMusicService extends IService<UserMusic> {

    Result synchronizationMusic(String csrf, String music,String userKey);
}
