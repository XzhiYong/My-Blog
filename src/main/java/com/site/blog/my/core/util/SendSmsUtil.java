package com.site.blog.my.core.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.site.blog.my.core.mapper.SmsMsgMapper;
import com.site.blog.my.core.entity.SmsMsg;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 夏志勇
 * @since 2023年05月04日 14:27
 */
@Slf4j
@Component
public class SendSmsUtil {

    @Value("${tengxunyun.sms.accessKey}")
    private String accessKey;

    @Value("${tengxunyun.sms.accessSecretKey}")
    private String accessSecretKey;

    @Autowired
    private SmsMsgMapper smsMsgMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean verificationCode(String mobile, String key) {
        SmsMsg smsMsg = new SmsMsg();

        try {
            Credential cred = new Credential(DesUtil.decrypt(accessKey), DesUtil.decrypt(accessSecretKey));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppid("1400818996");
            req.setSign("xiazyBlog");
            req.setTemplateID("1788644");

            int[] ints = NumberUtil.generateRandomNumber(0, 9, 4);
            StringBuilder stringBuilder = new StringBuilder();
            for (int anInt : ints) {
                stringBuilder.append(anInt);
            }
            String[] templateParamSet = {stringBuilder.toString()};
            req.setTemplateParamSet(templateParamSet);
            String[] phoneNumberSet = {"+86" + mobile};
            req.setPhoneNumberSet(phoneNumberSet);

            /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String sessionContext = "";
            req.setSessionContext(sessionContext);

            /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
            String extendCode = "";
            req.setExtendCode(extendCode);

            /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/
            String senderid = "";
            req.setSenderId(senderid);
            SendSmsResponse res = client.SendSms(req);
            SendStatus[] sendStatusSet = res.getSendStatusSet();
            if (sendStatusSet != null && sendStatusSet[0].getCode().equals("Ok")) {
                BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key + ":" + mobile);
                boundValueOperations.set(stringBuilder.toString(), 2, TimeUnit.MINUTES);
                smsMsg.setResult("ok");

            } else {
                smsMsg.setResult("on");
            }
            smsMsg.setMsg(JSONUtil.toJsonStr(res));
        } catch (Exception e) {
            smsMsg.setMsg(e.getMessage());
            smsMsg.setResult("on");
        }
        smsMsg.setMobile(mobile);
        smsMsgMapper.insert(smsMsg);
        return "ok".equals(smsMsg.getResult());

    }

}
