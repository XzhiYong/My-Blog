package com.site.blog.my.core.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.auth.AuthToken;
import com.site.blog.my.core.dao.AdminUserMapper;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.SysRole;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.RoleService;
import com.site.blog.my.core.util.IpRegionUtil;
import com.site.blog.my.core.util.MD5Util;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public AdminUser login(String userName, String password) {
        //获取一个用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        AuthToken usernamePasswordToken = new AuthToken(userName);
        subject.login(usernamePasswordToken);
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");

        AdminUser user = adminUserMapper.login(userName);
        if (!user.getLoginPassword().equals(passwordMd5)) {
            throw new IncorrectCredentialsException();
        }
        return user;
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        AdminUser user = adminUserMapper.selectByPrimaryKey(loginUserId);
        if (StrUtil.isNotBlank(user.getIp())) {
            user.setLocation(IpRegionUtil.searchByBaiDu(user.getIp()));
        }
        return user;
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
                //设置新密码并修改
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                    //修改成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //修改信息
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminUser findByUsername(String username) {
        AdminUser byUsername = adminUserMapper.findByUsername(username);
        if (byUsername == null) {
            return null;
        }
        if (StrUtil.isNotBlank(byUsername.getIp())) {
            byUsername.setLocation(IpRegionUtil.searchByBaiDu(byUsername.getIp()));
        }
        List<SysRole> userIdByRole = roleService.getUserIdByRole(byUsername.getAdminUserId());
        byUsername.setSysRole(userIdByRole);
        return byUsername;
    }

    @Override
    public PageInfo<AdminUser> pageList(Map<String, Object> params) {
        PageHelper.startPage(MapUtil.getInt(params, "page", 1), MapUtil.getInt(params, "limit", 10));
        List<AdminUser> list = list();
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(AdminUser adminUser, HttpServletRequest request) {
        String loginUserName = adminUser.getLoginUserName();
        String mobile = adminUser.getMobile();
        String clientIP = ServletUtil.getClientIP(request);
        adminUser.setIp(clientIP);
        if (adminUserMapper.findByUsername(loginUserName) != null || adminUserMapper.findByUsername(mobile) != null
        ) {
            return ResultGenerator.genFailResult("账号或手机号已注册");
        }

        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("register:" + mobile);
        Object o = boundValueOperations.get();
        if (!Objects.equals(o, adminUser.getVerificationCode())) {
            return ResultGenerator.genFailResult("验证码不正确");
        }
        String oldPas = adminUser.getLoginPassword();
        adminUser.setLoginPassword(MD5Util.MD5Encode(adminUser.getLoginPassword(), "UTF-8"));
        adminUser.setLocked(0);
        save(adminUser);
        AdminUser username = adminUserMapper.findByUsername(loginUserName);
        //注册新用户 给予普通用户权限
        Map<String, Object> params = new HashMap<>();
        params.put("userId", username.getAdminUserId());
        List<String> roles = new ArrayList<>();
        roles.add("2");
        params.put("roleIds", roles);
        roleService.saveUserRole(params);
        return ResultGenerator.genSuccessResult(oldPas);
    }

    @Override
    public Result updateMobilePassword(AdminUser adminUser) {
        AdminUser user = adminUserMapper.findByUsername(adminUser.getMobile());
        if (user == null) {
            return ResultGenerator.genFailResult("未找到手机号");
        }
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("register:" + adminUser.getMobile());
        Object o = boundValueOperations.get();
        if (!Objects.equals(o, adminUser.getVerificationCode())) {
            return ResultGenerator.genFailResult("验证码不正确");
        }
        user.setLoginPassword(MD5Util.MD5Encode(adminUser.getLoginPassword(), "UTF-8"));
        updateById(user);
        return ResultGenerator.genSuccessResult(updateById(user));
    }
}
