package com.site.blog.my.core.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeiBoUserInfoVo {

    /**
     * 社交账号Id
     */
    private Long id;

    /**
     * 昵称
     */
    @JsonProperty("screen_name")
    private String screenName;

    /**
     * 城市
     */
    private String location;

    /**
     * 签名
     */
    private String description;

    /**
     * 博客地址
     */
    private String url;

    /**
     * 性别 m：男 f：女
     */
    private String gender;

    /**
     * 大尺寸头像
     */
    @JsonProperty("avatar_hd")
    private String avatarHd;
}
