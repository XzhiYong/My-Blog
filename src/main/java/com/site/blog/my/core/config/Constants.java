package com.site.blog.my.core.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.site.blog.my.core.oauth2.entity.OAuth2QQ;
import com.site.blog.my.core.oauth2.entity.OAuth2WeiBo;
import com.site.blog.my.core.oauth2.properties.OAuth2Properties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 13 on 2019/5/24.
 */
@Component
public class Constants implements InitializingBean {
    @Autowired
    private OAuth2Properties oAuth2Properties;

    public static final String STICK = "stick";
    public static final String STATUS = "status";
    public static final String DELETE = "delete";

    public static final String AUTO_LOGIN_TAG_PREFIX = "^$";
    public static final String AUTO_LOGIN_TAG = AUTO_LOGIN_TAG_PREFIX + "isEncode";
    public static final Integer GENDER_MAN = 1;
    public static final Integer GENDER_GIRL = 2;
    public static final Integer GENDER_WOMAN = -1;
    public static final String WEIBO_ID = "weibo_id";
    public static final String QQ_ID = "qq_id";

    /** 微博 */
    public static String WEIBO_OAUTH2;
    public static String WEIBO_REDIRECT_URI;
    public static final String WEIBO_BIND_CODE = "bindCode:weibo:";
    public static final String WEIBO_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";
    public static final String WEIBO_USER_SHOW = "https://api.weibo.com/2/users/show.json";
    public static final String WEIBO_LOGOUT = "https://api.weibo.com/oauth2/revokeoauth2";
    public static final String WEIBO_OAUTH2_URL_TEM = "https://api.weibo.com/oauth2/authorize?client_id={}&redirect_uri={}";
    public static final String WEIBO_OAUTH2_URL_BIND_TEM = "https://api.weibo.com/oauth2/authorize?client_id={}&state={}&redirect_uri={}";

    /** QQ */
    public static String QQ_OAUTH2;
    public static String QQ_REDIRECT_URI;
    public static final String QQ_BIND_CODE = "bindCode:qq:";
    public static final String QQ_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    public static final String QQ_USER_OPEN_ID = "https://graph.qq.com/oauth2.0/me";
    public static final String QQ_USER_SHOW = "https://graph.qq.com/user/get_user_info";
    public static final String QQ_OAUTH2_URL_TEM = "https://graph.qq.com/oauth2.0/authorize?client_id={}&state={}&response_type=code&redirect_uri={}";


    public final static String FILE_UPLOAD_DIC = "/opt/deploy/upload/";//上传文件的默认url前缀，根据部署设置自行修改
    public static final String MUSIC_URL = "http://music.163.com/song/media/outer/url?id={}.mp3";
    public static final String DETAIL_ID = "2965698531";

    public static final String MUSIC_USER_KEY = "__csrf={};MUSIC_U={}";


    /**
     * 当私有成员被赋值后，此方法自动被调用，从而初始化常量
     */
    @Override
    public void afterPropertiesSet() {
        OAuth2QQ qq = oAuth2Properties.getQq();
        OAuth2WeiBo weibo = oAuth2Properties.getWeibo();

        //微博请求地址初始化
        WEIBO_REDIRECT_URI = URLUtil.encode(weibo.getRedirectUri());
        WEIBO_OAUTH2 = StrUtil.format(WEIBO_OAUTH2_URL_TEM, weibo.getClientId(), WEIBO_REDIRECT_URI);
        //QQ请求地址初始化
        QQ_REDIRECT_URI = URLUtil.encode(qq.getRedirectUri());
        QQ_OAUTH2 = StrUtil.format(QQ_OAUTH2_URL_TEM, qq.getClientId(), "login", QQ_REDIRECT_URI);
    }
}
