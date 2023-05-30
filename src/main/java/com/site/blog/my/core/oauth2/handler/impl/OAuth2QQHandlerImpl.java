package com.site.blog.my.core.oauth2.handler.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.oauth2.handler.OAuth2AbstractHandler;
import com.site.blog.my.core.oauth2.properties.OAuth2Properties;
import com.site.blog.my.core.service.UserSocialService;
import com.site.blog.my.core.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanggang
 * @since 2021年08月23日 下午 2:11
 */
@Slf4j
public class OAuth2QQHandlerImpl extends OAuth2AbstractHandler {

    public OAuth2QQHandlerImpl(String code, String state, UserSocialService userSocialService, RedisUtil redisUtil) {
        this.code = code;
        this.state = state;
        this.redirectUrl = Constants.QQ_REDIRECT_URI;
        this.socialBindCodeKey = Constants.QQ_BIND_CODE;
        this.accessTokenUrl = Constants.QQ_ACCESS_TOKEN;
        this.oAuth2Info = OAuth2Properties.QQ;
        this.userSocialService = userSocialService;
        this.redisUtil = redisUtil;
    }

    @Override
    public String socialLoginSuccess() {
        UserSocial social = this.getAccessToken();
        //QQ登录还需要单独获取一次用户的openId
        social.setSocialUid(this.getQqOpenId(social.getAccessToken()));
        return this.toLoginOrBind(social);
    }

    @Override
    public boolean isLogin() {
        return "login".equals(state);
    }

    private String getQqOpenId(String accessToken) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("fmt", "json");
        HttpResponse execute;
        try {
            execute = HttpRequest.get(Constants.QQ_USER_OPEN_ID).form(map).execute();
        } catch (HttpException e) {
            log.error("远程调用 " + Constants.QQ_USER_OPEN_ID + " 失败", e);
            throw new RuntimeException("远程调用 " + Constants.QQ_USER_OPEN_ID + " 失败！");
        }
        if (!execute.isOk()) {
            throw new RuntimeException("获取QQ用户ID失败！");
        }
        //{"client_id":"YOUR_APPID","openid":"YOUR_OPENID"}
        Map<String, String> userOpenInfo = JSONUtil.toBean(execute.body(), new TypeReference<HashMap<String, String>>() {}, true);
        String openid = userOpenInfo.get("openid");
        if (StrUtil.isBlank(openid)) {
            throw new RuntimeException(userOpenInfo.get("msg"));
        }
        return openid;
    }
}
