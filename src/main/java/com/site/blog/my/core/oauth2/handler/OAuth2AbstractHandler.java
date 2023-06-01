package com.site.blog.my.core.oauth2.handler;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.site.blog.my.core.auth.AuthToken;
import com.site.blog.my.core.entity.SocialUserVo;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.oauth2.entity.OAuth2Bean;
import com.site.blog.my.core.service.UserSocialService;
import com.site.blog.my.core.util.RedisUtil;
import com.site.blog.my.core.util.ShiroUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanggang
 * @since 2021年08月23日 下午 1:37
 */
@Data
@Slf4j
@Accessors(chain = true)
public abstract class OAuth2AbstractHandler implements OAuth2Handler {

    protected String code;

    protected String state;

    protected String socialBindCodeKey;

    protected String redirectUrl;

    protected String accessTokenUrl;

    protected OAuth2Bean oAuth2Info;

    protected UserSocialService userSocialService;

    protected RedisUtil redisUtil;

    @Override
    public Map<String, Object> getRequestParams() {
        //拼装请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", oAuth2Info.getClientId());
        params.put("client_secret", oAuth2Info.getClientSecret());
        params.put("grant_type", oAuth2Info.getGrantType());
        params.put("redirect_uri", redirectUrl);
        params.put("fmt", "json");
        return params;
    }

    @Override
    public UserSocial getAccessToken() {
        //拿到微博的回调，换取Access_Token
        HttpResponse response;
        try {
            response = HttpRequest.post(accessTokenUrl).form(this.getRequestParams()).execute();
        } catch (HttpException e) {
            log.error("远程调用 " + accessTokenUrl + " 失败", e);
            throw new RuntimeException("远程调用 " + accessTokenUrl + " 失败！");
        }
        if (!response.isOk()) {
            String body = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            throw new RuntimeException("换取Access_Token失败，错误原因+" + jsonObject);
        }

        //把微博的回调Json转为微博社交Vo
        SocialUserVo userVo = JSONUtil.toBean(response.body(), SocialUserVo.class);
        UserSocial userSocial = new UserSocial();
        userSocial.setSocialUid(userVo.getUid());
        userSocial.setSocialName(oAuth2Info.getSocialName());
        userSocial.setSocialType(oAuth2Info.getSocialType());
        userSocial.setExpiresIn(userVo.getExpiresIn());
        userSocial.setAccessToken(userVo.getAccessToken());
        userSocial.setRefreshToken(userVo.getRefreshToken());
        return userSocial;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String toLoginOrBind(UserSocial userSocial) {
        if (this.isLogin()) {
            AuthToken token = userSocialService.socialLogin(userSocial);
            //执行登录
            SecurityUtils.getSubject().login(token);
            return "redirect:/index";
        } else {
            Integer id = ShiroUtil.getProfileId();
            if (id == null) {
                throw new RuntimeException("你还未登陆或登录状态已过期，请登录后再试。");
            }
            String[] codes = state.split("@");
            if (codes.length == 2) {
                String md5 = codes[1];
                String redisCode = (String) redisUtil.get(socialBindCodeKey + id);
                redisUtil.del(socialBindCodeKey + id);
                if (!SecureUtil.md5(redisCode).equals(md5)) {
                    throw new RuntimeException("验证状态有误，请重试！");
                }

                userSocialService.socialBind(userSocial, id);
                return "redirect:" + state.split("@")[0].replace("-", "#");
            }
            throw new RuntimeException("验证状态有误，请重试！");
        }
    }

    @Override
    public abstract String socialLoginSuccess();

    @Override
    public abstract boolean isLogin();
}
