package com.lxt.face.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class IndexController {

    /**
     *登录页面
     */
    @RequestMapping
    public String login(){
        return "login";
    }

    /**
     *
     * @param image
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public String upload(String image){
        image = FaceUtil.handleFrontImage(image);
        if(image != null){
//            return FaceUtil.detect(imageArr[1],IMAGE_TYPE_BASE64).toString();
            return FaceUtil.addUser(image,FaceUtil.IMAGE_TYPE_BASE64,"group1","user1").toString();
        }
        return "fail！";
    }

}
