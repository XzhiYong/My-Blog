package com.site.blog.my.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月23日 18:08
 */
public interface RoleMapper extends BaseMapper<SysRole> {
    
    List<SysRole> getByIds(@Param("ids") List<String> ids);
    
    List<SysRole> getByNotIds(@Param("ids") List<String> ids);
}
