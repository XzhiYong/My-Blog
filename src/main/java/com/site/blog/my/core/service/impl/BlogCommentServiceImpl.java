package com.site.blog.my.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.*;
import com.site.blog.my.core.mapper.BlogCommentMapper;
import com.site.blog.my.core.service.BlogCommentService;
import com.site.blog.my.core.service.BlogMsgService;
import com.site.blog.my.core.service.BlogService;
import com.site.blog.my.core.service.UserCommentService;
import com.site.blog.my.core.util.ShiroUtil;
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

    @Autowired
    private UserCommentService userCommentService;

    @Override
    public PageInfo<BlogComment> commentPage(Integer currentPage, Long blogId) {
        PageHelper.startPage(currentPage, 10);
        List<BlogComment> blogComments = blogCommentMapper.selectAllParentCommentNullPage(blogId);
        return new PageInfo<>(blogComments);
    }

    @Override
    public boolean deleteComment(Integer cid) {
        return blogCommentMapper.deleteById(cid) > 0;
    }

    @Override
    public void replyRemind(BlogComment comment) {
        String content = "您的评论有回复了，点击查看";
    }
    
    public void replyRemind(UserComment comment) {
        String content = "您的评论有回复了，点击查看";
    }

    @Transactional
    @Override
    public boolean saveComment(BlogComment comment) {
        BlogMsg blogMsg = new BlogMsg();
        if (comment.getType() == 1) {
            Blog blog = blogService.getBlogById(comment.getBlogId());

            blogMsg.setBlogId(comment.getBlogId());
            blogMsg.setCId(comment.getUid());
            blogMsg.setUId(blog.getAdminUser().getAdminUserId());
            blogMsg.setMsg(comment.getContent());

            Integer parentComment = comment.getParentCommentId();
            if (parentComment.equals(-1)) {
                blogMsg.setType(1);
                blogMsg.setUId(blog.getAdminUser().getAdminUserId());
                comment.setParentCommentId(null);

            } else {
                BlogComment comment1 = blogCommentMapper.selectById(comment.getParentCommentId());
                if (comment1.isRemind()) {
                    replyRemind(comment1);
                }
                blogMsg.setUId(comment1.getUid());
                blogMsg.setType(2);

            }
            Integer replyComment = comment.getReplyCommentId();
            if (replyComment.equals(-1)) {
                comment.setReplyCommentId(null);
            }
            blogCommentMapper.insert(comment);

        } else {
            AdminUser profile = ShiroUtil.getProfile();
            blogMsg.setUId(profile.getAdminUserId());
            Integer parentComment = comment.getParentCommentId();
            if (parentComment.equals(-1)) {
                blogMsg.setType(0);
                comment.setParentCommentId(null);
            } else {
                UserComment comment1 = userCommentService.getById(comment.getParentCommentId());
                if (comment1.isRemind()) {
                    replyRemind(comment1);
                }
                blogMsg.setUId(comment1.getUid());
                blogMsg.setType(0);

            }
            Integer replyComment = comment.getReplyCommentId();
            if (replyComment.equals(-1)) {
                comment.setReplyCommentId(null);
            }
            userCommentService.save(BeanUtil.copyProperties(comment, UserComment.class));
        }
        blogMsg.setCId(comment.getUid());
        blogMsg.setMsg(comment.getContent());
        comment.setCreateTime(new Date());
        try {
            blogMsgService.save(blogMsg);
            WebSocketServer.sendInfo("true", "xiazhi");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
