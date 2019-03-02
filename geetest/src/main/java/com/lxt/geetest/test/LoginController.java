package com.lxt.geetest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("login")
@Controller
public class LoginController {
    @Autowired
    private HttpSession httpSession;
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
    /**
     *
     * @param model
     * @param geetest_challenge
     * @param geetest_validate
     * @param geetest_seccode
     * @param username1
     * @param password1
     * @return
     */
    @RequestMapping("/loginForm")
    public String loginForm(Model model,String geetest_challenge, String geetest_validate, String geetest_seccode
            ,String username1, String password1){
        if(!GeeTestUtil.validate(httpSession,geetest_challenge,geetest_validate,geetest_seccode)){
            model.addAttribute("result","验证失败!!!");
            return "result";
        }
        if("admin1".equals(username1) && "admin1".equals(password1)){
            model.addAttribute("result","登录成功!!!");
        }else{
            model.addAttribute("result","登录失败!!!");
        }
        return "result";
    }

    /**
     * 模拟AJAX登录
     * @param model
     * @param geetest_challenge
     * @param geetest_validate
     * @param geetest_seccode
     * @param username2
     * @param password2
     * @return
     */
    @ResponseBody
    @RequestMapping("/loginAJAX")
    public String loginAJAX(String geetest_challenge, String geetest_validate, String geetest_seccode
            ,String username2, String password2){
        if(!GeeTestUtil.validate(httpSession,geetest_challenge,geetest_validate,geetest_seccode)){
            return "验证失败!!!";
        }
        if("admin2".equals(username2) && "admin2".equals(password2)){
            return "登录成功!!!";
        }else{
            return "登录失败!!!";
        }
    }
}
