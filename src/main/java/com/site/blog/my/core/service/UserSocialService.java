package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.auth.AuthToken;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.util.Result;

/**
 * <p>
 * 用户与第三方账户绑定表 服务类
 * </p>
 *
 * @author codecrab
 * @since 2021-08-19
 */
public interface UserSocialService extends IService<UserSocial> {

    UserSocial getBySocialUidAndSocialName(String socialUid, String socialName);

    Boolean getByUserId(String userId, String socialName);

    AuthToken socialLogin(UserSocial userSocial);

    void socialBind(UserSocial userSocial, Integer userId);

    Result doUnBind(String type, Integer profileId);
}
