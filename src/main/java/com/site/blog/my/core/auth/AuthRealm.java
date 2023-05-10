package com.site.blog.my.core.auth;

import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.Permission;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.service.AdminUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author xiazy
 * @Date 2019/3/30 21:38
 * @Version 1.0
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private AdminUserService adminUserService;

    //自定义token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 权限认证校验权限的时候会掉用此方法 获取用户的角色和权限
     *
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        AdminUser user = (AdminUser) principals.getPrimaryPrincipal();
        //Integer userId = user.getUserId();
        //2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SysRole role : user.getSysRole()) {
            //2.1添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            for (Permission permission : role.getPermissions()) {
                //2.1.1添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    /*
      认证 判断token的有效性
     @param  [token]
     @return org.apache.shiro.authc.AuthenticationInfo
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();

        AdminUser user = adminUserService.findByUsername(username);
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked() == 1) {
            throw new LockedAccountException();
        }
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo(user, user.getLoginUserName(), this.getName());
    }
}
