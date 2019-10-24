package com.lxt.geetest.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class IndexController {
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

}
