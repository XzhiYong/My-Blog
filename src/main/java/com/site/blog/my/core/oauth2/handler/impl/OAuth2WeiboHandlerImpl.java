package com.site.blog.my.core.oauth2.handler.impl;

import cn.hutool.core.util.StrUtil;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.oauth2.handler.OAuth2AbstractHandler;
import com.site.blog.my.core.oauth2.properties.OAuth2Properties;
import com.site.blog.my.core.service.UserSocialService;
import com.site.blog.my.core.util.RedisUtil;

/**
 * @author wanggang
 * @since 2021年08月23日 下午 2:11
 */
public class OAuth2WeiboHandlerImpl extends OAuth2AbstractHandler {

    public OAuth2WeiboHandlerImpl(String code, String state, UserSocialService userSocialService, RedisUtil redisUtil) {
        this.code = code;
        this.state = state;
        this.redirectUrl = Constants.WEIBO_REDIRECT_URI;
        this.socialBindCodeKey = Constants.WEIBO_BIND_CODE;
        this.accessTokenUrl = Constants.WEIBO_ACCESS_TOKEN;
        this.oAuth2Info = OAuth2Properties.WEIBO;
        this.userSocialService = userSocialService;
        this.redisUtil = redisUtil;
    }

    @Override
    public String socialLoginSuccess() {
        UserSocial social = this.getAccessToken();
        return this.toLoginOrBind(social);
    }

    @Override
    public boolean isLogin() {
        return StrUtil.isBlank(state);
    }

}
