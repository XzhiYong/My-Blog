package com.site.blog.my.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 夏志勇
 * @since 2023年05月04日 13:20
 */

public class UserRoleUtil {

    public static boolean getUserRole( AdminUser user) {
       
        if (user != null) {
            List<SysRole> sysRole = user.getSysRole();
            if (CollUtil.isNotEmpty(sysRole)) {
                String collect = sysRole.stream().map(SysRole::getName).collect(Collectors.joining(","));
                return StrUtil.contains(collect, "系统管理员");
            }
        }
        return false;

    }
}
