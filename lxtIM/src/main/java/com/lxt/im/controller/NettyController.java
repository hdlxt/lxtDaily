package com.lxt.im.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: NettyController
 * @Author: Administrator
 * @Description:
 * @Date: 2019/10/24 23:11
 * @Version: 1.0
 */
@Controller
public class NettyController {



    @GetMapping
    public String index(){
        return "index";
    }


}
