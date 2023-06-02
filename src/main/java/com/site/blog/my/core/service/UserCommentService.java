package com.site.blog.my.core.service;

import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.UserComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
public interface UserCommentService extends IService<UserComment> {

    PageInfo<UserComment> commentPage(Integer commentPage);
}
