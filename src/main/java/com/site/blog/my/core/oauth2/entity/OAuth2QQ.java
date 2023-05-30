package com.site.blog.my.core.oauth2.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eblog.oauth2.qq")
public class OAuth2QQ implements OAuth2Bean {

    private String socialName;

    private String socialType;

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String redirectUri;

    @Override
    public String getSocialName() {
        return socialName;
    }

    @Override
    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    @Override
    public String getSocialType() {
        return socialType;
    }

    @Override
    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String getGrantType() {
        return grantType;
    }

    @Override
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
