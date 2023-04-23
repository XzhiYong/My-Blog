package com.site.blog.my.core.auth;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Shiro自定义auth过滤器
 *
 * @Author 大誌
 * @Date 2019/3/31 10:38
 * @Version 1.0
 */
@Slf4j
@Component
public class AuthFilter extends AuthenticatingFilter {


    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 生成自定义token
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = (String) ((HttpServletRequest) request).getSession().getAttribute("loginUserName");
        return new AuthToken(token);
    }

    /**
     * 步骤1.所有请求全部拒绝访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        boolean equals = ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
        log.info("请求路径：" + ((HttpServletRequest) request).getRequestURI() + "--->" + (equals ? "放行" : "拦截"));
        return equals;
    }

    /**
     * 步骤2，拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回
        String token = (String) ((HttpServletRequest) request).getSession().getAttribute("loginUserName");
        String origin = ((HttpServletRequest) request).getHeader("Origin");
        if (StrUtil.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setCharacterEncoding("UTF-8");
            ((HttpServletRequest) request).getSession().setAttribute("errorMsg", "请先登录!");
            ((HttpServletResponse) response).sendRedirect("/admin/login");
            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * token失效时候调用
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = ((HttpServletRequest) request).getHeader("Origin");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            //处理登录失败的异常
            ((HttpServletRequest) request).getSession().setAttribute("errorMsg", "身份令牌时效，请重新登录!");
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/admin/login");
        } catch (IOException e1) {
            e.printStackTrace();
        }
        return false;
    }

}
