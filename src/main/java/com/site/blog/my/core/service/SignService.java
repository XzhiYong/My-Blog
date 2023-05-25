package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.entity.Sign;
import com.site.blog.my.core.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-25
 */
public interface SignService extends IService<Sign> {

    Result saveSignIn(HttpServletRequest request);

}
