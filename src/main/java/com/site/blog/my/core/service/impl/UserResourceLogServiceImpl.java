package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.UserResourceLog;
import com.site.blog.my.core.mapper.UserResourceLogMapper;
import com.site.blog.my.core.service.UserResourceLogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-29
 */
@Service
public class UserResourceLogServiceImpl extends ServiceImpl<UserResourceLogMapper, UserResourceLog> implements UserResourceLogService {

    @Override
    public PageInfo<UserResourceLog> getList(Map<String, Object> param) {
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(MapUtil.getInt(param, "page", 1), MapUtil.getInt(param, "limit", 10));
        param.put("userId", adminUser.getAdminUserId());
        List<UserResourceLog> list = baseMapper.getList(param);
        return new PageInfo<>(list);
    }
}
