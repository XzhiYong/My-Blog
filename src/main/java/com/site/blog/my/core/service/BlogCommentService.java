package com.site.blog.my.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.BlogComment;

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

    PageInfo<BlogComment> commentPage(Integer currentPage, Long blogId);

    boolean deleteComment(Integer cid);
}
