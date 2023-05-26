package com.site.blog.my.core.mapper;

import com.site.blog.my.core.entity.UserResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-26
 */
public interface UserResourceMapper extends BaseMapper<UserResource> {

    List<UserResource> listVo(Map<String, Object> params);

    UserResource getByIdVo(Integer id);
}
