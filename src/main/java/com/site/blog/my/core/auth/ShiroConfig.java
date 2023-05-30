package com.site.blog.my.core.auth;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author xiazy
 * @Date 2019/3/30 21:50
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(authRealm);
        //配置记住我 
        securityManager.setRememberMeManager(null);
        return securityManager;
    }


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //自定义返回对象
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilter.setSecurityManager(securityManager);

        /*
         * auth 拦截
         * anon 放行
         */
        Map<String, Filter> filters = new HashMap<>();
        //拦截所有AuthFilter下的路径
        filters.put("auth", new AuthFilter());
        shiroFilter.setFilters(filters);
        //自定义拦截路径
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/blogs/**", "anon");
        filterMap.put("/blog/**", "anon");
        filterMap.put("/categories/**", "anon");
        filterMap.put("/comments/**", "anon");
        filterMap.put("/tags/**", "anon");
        filterMap.put("/tag/**", "anon");
        filterMap.put("/search/**", "anon");
        filterMap.put("/upload/file/**", "anon");
        filterMap.put("/admin/user/password/**", "anon");
        filterMap.put("/admin/login", "anon");
        filterMap.put("/admin/register/**", "anon");
        filterMap.put("/admin/index", "anon");
        filterMap.put("/admin/roles/**", "anon");
        filterMap.put("/common/kaptcha", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/admin/dist/**", "anon");
        filterMap.put("/templates/**", "anon");
        filterMap.put("/app/qq", "anon");
        filterMap.put("/admin/plugins/**", "anon");
        filterMap.put("/sign/in", "anon");
        filterMap.put("/**", "auth");
        filterMap.put("/*", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
