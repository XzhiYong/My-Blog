package com.site.blog.my.core.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.blog.my.core.auth.AuthToken;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.controller.vo.QqUserInfoVo;
import com.site.blog.my.core.controller.vo.WeiBoUserInfoVo;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.UserSocial;
import com.site.blog.my.core.mapper.UserSocialMapper;
import com.site.blog.my.core.oauth2.properties.OAuth2Properties;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.UserSocialService;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.site.blog.my.core.config.Constants.AUTO_LOGIN_TAG;

/**
 * <p>
 * 用户与第三方账户绑定表 服务实现类
 * </p>
 *
 * @author codecrab
 * @since 2021-08-19
 */
@Slf4j
@Service
public class UserSocialServiceImpl extends ServiceImpl<UserSocialMapper, UserSocial> implements UserSocialService {

    @Resource
    @Lazy
    private AdminUserService adminUserService;

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserSocial getBySocialUidAndSocialName(String socialUid, String socialType) {
        return baseMapper.selectOne(
            new QueryWrapper<UserSocial>()
                .eq("social_uid", socialUid)
                .eq("social_type", socialType)
        );
    }

    @Override
    public Boolean getByUserId(String userId, String socialName) {
        return baseMapper.selectOne(
            new QueryWrapper<UserSocial>()
                .eq("user_id", userId)
                .eq("social_type", socialName)
        ) != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthToken socialLogin(UserSocial userSocial) {
        UserSocial social = this.getBySocialUidAndSocialName(userSocial.getSocialUid(), userSocial.getSocialType());
        if (social != null) {
            //更新第三方用户验证令牌
            social.setAccessToken(userSocial.getAccessToken());
            social.setRefreshToken(userSocial.getRefreshToken());
            social.setExpiresIn(userSocial.getExpiresIn());
            baseMapper.updateById(social);
            AdminUser user = adminUserService.getUserDetailById(social.getUserId());
            //如果username带了isEncode，那么登录判断时就不需要加密密码比较
            String username = user.getLoginUserName();
            return new AuthToken(username);
        }

        //新增一个用户
        AdminUser user = new AdminUser();
        this.packageUserByRemote(userSocial, user);

        Date now = new Date();
        //获取一个随机密码
        String pass = RandomUtil.randomStringUpper(8);
        //给密码加盐加密
        String password = SecureUtil.md5(pass);
        user.setLoginPassword(password);
        user.setEmail("请尽快绑定邮箱");
        user.setLocked(0);
        user.setCreateTime(now);
        user.setPoint(0);
        user.setLastLoginTime(now);
        user.setIp("0.0.0.0");
        //执行保存用户
        boolean saveUserCount = adminUserService.save(user);
        //保存用户与社交账户绑定信息
        userSocial.setUserId(user.getAdminUserId());
        int saveUserSocialCount = baseMapper.insert(userSocial);

        if (saveUserCount && saveUserSocialCount > 0) {
            //向用户发送系统消息

        }
        return new AuthToken(user.getLoginUserName());
    }

    @Override
    public void socialBind(UserSocial userSocial, Integer userId) {
        UserSocial social = this.getBySocialUidAndSocialName(userSocial.getSocialUid(), userSocial.getSocialType());
        Assert.isNull(social, "该社交账号已被绑定，请先解绑后再试");

        AdminUser user = adminUserService.getUserDetailById(userId);
        Assert.notNull(user, "当前用户未找到，请重新登录后再试");

        //绑定第三方用户验证令牌
        userSocial.setUserId(userId);
        baseMapper.insert(userSocial);
        SecurityUtils.getSubject().logout();
        //如果username带了isEncode，那么登录判断时就不需要加密密码比较
        String username = user.getLoginUserName();

        SecurityUtils.getSubject().login(new AuthToken(username));
    }

    @Override
    public Result doUnBind(String type, Integer profileId) {
        QueryWrapper<UserSocial> queryWrapper = new QueryWrapper<UserSocial>()
            .eq("user_id", profileId)
            .eq("social_type", type);

        UserSocial social = baseMapper.selectOne(queryWrapper);

        Map<String, Object> params = MapUtil.newHashMap(1);
        params.put("access_token", social.getAccessToken());
        HttpRequest request;

        if (oAuth2Properties.getWeibo().getSocialType().equals(social.getSocialType())) {
            request = HttpRequest.post(Constants.WEIBO_LOGOUT);

            HttpResponse response = request.form(params).execute();
            if (!response.isOk()) {
                return ResultGenerator.genFailResult("请求失败！请重试！");
            }

            Map<String, Boolean> map = JSONUtil.toBean(response.body(), new TypeReference<HashMap<String, Boolean>>() {}, true);
            Boolean result = map.get("result");
            if (result == null || !result) {
                return ResultGenerator.genFailResult("取消授权失败！请稍后重试！");
            }

            log.info("ID为 {} 的用户解除了微博绑定", profileId);
        } else if (oAuth2Properties.getQq().getSocialType().equals(social.getSocialType())) {
            log.info("ID为 {} 的用户解除了QQ绑定", profileId);
        } else {
            throw new RuntimeException("解除授权未知的请求类型");
        }

        boolean isSuccess = baseMapper.delete(queryWrapper) > 0;
        AdminUser user = adminUserService.getUserDetailById(profileId);
        SecurityUtils.getSubject().logout();
        //如果username带了isEncode，那么登录判断时就不需要加密密码比较
        String username = user.getLoginUserName() + AUTO_LOGIN_TAG;
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, user.getLoginPassword()));
        return isSuccess ? ResultGenerator.genSuccessResult("解除绑定成功") : ResultGenerator.genFailResult("解除绑定失败，请稍后重试");
    }

    private void packageUserByRemote(UserSocial userSocial, AdminUser user) {
        try {
            if (oAuth2Properties.getWeibo().getSocialType().equalsIgnoreCase(userSocial.getSocialType())) {
                //封装请求参数
                Map<String, Object> params = MapUtil.newHashMap(2);
                params.put("uid", userSocial.getSocialUid());
                params.put("access_token", userSocial.getAccessToken());
                //调用微博api查询用户信息信息
                HttpResponse response = HttpRequest.get(Constants.WEIBO_USER_SHOW).form(params).execute();
                if (response.isOk()) {
                    WeiBoUserInfoVo infoVo = objectMapper.readValue(response.body(), WeiBoUserInfoVo.class);
                    String username = this.checkUsername(userSocial, infoVo.getScreenName());
                    user.setNickName(username);
                    user.setHeadPortrait(infoVo.getAvatarHd());
                    user.setLoginUserName(infoVo.getId().toString());
                    if ("m".equals(infoVo.getGender())) {
                        user.setSex(Constants.GENDER_MAN);
                    } else if ("f".equals(infoVo.getGender())) {
                        user.setSex(Constants.GENDER_GIRL);
                    } else {
                        user.setSex(Constants.GENDER_WOMAN);
                    }
                    user.setRemark(infoVo.getDescription());
                } else {
                    log.error("查询用户信息失败 -> {} \n请求参数：-> {}", response, userSocial);
                    throw new RuntimeException("微博登录获取用户信息失败");
                }
            } else if (oAuth2Properties.getQq().getSocialType().equalsIgnoreCase(userSocial.getSocialType())) {
                //封装请求参数
                Map<String, Object> params = MapUtil.newHashMap(3);
                params.put("openid", userSocial.getSocialUid());
                params.put("access_token", userSocial.getAccessToken());
                params.put("oauth_consumer_key", oAuth2Properties.getQq().getClientId());
                //调用 QQ api查询用户信息信息
                HttpResponse response = HttpRequest.get(Constants.QQ_USER_SHOW).form(params).execute();
                if (response.isOk()) {
                    QqUserInfoVo infoVo = objectMapper.readValue(response.body(), QqUserInfoVo.class);
                    String username = this.checkUsername(userSocial, infoVo.getNickname());
                    user.setNickName(username);
                    user.setHeadPortrait(StrUtil.blankToDefault(infoVo.getFigureUrlQq2(), infoVo.getFigureUrlQq1()));
                    user.setSex("男".equals(infoVo.getGender()) ? Constants.GENDER_MAN : Constants.GENDER_WOMAN);
                } else {
                    log.error("查询用户信息失败 -> {} \n请求参数：-> {}", response, userSocial);
                    throw new RuntimeException("QQ登录获取用户信息失败");
                }
            } else {
                throw new RuntimeException("未知的请求类型");
            }
        } catch (JsonProcessingException e) {
            log.error("获取的请求内容反序列化失败，第三方用户信息为：" + userSocial, e);
        }
    }

    private String checkUsername(UserSocial userSocial, String nickname) {
        String username = nickname;
        //判断用户名是否为空
        if (StrUtil.isBlank(username)) {
            username = RandomUtil.randomString(8);
        }

        //判断用户名是否被占用
        Long usernameCount = adminUserService.count(
            new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getLoginUserName, username)
        );

        if (usernameCount > 0) {
            username = username + "#" + userSocial.getSocialUid();
        }

        return username;
    }
}
