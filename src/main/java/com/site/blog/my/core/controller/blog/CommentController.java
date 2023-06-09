package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jxj4869
 * @since 2020-04-12
 */
@Controller
@RequestMapping("/blog")
public class CommentController extends BaseController {

    @Autowired
    MyBlogController myBlogController;

    @ResponseBody
    @PostMapping("/comment/postcomment")
    public Result postcomment(HttpServletRequest request, @RequestBody BlogComment comment) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return ResultGenerator.genFailResult("未登录");
        }
        comment.setUid(user.getAdminUserId());
        blogCommentService.saveComment(comment);
        myBlogController.getDetail(request, comment.getBlogId(), null);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/comment/commentlist")
    public String commentlist(HttpServletRequest request, @RequestParam("blogId") Long blogId, Integer page) {
        return myBlogController.getDetail(request, blogId, page);
    }

    @ResponseBody
    @DeleteMapping("/comment/delete/{id}")
    public Result deleteComment(@PathVariable Integer id) {
        return ResultGenerator.genSuccessResult(blogCommentService.deleteComment(id));
    }

    @RequestMapping({"/comment"})
    public String getcomment(HttpServletRequest request, @RequestParam("blogId") Long blogId) {
        return myBlogController.getDetail(request, blogId, null);
    }

}
