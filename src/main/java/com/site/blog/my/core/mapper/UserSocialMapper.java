package com.site.blog.my.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.UserSocial;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户与第三方账户绑定表 Mapper 接口
 * </p>
 *
 * @author codecrab
 * @since 2021-08-19
 */
@Repository
public interface UserSocialMapper extends BaseMapper<UserSocial> {

}
