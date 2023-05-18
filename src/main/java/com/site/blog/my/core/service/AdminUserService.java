package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AdminUserService extends IService<AdminUser> {

    AdminUser login(String userName, String password);

    /**
     * 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    AdminUser getUserDetailById(Integer loginUserId);

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

    AdminUser findByUsername(String username);

    PageInfo<AdminUser> pageList(Map<String, Object> params);

    Result register(AdminUser adminUser, HttpServletRequest request);

    Result updateMobilePassword(AdminUser adminUser);
}
