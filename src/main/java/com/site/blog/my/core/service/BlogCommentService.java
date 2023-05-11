package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.my.core.entity.BlogComment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jxj4869
 * @since 2020-04-12
 */
public interface BlogCommentService extends IService<BlogComment> {

    boolean saveComment(BlogComment comment);

    void replyRemind(BlogComment comment);

    IPage<BlogComment> commentPage(Integer currentPage, Long blogId);

    boolean deleteComment(Integer cid);
}
