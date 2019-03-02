package com.lxt.geetest.test;

import com.lxt.geetest.sdk.GeetestLib;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 极验验证相关
 */
@RequestMapping("geetTest")
@Controller
public class GeetTestController {
    /**
     * 注入session
     */
    @Autowired
    private HttpSession httpSession;
    /**
     * 验证1初始化
     * @return
     */
    @ResponseBody
    @GetMapping("register1")
    public String register1(){
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String resStr = "{}";
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        httpSession.setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        httpSession.setAttribute("userid", userid);
        resStr = gtSdk.getResponseStr();
        return resStr;
    }
    /**
     * 验证2 二次验证
     * @return
     */
    @ResponseBody
    @GetMapping("register2")
    public String register2(){
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String resStr = "{}";
        //自定义userid
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        httpSession.setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        httpSession.setAttribute("userid", userid);
        resStr = gtSdk.getResponseStr();
        return resStr;
    }

    /**
     *
     * @param geetest_challenge 极验验证二次验证表单数据 chllenge
     * @param geetest_validate 极验验证二次验证表单数据 validate
     * @param geetest_seccode 极验验证二次验证表单数据 seccode
     * @return
     */
    @ResponseBody
    @PostMapping("validate2")
    public Map<String,String> validate2(@RequestParam("geetest_challenge")String challenge
            ,@RequestParam("geetest_validate") String validate
            ,@RequestParam("geetest_seccode") String seccode
            ,String username2,String password2){
        Map<String,String> data = new HashMap<>(2);
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) httpSession.getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid = (String)httpSession.getAttribute("userid");
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        int gtResult = 0;
        boolean flag = false;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            if("admin2".equals(username2) && "admin2".equals(password2)){
                flag = true;
            }
            System.out.println("gtResult:"+gtResult+";flag:"+flag);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }
        if (gtResult == 1 && flag) {
            // 验证成功
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            // 验证失败
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
