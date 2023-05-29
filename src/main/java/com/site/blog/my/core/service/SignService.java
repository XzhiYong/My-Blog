package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.Sign;
import com.site.blog.my.core.entity.SignLog;
import com.site.blog.my.core.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    PageInfo<SignLog> signInLog(Map<String,Object> param);
    
}
