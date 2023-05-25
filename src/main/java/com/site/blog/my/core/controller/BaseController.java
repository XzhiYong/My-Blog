package com.site.blog.my.core.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.Sign;
import com.site.blog.my.core.entity.SignLog;
import com.site.blog.my.core.entity.SignVo;
import com.site.blog.my.core.mapper.RolePermissionMapper;
import com.site.blog.my.core.mapper.SysUserRoleMapper;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.util.QiniuUtils;
import com.site.blog.my.core.util.SendSmsUtil;
import com.site.blog.my.core.util.SignUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    protected RolePermissionMapper rolePermissionMapper;

    @Resource
    protected SendSmsUtil sendSmsUtil;

    @Resource
    protected QiniuUtils qiniuUtils;

    @Resource
    protected BlogMsgService blogMsgService;

    @Resource
    protected SignLogService signLogService;

    @Resource
    protected SignService signService;

    protected void getSign(HttpServletRequest request, AdminUser user) {
        if (user == null) {
            user = (AdminUser) SecurityUtils.getSubject().getPrincipal();

        }
        SignVo signVo = new SignVo();
        if (user == null) {
            signVo.setDay(0);
            signVo.setCount(SignUtil.calculatedReward(1));
            signVo.setIsIn(false);
            request.setAttribute("sign", signVo);
        } else {
            DateTime now = DateTime.now();
            DateTime beginOfDay = DateUtil.beginOfDay(now);
            DateTime endOfDay = DateUtil.endOfDay(now);

            long count = signLogService.count(
                new QueryWrapper<SignLog>()
                    .eq("user_id", user.getAdminUserId())
                    .ge("create_time", beginOfDay)
                    .le("create_time", endOfDay)
            );

            Sign sign = signService.getOne(
                new QueryWrapper<Sign>()
                    .eq("user_id", user.getAdminUserId())
            );

            if (sign == null) {
                signVo.setDay(0);
                signVo.setCount(SignUtil.calculatedReward(1));
            } else {
                signVo.setDay(sign.getDays());
                signVo.setCount(SignUtil.calculatedReward(sign.getDays()));
            }

            signVo.setIsIn(count == 1);
            request.setAttribute("sign", signVo);

        }
    }


}
