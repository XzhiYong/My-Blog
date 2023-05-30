package com.site.blog.my.core.oauth2.entity;

import java.io.Serializable;

/**
 * @author 王刚
 * @since 2022年03月08日 15:43
 */
public interface OAuth2Bean extends Serializable {

    String getSocialName();

    void setSocialName(String socialName);

    String getSocialType();

    void setSocialType(String socialType);

    String getClientId();

    void setClientId(String clientId);

    String getClientSecret();

    void setClientSecret(String clientSecret);

    String getGrantType();

    void setGrantType(String grantType);

    String getRedirectUri();

    void setRedirectUri(String redirectUri);

}
