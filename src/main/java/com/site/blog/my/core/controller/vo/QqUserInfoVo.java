package com.site.blog.my.core.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author codecrab
 * @since 2021年08月22日 下午 07:11
 */
@Data
public class QqUserInfoVo {

    /** 返回码 */
    @JsonProperty("ret")
    private Integer ret;

    /** 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。 */
    @JsonProperty("msg")
    private String msg;

    @JsonProperty("nickname")
    private String nickname;

    /** 大小为30×30像素的QQ空间头像URL。 */
    @JsonProperty("figureurl")
    private String figureUrl;

    /** 大小为50×50像素的QQ空间头像URL。 */
    @JsonProperty("figureurl_1")
    private String figureUrl1;

    /** 大小为100×100像素的QQ空间头像URL。 */
    @JsonProperty("figureurl_2")
    private String figureUrl2;

    /** 大小为40×40像素的QQ头像URL。 */
    @JsonProperty("figureurl_qq_1")
    private String figureUrlQq1;

    /** 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。 */
    @JsonProperty("figureurl_qq_2")
    private String figureUrlQq2;

    /** 性别。 如果获取不到则默认返回"男" */
    @JsonProperty("gender")
    private String gender;

    @JsonProperty("is_yellow_vip")
    private String isYellowVip;

    @JsonProperty("yellow_vip_level")
    private String yellowVipLevel;

    @JsonProperty("is_yellow_year_vip")
    private String isYellowYearVip;

    @JsonProperty("vip")
    private String vip;

    @JsonProperty("level")
    private String level;
}
