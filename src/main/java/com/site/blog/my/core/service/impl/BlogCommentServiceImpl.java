package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.site.blog.my.core.dao.BlogCommentMapper;
import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.entity.BlogMsg;
import com.site.blog.my.core.service.BlogCommentService;
import com.site.blog.my.core.service.BlogMsgService;
import com.site.blog.my.core.service.BlogService;
import com.site.blog.my.core.webSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private BlogCommentMapper blogCommentMapper;

    @Autowired
    @Lazy
    private BlogService blogService;

    @Autowired
    private BlogMsgService blogMsgService;

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
        Blog blog = blogService.getBlogById(comment.getBlogId());
        BlogMsg blogMsg = new BlogMsg();
        blogMsg.setBlogId(comment.getBlogId());
        blogMsg.setCId(comment.getUid());
        blogMsg.setUId(blog.getAdminUser().getAdminUserId());
        blogMsg.setMsg(comment.getContent());
        comment.setCreateTime(new Date());

        Integer parentComment = comment.getParentCommentId();
        if (parentComment.equals(-1)) {
            blogMsg.setTitle("评论了你的文章");
            blogMsg.setUId(blog.getAdminUser().getAdminUserId());
            comment.setParentCommentId(null);

        } else {
            BlogComment comment1 = blogCommentMapper.selectById(comment.getParentCommentId());
            if (comment1.isRemind()) {
                replyRemind(comment1);
            }
            blogMsg.setUId(comment1.getUid());
            blogMsg.setTitle("回复了你的评论");
            
        }
        Integer replyComment = comment.getReplyCommentId();
        if (replyComment.equals(-1)) {
            comment.setReplyCommentId(null);
        }
        boolean b = blogCommentMapper.insert(comment) > 0;
        if (b) {
            try {
                blogMsgService.save(blogMsg);

                WebSocketServer.sendInfo("true", "xiazhi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


}
