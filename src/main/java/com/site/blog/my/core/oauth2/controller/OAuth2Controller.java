package com.site.blog.my.core.oauth2.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.oauth2.handler.impl.OAuth2QQHandlerImpl;
import com.site.blog.my.core.oauth2.handler.impl.OAuth2WeiboHandlerImpl;
import com.site.blog.my.core.oauth2.utils.OAuth2Utils;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import com.site.blog.my.core.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author codecrab
 * @since 2021年06月22日 15:11
 */
@Slf4j
@Controller
public class OAuth2Controller extends BaseController {

    /**
     * 解除账号与第三方社交账号的绑定
     *
     * @param type 第三方社交账号名称
     */
    @ResponseBody
    @PostMapping("/api/unbind")
    public Result unbind(String type) {
        AdminUser profileId = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (profileId == null) {
            return ResultGenerator.genFailResult("您还未登陆");
        }

        return userSocialService.doUnBind(type, profileId.getAdminUserId());
    }    /* ========================== 微博登录 ========================== */

    @GetMapping("/app/weibo")
    public String toWeiboLogin() {
        return "redirect:" + Constants.WEIBO_OAUTH2;
    }

    @GetMapping("/app/binding/weibo")
    public String bindingWeibo(@RequestParam String redirect) {
        Integer id = ShiroUtil.getProfileId();
        if (id == null) {
            return "redirect:/login";
        }
        String uuid = UUID.fastUUID().toString();
        redisUtil.set(Constants.WEIBO_BIND_CODE + id, uuid);
        redirect += "@" + SecureUtil.md5(uuid);
        String format = StrUtil.format(Constants.WEIBO_OAUTH2_URL_BIND_TEM, oAuth2Properties.getWeibo().getClientId(), redirect, Constants.WEIBO_REDIRECT_URI);
        return "redirect:" + format;
    }

    @GetMapping("/oauth2.0/weibo/fail")
    public String weiBoFail() {
        return "redirect:/login";
    }

    @GetMapping("/oauth2.0/weibo/success")
    public String weiBo(@RequestParam String code, @RequestParam(required = false) String state) {
        return OAuth2Utils.socialLoginSuccess(new OAuth2WeiboHandlerImpl(code, state, userSocialService, redisUtil));
    }

    /* ========================== QQ登录 ========================== */

    @GetMapping("/app/qq")
    public String toQQLogin() {
        return "redirect:" + Constants.QQ_OAUTH2;
    }

    @GetMapping("/app/binding/qq")
    public String bindingQq(@RequestParam String redirect) {
        Integer id = ShiroUtil.getProfileId();
        if (id == null) {
            return "admin/login";
        }
        String uuid = UUID.fastUUID().toString();
        redisUtil.set(Constants.QQ_BIND_CODE + id, uuid);
        redirect += "@" + SecureUtil.md5(uuid);
        String format = StrUtil.format(Constants.QQ_OAUTH2_URL_TEM, oAuth2Properties.getQq().getClientId(), redirect, Constants.QQ_REDIRECT_URI);
        return "redirect:" + format;
    }

    @GetMapping("/oauth2.0/qq/success")
    public String qq(@RequestParam String code, @RequestParam(required = false) String state) {
        return OAuth2Utils.socialLoginSuccess(new OAuth2QQHandlerImpl(code, state, userSocialService, redisUtil));
    }
}
