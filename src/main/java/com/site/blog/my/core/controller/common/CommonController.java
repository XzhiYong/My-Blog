package com.site.blog.my.core.controller.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class CommonController {

    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");

        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 30, 4, 2);

        // 验证码存入session
        httpServletRequest.getSession().setAttribute("verifyCode", shearCaptcha);
        log.info("验证码:" + shearCaptcha.getCode());

        // 输出图片流
        shearCaptcha.write(httpServletResponse.getOutputStream());
    }
}
