package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.SysSign;
import com.site.blog.my.core.entity.SysSignItem;
import com.site.blog.my.core.mapper.SysSignMapper;
import com.site.blog.my.core.service.SysSignItemService;
import com.site.blog.my.core.service.SysSignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-19
 */
@Service
@AllArgsConstructor
public class SysSignServiceImpl extends ServiceImpl<SysSignMapper, SysSign> implements SysSignService {

    public final SysSignItemService sysSignItemService;

    @Override
    public PageInfo<SysSign> pageList(Map<String, Object> params) {
        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "limit", 10));
        List<SysSign> list = list();
        List<SysSign> sysSignList = list
            .stream()
            .peek(item ->
                item.setItems(sysSignItemService.list(new LambdaQueryWrapper<SysSignItem>().ge(SysSignItem::getSingId, item.getId())))
            ).collect(Collectors.toList());
        return new PageInfo<>(sysSignList);


    }

    @Override
    public boolean sign(Map<String, Object> params) {
        return false;
    }
}
