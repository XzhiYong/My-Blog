package com.site.blog.my.core.service;

import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.SysSign;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-19
 */
public interface SysSignService extends IService<SysSign> {

    PageInfo<SysSign> pageList(Map<String, Object> params);

    boolean sign(Map<String, Object> params);
}
