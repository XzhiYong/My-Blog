package com.site.blog.my.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.BlogComment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jxj4869
 * @since 2020-04-12
 */
@Repository
public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    List<BlogComment> selectAllParentCommentNullPage(Long blogId);

    List<BlogComment> selectByParentCommentId(Integer cid);
}
