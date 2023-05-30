package com.site.blog.my.core.oauth2.handler;

import com.site.blog.my.core.entity.UserSocial;

import java.util.Map;

/**
 * OAuth2第三方社交登录顶级接口，后期实现其他社交登录需实现该接口
 *
 * @author wanggang
 * @since 2021年08月23日 下午 1:37
 */
public interface OAuth2Handler {

    /**
     * 外部调用直接完成社交登录后续操作
     *
     * @return 完成后跳转的url路径
     */
    String socialLoginSuccess();

    /**
     * 拼装请求参数
     *
     * @return 参数map
     */
    Map<String, Object> getRequestParams();

    /**
     * 获取Access_Token和其他信息 {@link SocialUserVo}，封装为UserSocial
     *
     * @return {@link UserSocial}
     */
    UserSocial getAccessToken();

    /**
     * 登录或绑定第三方社交用户
     *
     * @param userSocial 第三方社交用户实体类
     * @return 完成后跳转的url路径
     */
    String toLoginOrBind(UserSocial userSocial);

    boolean isLogin();

}
