package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户与第三方账户绑定表
 * </p>
 *
 * @author codecrab
 * @since 2021-08-21
 */
@Data
@Accessors(chain = true)
@TableName("m_user_social")
public class UserSocial {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 社交用户id
     */
    private String socialUid;

    /**
     * 社交平台名称
     */
    private String socialName;

    /**
     * 社交平台类型
     */
    private String socialType;

    /**
     * 用户此次登录的Access Token
     */
    private String accessToken;

    /**
     * 针对QQ，在授权自动续期步骤中，获取新的Access_Token时需要提供的参数
     */
    private String refreshToken;

    /**
     * Access Token过期时间
     */
    private Long expiresIn;

    private Integer status;

}
