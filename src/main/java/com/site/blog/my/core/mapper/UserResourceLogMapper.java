package com.site.blog.my.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.UserResourceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-29
 */
public interface UserResourceLogMapper extends BaseMapper<UserResourceLog> {

    List<UserResourceLog> getList(@Param("param") Map<String, Object> param);
}
