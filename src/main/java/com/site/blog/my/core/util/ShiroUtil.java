package com.site.blog.my.core.util;

import cn.hutool.core.lang.Assert;
import com.site.blog.my.core.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author 夏志勇
 * @since 2023年05月30日 16:07
 */
public class ShiroUtil {
    /**
     * 获取当前用户
     */
    public static AdminUser getProfile() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        Assert.notNull(principal, "登录状态失效，请重新登陆");
        return (AdminUser) principal;
    }

    /**
     * 获取当前用户id
     */
    public static Integer getProfileId() {
        AdminUser profile = getProfile();
        Assert.notNull(profile.getAdminUserId(), "登录状态失效，请重新登陆");
        return profile.getAdminUserId();
    }

    /**
     * 切换身份，登录后，动态更改subject的用户属性
     */
    public static void setUser(AdminUser profile) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("userInfo", profile);
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection =
            new SimplePrincipalCollection(profile, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
