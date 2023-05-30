package com.site.blog.my.core.oauth2.properties;

import com.site.blog.my.core.oauth2.entity.OAuth2Bean;
import com.site.blog.my.core.oauth2.entity.OAuth2QQ;
import com.site.blog.my.core.oauth2.entity.OAuth2WeiBo;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author codecrab
 * @since 2021年06月22日 10:45
 */
@Data
@Component("oAuth2Properties")
@EnableConfigurationProperties({OAuth2QQ.class, OAuth2WeiBo.class})
@ConfigurationProperties(prefix = "eblog.oauth2")
public class OAuth2Properties implements InitializingBean {

    public static OAuth2Bean WEIBO;
    public static OAuth2QQ QQ;

    private OAuth2WeiBo weibo;

    private OAuth2QQ qq;

    @Override
    public void afterPropertiesSet() throws Exception {
        WEIBO = weibo;
        QQ = qq;
    }
}
