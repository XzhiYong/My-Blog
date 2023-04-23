package com.site.blog.my.core.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String jwtToken = "123456XiaZy!@#$$";

    public static String createToken(Integer userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
            .setClaims(claims) // body数据，要唯一，自行设置
            .setIssuedAt(new Date()) // 设置签发时间
            .setExpiration(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(jwtToken);
            Jwt parse = jwtParser.parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            throw new RuntimeException("未登录!");
        }

    }

}
