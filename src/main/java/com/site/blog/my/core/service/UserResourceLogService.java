package com.site.blog.my.core.service;

import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.UserResourceLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-29
 */
public interface UserResourceLogService extends IService<UserResourceLog> {

    PageInfo<UserResourceLog> getList(Map<String, Object> param);
}
