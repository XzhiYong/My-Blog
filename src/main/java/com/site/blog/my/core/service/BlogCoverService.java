package com.site.blog.my.core.service;

import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.BlogCover;

import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月10日 16:21
 */
public interface BlogCoverService {
    
    PageInfo<BlogCover> pageList(Map<String, Object> params);
    
    Boolean saveCover(String url);

    List<BlogCover> getIds();

    boolean removeBatchByIds(List<String> ids);
}
