package com.site.blog.my.core.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Component
public class QiniuUtils {

    @Value("${qiniu.url}")
    private String url;

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private String accessSecretKey;

    public String upload(MultipartFile file,String prefix) {
        int dotPos = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();

        String fileName = IdUtil.randomUUID().replaceAll("-", "") + "." + fileExt;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String bucket = "xiazy-blog";
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes,prefix+ fileName, upToken);
            //解析上传成功的结果
            System.out.println(url + "/" + JSONUtil.parseObj(response.bodyString()).getStr("key"));
            if (response.isOK() && response.isJson()) {
                // 返回这张存储照片的地址
                return url + "/" + JSONUtil.parseObj(response.bodyString()).getStr("key");
            } else {
                log.error("七牛异常:" + response.bodyString());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
