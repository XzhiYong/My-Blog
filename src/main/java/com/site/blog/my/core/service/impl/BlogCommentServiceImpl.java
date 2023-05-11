package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.site.blog.my.core.dao.BlogCommentMapper;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jxj4869
 * @since 2020-04-12
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

    @Autowired
    BlogCommentMapper blogCommentMapper;

    @Override
    public IPage<BlogComment> commentPage(Integer currentPage, Long blogId) {
        PageHelper.startPage(currentPage, 10);
        List<BlogComment> blogComments = blogCommentMapper.selectAllParentCommentNullPage(blogId);
        Page<BlogComment> page = new Page<>();
        page.setRecords(blogComments);
        page.setSize(10);
        page.setCurrent(currentPage);
        return page;
    }

    @Override
    public boolean deleteComment(Integer cid) {
        return blogCommentMapper.deleteById(cid) > 0;
    }

    @Override
    public void replyRemind(BlogComment comment) {
        String content = "您的评论有回复了，点击查看";
    }

    @Transactional
    @Override
    public boolean saveComment(BlogComment comment) {
        comment.setCreateTime(new Date());

        Integer parentComment = comment.getParentCommentId();
        if (parentComment.equals(-1)) {
            comment.setParentCommentId(null);

        } else {
            BlogComment comment1 = blogCommentMapper.selectById(comment.getParentCommentId());
            if (comment1.isRemind()) {
                replyRemind(comment1);
            }
        }
        Integer replyComment = comment.getReplyCommentId();
        if (replyComment.equals(-1)) {
            comment.setReplyCommentId(null);
        }
        return blogCommentMapper.insert(comment) > 0;
    }


}
