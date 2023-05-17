package com.site.blog.my.core.controller.admin;

import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 夏志勇
 * @since 2023年05月04日 10:47
 */
@Controller
@RequestMapping("/admin/register")
public class RegisterController extends BaseController {

    @GetMapping
    public String index() {
        return "admin/register";
    }

    @ResponseBody
    @GetMapping("/verificationCode")
    public Boolean verificationCode(@RequestParam String mobile) {
        return sendSmsUtil.verificationCode(mobile, "register");
    }

    @ResponseBody
    @PostMapping
    public Result register(@RequestBody AdminUser adminUser,
                           HttpSession session) {
        Result register = adminUserService.register(adminUser, session);
        if (register.getResultCode() == 200) {
           try {
               adminUserService.login(adminUser.getLoginUserName(), (String) register.getData());
           }catch (Exception e){
               e.printStackTrace();
               register.setMessage("自动登录失败，请手动登录！");
               register.setResultCode(500);
               return register;  
           }
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            session.setAttribute("loginUserName", adminUser.getLoginUserName());
            session.setAttribute("user", adminUser);
        }
        return register;
    }

}
