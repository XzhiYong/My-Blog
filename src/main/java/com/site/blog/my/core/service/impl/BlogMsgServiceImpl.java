package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.dao.BlogMsgMapper;
import com.site.blog.my.core.entity.BlogMsg;
import com.site.blog.my.core.service.BlogMsgService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月12日 15:32
 */
@Service
public class BlogMsgServiceImpl extends ServiceImpl<BlogMsgMapper, BlogMsg> implements BlogMsgService {
    @Override
    public PageInfo<BlogMsg> msgList(Map<String, Object> params) {
        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "size", 10));
        return new PageInfo<>(baseMapper.msgList(params));
    }
}
