package com.site.blog.my.core.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义AuthenticationToken类
 * @Author 大誌
 * @Date 2019/3/31 10:58
 * @Version 1.0
 */
public class AuthToken extends UsernamePasswordToken {
 
    private String token;
 
    public AuthToken(String token) {
        this.token = token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
}
