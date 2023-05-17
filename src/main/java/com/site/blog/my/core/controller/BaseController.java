package com.site.blog.my.core.controller;

import com.site.blog.my.core.controller.blog.MyBlogController;
import com.site.blog.my.core.dao.RolePermissionMapper;
import com.site.blog.my.core.dao.SysUserRoleMapper;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.util.QiniuUtils;
import com.site.blog.my.core.util.SendSmsUtil;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author 夏志勇
 * @since 2023年05月17日 16:13
 */
@Controller
public class BaseController {

    public static String theme = "amaze";

    @Resource
    protected BlogService blogService;

    @Resource
    protected TagService tagService;

    @Resource
    protected LinkService linkService;

    @Resource
    protected ConfigService configService;

    @Resource
    protected CategoryService categoryService;

    @Resource
    protected BlogCommentService blogCommentService;

    @Resource
    protected AdminUserService adminUserService;

    @Resource
    protected RoleService roleService;

    @Resource
    protected SysUserRoleMapper sysUserRoleMapper;

    @Resource
    protected BlogCoverService blogCoverService;

    @Resource
    protected PermissionService permissionService;

    @Resource
    protected RolePermissionMapper permissionMapper;

    @Resource
    protected SendSmsUtil sendSmsUtil;

    @Resource
    protected QiniuUtils qiniuUtils;

    @Resource
    protected MyBlogController myBlogController;
}
