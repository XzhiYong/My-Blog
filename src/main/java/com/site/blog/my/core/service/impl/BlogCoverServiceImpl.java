package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.dao.BlogCoverMapper;
import com.site.blog.my.core.entity.BlogCover;
import com.site.blog.my.core.service.BlogCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月10日 16:21
 */
@Service
public class BlogCoverServiceImpl implements BlogCoverService {
    @Autowired
    private BlogCoverMapper blogCoverMapper;

    @Override
    public PageInfo<BlogCover> pageList(Map<String, Object> params) {
        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "limit", 10));
        List<BlogCover> blogCovers = blogCoverMapper.selectList(new QueryWrapper<>());
        return new PageInfo<>(blogCovers);
    }

    @Override
    public Boolean saveCover(String url) {
        BlogCover blogCover = new BlogCover();
        blogCover.setUrl(url);
        blogCover.setCreateTime(new Date());
        return blogCoverMapper.insert(blogCover) > 0;
    }

    @Override
    public List<BlogCover> getIds() {
        return blogCoverMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public boolean removeBatchByIds(List<String> ids) {
        return blogCoverMapper.deleteBatchIds(ids)>0;
    }
}
