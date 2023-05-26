package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.entity.UserResource;
import com.site.blog.my.core.util.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-26
 */
public interface UserResourceService extends IService<UserResource> {

    List<UserResource> listVo( Map<String, Object> params);

    UserResource getByIdVo(Integer id);

    boolean start(Integer id);

    boolean close(Integer id);

    Result redemption(Integer id);
}
