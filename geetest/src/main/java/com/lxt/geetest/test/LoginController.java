package com.lxt.geetest.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("login")
@Controller
public class LoginController {
    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping()
    public String login(){
        return "login";
    }

    /**
     * 模拟表单登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/loginForm")
    public String loginForm(Model model,String username1, String password1){
        if("admin1".equals(username1) && "admin1".equals(password1)){
            model.addAttribute("result","登录成功!!!");
        }else{
            model.addAttribute("result","登录失败!!!");
        }
        return "result";
    }
}
