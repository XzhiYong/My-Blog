package com.site.blog.my.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.Sign;
import com.site.blog.my.core.entity.SignLog;
import com.site.blog.my.core.mapper.SignMapper;
import com.site.blog.my.core.service.AdminUserService;
import com.site.blog.my.core.service.SignLogService;
import com.site.blog.my.core.service.SignService;
import com.site.blog.my.core.util.IpRegionUtil;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import com.site.blog.my.core.util.SignUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-25
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

    @Autowired
    private SignLogService signLogService;

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public Result saveSignIn(HttpServletRequest request) {

        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();

        if (user == null) {
            return ResultGenerator.genFailResult("你不登录签到的积分算谁的？");

        }

        String clientIp = ServletUtil.getClientIP(request);
        String address = IpRegionUtil.searchByBaiDu(clientIp);

        DateTime now = DateTime.now();
        //先判断今天有没有签到
        List<SignLog> signLogs = signLogService.list(new LambdaQueryWrapper<SignLog>()
            .ge(SignLog::getCreateTime, DateUtil.formatDateTime(DateUtil.beginOfDay((now))))
            .le(SignLog::getCreateTime, DateUtil.formatDateTime(DateUtil.endOfDay((now))))
            .eq(SignLog::getUserId, user.getAdminUserId())
        );
        if (CollUtil.isNotEmpty(signLogs)) {
            ResultGenerator.genFailResult("今天已经签过到了，明天再来吧!");
        }
        //在判断昨天有没有签到
        List<SignLog> signLogList = signLogService.list(new LambdaQueryWrapper<SignLog>()
            .ge(SignLog::getCreateTime, DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.offsetDay(now, -1))))
            .le(SignLog::getCreateTime, DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.offsetDay(now, -1))))
            .eq(SignLog::getUserId, user.getAdminUserId())
        );
        Sign sign = this.getOne(new LambdaQueryWrapper<Sign>().eq(Sign::getUserId, user.getAdminUserId()));
        //代表昨天签到
        if (CollUtil.isNotEmpty(signLogList)) {
            sign.setDays(sign.getDays() + 1);
        } else {
            if (sign == null) {
                sign = new Sign();
            }

            JSONObject info = new JSONObject();
            info.set("ip", clientIp);
            info.set("ipAddress", address);
            sign.setInfo(info.toString());
            sign.setUserId(user.getAdminUserId());
            sign.setDays(1);
        }
        saveOrUpdate(sign);

        SignLog signLog = new SignLog();
        signLog.setSignId(sign.getId());
        signLog.setUserId(user.getAdminUserId());
        signLog.setIp(clientIp);
        signLog.setIpAddress(address);
        // 从SignRule中获取要加的积分
        Integer point = SignUtil.calculatedReward(sign.getDays());
        signLog.setPoint(point);
        signLogService.save(signLog);

        AdminUser adminUserServiceById = adminUserService.getById(user.getAdminUserId());
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminUserId(user.getAdminUserId());
        adminUser.setPoint((adminUserServiceById.getPoint() == null ? 0 : adminUserServiceById.getPoint()) + point);
        adminUserService.updateById(adminUser);

        return ResultGenerator.genSuccessResult();
    }

    @Override
    public PageInfo<SignLog> signInLog(Map<String, Object> param) {
        PageHelper.startPage(MapUtil.getInt(param, "page", 1), MapUtil.getInt(param, "limit", 10));
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        List<SignLog> list = signLogService.list(new LambdaQueryWrapper<SignLog>().eq(SignLog::getUserId, user.getAdminUserId()).orderByDesc(SignLog::getCreateTime));
        return new PageInfo<>(list);
    }
}
