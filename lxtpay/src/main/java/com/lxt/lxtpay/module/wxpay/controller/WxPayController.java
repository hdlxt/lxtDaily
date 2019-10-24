package com.lxt.lxtpay.module.wxpay.controller;

import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.module.wxpay.service.WxPayService;
import com.lxt.lxtpay.utill.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

/**
 * @ClassName: WxPayController
 * @Description: 微信支付
 * @Author: lxt
 * @Date: 2019-02-25 16:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("wxPay")
public class WxPayController {
    private final static Logger logger = LoggerFactory.getLogger(WxPayController.class);
    private final static String BASE_URL="pay/wxpay/";

    @Autowired
    private WxPayService wxPayService;

    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("orderId", DateUtil.format(null,DateUtil.PATTERN_yyyyMMddHHmmss));
        return "test/test-wxpay";
    }
    /**
     * @Title: payPC
     * @Description:  电脑网站支付 生成支付二维码
     * @Author: lxt
     * @param: model
     * @param: productModel
     * @Date: 2019-02-26 13:41
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws:
     */
    @RequestMapping(value = "/payPC",method = {RequestMethod.POST})
    @ResponseBody
    public ResultData<String> payPC(Model model, @RequestBody @Valid ProductModel productModel){
        return wxPayService.payPC(productModel);
    }
    /**
     * @Title: payCallback
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt 
     * @param: request
     * @param: response
     * @Date: 2019-02-26 13:41 
     * @throws:
     */
    @RequestMapping(value = "/payCallback", method = RequestMethod.POST)
    public void payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("进入微信支付异步通知");
        String resXml="";
        try{
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            logger.info("微信支付异步通知请求包: {}", resXml);
            resXml = wxPayService.payCallback(resXml);
        }catch (Exception e){
            logger.error("微信支付回调通知异常",e);
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[通知异常]]></return_msg>" + "</xml> ";
        }
        // ------------------------------
        // 处理业务完毕
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }



}
