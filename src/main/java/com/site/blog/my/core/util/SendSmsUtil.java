package com.site.blog.my.core.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.site.blog.my.core.dao.SmsMsgMapper;
import com.site.blog.my.core.entity.SmsMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 夏志勇
 * @since 2023年05月04日 14:27
 */
@Component
public class SendSmsUtil {

    @Value("${aliyun.sms.accessKey}")
    private String accessKey;

    @Value("${aliyun.sms.accessSecretKey}")
    private String accessSecretKey;

    @Autowired
    private SmsMsgMapper smsMsgMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean verificationCode(String mobile) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", DesUtil.decrypt(accessKey),
            DesUtil.decrypt(accessSecretKey));
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("夏志勇的博客");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_460650484");
        int[] ints = NumberUtil.generateRandomNumber(0, 9, 4);
        StringBuilder stringBuilder = new StringBuilder();
        for (int anInt : ints) {
            stringBuilder.append(anInt);
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", stringBuilder.toString());

        request.setTemplateParam(JSONUtil.toJsonStr(map));
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        SmsMsg smsMsg = new SmsMsg();
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            redisTemplate.opsForValue().set(mobile, stringBuilder.toString(),120, TimeUnit.SECONDS);
            smsMsg.setResult("OK");
            //请求成功
            return true;
        }
        smsMsg.setResult("ON");
        smsMsg.setMsg(sendSmsResponse.getMessage());
        smsMsg.setMobile(mobile);
        smsMsgMapper.insert(smsMsg);
        return false;
    }
}
