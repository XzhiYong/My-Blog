package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.dao.RoleMapper;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {

    @Override
    public PageInfo<SysRole> pageList(Map<String, Object> params) {

        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "limit", 10));
        List<SysRole> list = list();
        return new PageInfo<>(list);
    }
}
