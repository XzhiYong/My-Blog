package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.util.Result;
import org.apache.shiro.authc.UsernamePasswordToken;

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

    UsernamePasswordToken socialLogin(UserSocial userSocial);

    void socialBind(UserSocial userSocial, Integer userId);

    Result doUnBind(String type, Integer profileId);
}
