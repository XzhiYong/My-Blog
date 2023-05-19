package com.site.blog.my.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.BlogMsg;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 夏志勇
 * @since 2023年05月12日 15:30
 */
@Repository
public interface BlogMsgMapper extends BaseMapper<BlogMsg> {

    List<BlogMsg> msgList(Map<String, Object> params);
}
