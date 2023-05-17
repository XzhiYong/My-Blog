package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.BlogMsg;

import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月12日 15:32
 */
public interface BlogMsgService extends IService<BlogMsg> {
    
    PageInfo<BlogMsg> msgList(Map<String,Object> params);
    
}
