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
    public String face(){
        return "face";
    }
    @RequestMapping("detectBrefore")
    public String detectBrefore(){
        return "face-detect";
    }
    @RequestMapping("addUserBefore")
    public String addUserBefore(){
        return "face-add-user";
    }
    @RequestMapping("searchBefore")
    public String searchBefore(){
        return "face-search";
    }

    /**
     *  人脸检测
     * @param image
     * @return
     */
    @RequestMapping("detect")
    @ResponseBody
    public String detect(String image){
        image = FaceUtil.handleFrontImage(image);
        if(image != null){
            return FaceUtil.detect(image,FaceUtil.IMAGE_TYPE_BASE64).toString();
        }
        return "fail！";
    }

    /**
     * 人脸注册
     * @param image
     * @return
     */
    @RequestMapping("addUser")
    @ResponseBody
    public String addUser(String image){
        image = FaceUtil.handleFrontImage(image);
        if(image != null){
            return FaceUtil.addUser(image,FaceUtil.IMAGE_TYPE_BASE64,"group1","user2").toString();
        }
        return "fail！";
    }

    /**
     * 人脸搜索
     * @param image
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public String search(String image){
        image = FaceUtil.handleFrontImage(image);
        if(image != null){
            return FaceUtil.search(image,FaceUtil.IMAGE_TYPE_BASE64,"group1","user1").toString();
        }
        return "fail！";
    }
}
