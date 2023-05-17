package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link http://13blog.site
 */
@Controller
@RequestMapping("/admin")
public class UploadController extends BaseController {

    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        return ResultGenerator.genSuccessResult(qiniuUtils.upload(file, "blog"));
    }

    @PostMapping({"/upload/file/cover"})
    @ResponseBody
    public Boolean uploadCover(@RequestParam("file") MultipartFile file) {
        String url = qiniuUtils.upload(file, "blog");
        return blogCoverService.saveCover(url);
    }

}
