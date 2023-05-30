package com.site.blog.my.core.mapper;

import com.site.blog.my.core.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-25
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    SysResource selectById(Integer id);
}
