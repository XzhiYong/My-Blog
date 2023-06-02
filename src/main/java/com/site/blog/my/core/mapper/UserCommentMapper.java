package com.site.blog.my.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.UserComment;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
public interface UserCommentMapper extends BaseMapper<UserComment> {

    List<UserComment> selectAllParentCommentNullPage();

    List<UserComment> selectByParentCommentIds(Integer cid);

    UserComment selectByParentCommentId(Integer replyCommentId);
}
