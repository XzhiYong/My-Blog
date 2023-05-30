package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysResource;
import com.site.blog.my.core.entity.UserResource;
import com.site.blog.my.core.entity.UserResourceLog;
import com.site.blog.my.core.mapper.UserResourceMapper;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.SysResourceService;
import com.site.blog.my.core.service.UserResourceLogService;
import com.site.blog.my.core.service.UserResourceService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-26
 */
@Service
public class UserResourceServiceImpl extends ServiceImpl<UserResourceMapper, UserResource> implements UserResourceService {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private UserResourceLogService userResourceLogService;

    @Override
    public List<UserResource> listVo(Map<String, Object> params) {

        return baseMapper.listVo(params);
    }

    @Override
    public UserResource getByIdVo(Integer id) {
        return baseMapper.getByIdVo(id);
    }

    @Override
    public boolean start(Integer id) {
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();

        UserResource userResource = getByIdVo(id);

        Map<String, Object> params = MapUtil.newHashMap(1);
        params.put("userId", adminUser.getAdminUserId());
        //先关闭所有想同类型的资源
        List<UserResource> userResources = listVo(params);


        List<UserResource> sameResources = userResources
            .stream()
            .filter(item -> item.getSysResource().getType().equals(userResource.getSysResource().getType()))
            .peek(item -> item.setStatus(false))
            .collect(Collectors.toList());

        userResource.setStatus(true);

        sameResources.add(userResource);
        return updateBatchById(sameResources);
    }

    @Override
    public boolean close(Integer id) {
        UserResource userResource = new UserResource();
        userResource.setId(id);
        userResource.setStatus(false);
        return updateById(userResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result redemption(Integer id) {
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        adminUser = adminUserService.getById(adminUser.getAdminUserId());

        SysResource sysResource = sysResourceService.getById(id);
        int point = adminUser.getPoint() == null ? 0 : adminUser.getPoint();
        if (sysResource.getPoint() > point) {
            return ResultGenerator.genFailResult("你的积分不足，抓紧多签到！");
        }
        UserResource userResource = new UserResource();
        userResource.setResourceId(id);
        userResource.setUserId(adminUser.getAdminUserId());
        userResource.setStatus(false);
        save(userResource);

        adminUser.setPoint(point - sysResource.getPoint());
        //生成兑换记录
        UserResourceLog userResourceLog = new UserResourceLog();
        userResourceLog.setResourceId(id);
        userResourceLog.setUserId(adminUser.getAdminUserId());
        userResourceLog.setPoint(sysResource.getPoint());
        userResourceLogService.save(userResourceLog);
        return ResultGenerator.genSuccessResult(adminUserService.updateById(adminUser));
    }
}
