package com.site.blog.my.core.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 夏志勇
 * @since 2023年03月31日 15:02
 */
@Component
public class TokenUtil {


    public static String getToken(HttpServletRequest request) {
       return request.getHeader("Authorization");
    }
    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {

        //从header中获取token
        String token = httpRequest.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if (StrUtil.isBlank(token)) {
            token = httpRequest.getParameter("Authorization");
        }
        return token;
    }


}
