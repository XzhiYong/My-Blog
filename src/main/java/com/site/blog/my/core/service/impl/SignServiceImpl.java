package com.site.blog.my.core.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

        Sign sign = this.getOne(
            new QueryWrapper<Sign>()
                .eq("user_id", user.getAdminUserId())
        );

        String clientIp = ServletUtil.getClientIP(request);
        String address = IpRegionUtil.searchByBaiDu(clientIp);

        Sign newSign = new Sign();
        if (sign != null) {
            String day = DateUtil.format(sign.getCreateTime(), "yyyy-MM-dd");
            // 获取的签到日期不能是当天的
            if (DateTime.now().toString("yyyy-MM-dd").equals(day)) {
                return ResultGenerator.genFailResult("今天已经签到过了!明天再来吧");
            }


            DateTime date = DateTime.of(day, "yyyy-MM-dd");
            long betweenDay = DateUtil.betweenDay(DateTime.now(), date, true);
            if (betweenDay > 1) {
                newSign.setDays(1);
            } else {
                newSign.setDays(sign.getDays() + 1);
            }

            newSign.setId(sign.getId());
        } else {
            newSign.setDays(1);
            newSign.setUserId(user.getAdminUserId());
        }

        JSONObject info = new JSONObject();
        info.set("ip", clientIp);
        info.set("ipAddress", address);
        newSign.setInfo(info.toString());

        saveOrUpdate(newSign);

        SignLog signLog = new SignLog();
        signLog.setSignId(newSign.getId());
        signLog.setUserId(user.getAdminUserId());
        signLog.setIp(clientIp);
        signLog.setIpAddress(address);
        // 从SignRule中获取要加的积分
        Integer point = SignUtil.calculatedReward(newSign.getDays());
        signLog.setPoint(point);
        signLogService.save(signLog);

        AdminUser adminUserServiceById = adminUserService.getById(user.getAdminUserId());

        AdminUser adminUser = new AdminUser();
        adminUser.setAdminUserId(user.getAdminUserId());
        adminUser.setPoint((adminUserServiceById.getPoint() == null ? 0 : adminUserServiceById.getPoint()) + point);
        adminUserService.updateById(adminUser);

        return ResultGenerator.genSuccessResult();
    }
}
