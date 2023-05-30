package com.site.blog.my.core.oauth2.utils;

import com.site.blog.my.core.oauth2.handler.OAuth2Handler;

/**
 * @author wanggang
 * @since 2021年08月23日 下午 4:45
 */
public class OAuth2Utils {

    public static String socialLoginSuccess(OAuth2Handler handler) {
        return handler.socialLoginSuccess();
    }

}
