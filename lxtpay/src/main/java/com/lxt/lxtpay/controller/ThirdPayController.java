package com.lxt.lxtpay.controller;

import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.module.alipay.service.AliPayService;
import com.lxt.lxtpay.module.unionpay.service.UnionPayService;
import com.lxt.lxtpay.module.wxpay.service.WxPayService;
import com.lxt.lxtpay.service.PayService;
import com.lxt.lxtpay.utill.DateUtil;
import com.lxt.lxtpay.utill.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName: PayController
 * @Description: 第三方支付控制层
 * @Author: lxt
 * @Date: 2019-03-06 11:21
 * @Version 1.0
 **/
@Controller
@RequestMapping("thirdPay")
public class ThirdPayController {
    private final static Logger logger = LoggerFactory.getLogger(ThirdPayController.class);
    private final static String BASE_URL="pay/";
    private final static String URL_SUCCESS=BASE_URL+"pay-success";
    @Autowired
    private PayService payService;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private UnionPayService unionPayService;
    /**
     * @Title: test
     * @Description: 测试
     * @Author: lxt 
     * @param: model
     * @Date: 2019-03-09 10:14 
     * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("orderId", PayUtil.getUniqueOrderId());
        model.addAttribute("txnTime", DateUtil.format(null,DateUtil.PATTERN_yyyyMMddHHmmss));
        return BASE_URL+"test-pay";
    }
    /**
     * @Title: payPC
     * @Description: 第三方支付方法
     * @Author: lxt 
     * @param: productModel
     * @Date: 2019-03-09 10:05 
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws: 
     */
    @PostMapping("pay")
    public String pay(Model model, ProductModel productModel){
        model.addAttribute("result",  payService.payPC(productModel));
        return BASE_URL+"pay-start";
    }
    /**
     * @Title: refund
     * @Description: 退款方法
     * @Author: lxt 
     * @param: refundModel
     * @Date: 2019-03-09 11:30 
     * @return: com.design.background.model.ResultData
     * @throws: 
     */
    @RequestMapping(value = "/refund",method = {RequestMethod.POST})
    @ResponseBody
    public ResultData refund(@RequestBody RefundModel refundModel){
        return payService.refund(refundModel);
    }

    /**
     * @Title: aliPayBackUrl
     * @Description: 支付宝支付成功后台回调验签&业务处理方法
     * @Author: lxt 
     * @param: request
     * @param: response
     * @Date: 2019-03-11 10:05 
     * @throws: 
     */
    @PostMapping(value="aliPayBackUrl")
    public void aliPayBackUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        aliPayService.notifyUrl(request,response);
    }
    /**
     * @Title: aliPayFrontUrl
     * @Description:  支付宝支付结束 前台回调验签跳转 支付结果页面方法
     * @Author: lxt 
     * @param: model
     * @param: request
     * @Date: 2019-03-11 10:08 
     * @return: java.lang.String
     * @throws: 
     */
    @GetMapping("/aliPayFrontUrl")
    public String  aliPayFrontUrl(Model model, HttpServletRequest request){
        return aliPayService.returnUrl(model,request,URL_SUCCESS);
    }
    /**
     * @Title: wxPayBackUrl
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt 
     * @param: request
     * @param: response
     * @Date: 2019-03-11 10:09 
     * @throws: 
     */
    @PostMapping(value = "/wxPayBackUrl")
    public void wxPayBackUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        wxPayService.payCallback(request,response);
    }
    /**
     * @Title: unionPayBackUrl
     * @Description: 
     * @Author: lxt 
     * @param: req
     * @param: resp
     * @Date: 2019-03-11 10:11 
     * @throws: 
     */
    @PostMapping(value="unionPayBackUrl")
    public void unionPayBackUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        unionPayService.backRcvResponse(req,resp);
    }
    /**
     * @Title: unionPayFrontUrl
     * @Description: 银联前台回调
     * @Author: lxt 
     * @param: model
     * @param: req
     * @param: resp
     * @Date: 2019-03-11 10:13 
     * @return: java.lang.String
     * @throws: 
     */
    @PostMapping(value="unionPayFrontUrl")
    public String unionPayFrontUrl(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        return unionPayService.frontRcvResponse(model,req,resp,URL_SUCCESS);
    }

}
