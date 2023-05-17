package com.site.blog.my.core.controller.blog;


import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.BlogComment;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/comment/postcomment")
    public String postcomment(HttpServletRequest request, BlogComment comment) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user==null) {
            request.setAttribute("errorMsg", "请先登录");
            return "admin/login";
        }
        comment.setUid(user.getAdminUserId());
        blogCommentService.saveComment(comment);
        return myBlogController.getDetail(request, comment.getBlogId(), null);
    }

    @RequestMapping("/comment/commentlist")
    public String commentlist(HttpServletRequest request, @RequestParam("blogId") Long blogId, Integer page) {

        return myBlogController.getDetail(request, blogId, page);
    }

    @RequestMapping("/comment/delete")
    public String deleteComment(HttpServletRequest request, @RequestParam("blogId") Long blogId, Integer cid) {
        blogCommentService.deleteComment(cid);
        return myBlogController.getDetail(request, blogId, null);
    }

    @RequestMapping({"/comment"})
    public String getcomment(HttpServletRequest request, @RequestParam("blogId") Long blogId) {
        return myBlogController.getDetail(request, blogId, null);
    }

}
