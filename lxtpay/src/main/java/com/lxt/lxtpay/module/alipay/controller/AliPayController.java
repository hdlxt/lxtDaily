package com.lxt.lxtpay.module.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.lxt.lxtpay.common.CharConstants;
import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.module.alipay.service.AliPayService;
import com.lxt.lxtpay.module.alipay.util.AliPayConstants;
import com.lxt.lxtpay.properties.AlipayProerties;
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
import java.io.BufferedOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: AlipayController
 * @Description: 支付宝支付控制层
 * @Author: lxt
 * @Date: 2019-02-21 12:28
 * @Version 1.0
 **/
@Controller
@RequestMapping("/aliPay")
public class AliPayController {
    private final static Logger logger = LoggerFactory.getLogger(AliPayController.class);
    private final static String BASE_URL="pay/alipay/";
    @Autowired
    private AliPayService alipayService;
    @Autowired
    private AlipayProerties alipayProerties;
    /**
     * @Title: test
     * @Description: 跳转到测试页面
     * @Author: lxt 
     * @Date: 2019-02-21 14:37 
     * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("orderId", DateUtil.format(null,DateUtil.PATTERN_yyyyMMddHHmmss));
        return "test/test-alipay";
    }
    /**
     * @Title: payPC
     * @Description: pc端支付
     * @Author: lxt
     * @param: model
     * @param: productModel
     * @Date: 2019-02-21 19:10
     * @return: java.lang.String
     * @throws:
     */
    @RequestMapping("/payPC")
    public String payPC(Model model, @Valid ProductModel productModel){
        model.addAttribute("form", alipayService.payPC(productModel).getMessage());
        return BASE_URL+"pay-pc";
    }
    /**
     * @Title: payPC
     * @Description:  退款
     * @Author: lxt 
     * @param: model
     * @param: refundModel
     * @Date: 2019-02-21 19:10 
     * @return: com.design.background.model.ResultData
     * @throws: 
     */
    @RequestMapping(value = "/refund",method = {RequestMethod.POST})
    @ResponseBody
    public ResultData refund(@RequestBody @Valid RefundModel refundModel){
        return alipayService.refund(refundModel);
    }
    /**
     * @Title: downloadUrl
     * @Description: 查询对账单下载地址
     * @Author: lxt 
     * @param: billDate
     * @Date: 2019-02-22 14:13 
     * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping(value = "/downloadurl")
    @ResponseBody
    public ResultData downloadurl(String billDate){
        return alipayService.downloadUrl(billDate);
    }
    /**
     * @Title: notify
     * @Description: 支付宝支付后台回调
     * @Author: lxt
     * @param: request
     * @param: response
     * @Date: 2019-02-21 15:00
     * @throws:
     */
    @RequestMapping(value="notifyUrl",method= RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String  message = "success";
        Map<String, String> params = new HashMap<String, String>(20);
        // 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        //验证签名 校验签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayProerties.getAlipayPublicKey(), alipayProerties.getCharSet(), alipayProerties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            message =  "failed";
        }
        if (signVerified) {
            logger.info("支付宝验证签名成功！");
            // 若参数中的appid和填入的appid不相同，则为异常通知
            if (!alipayProerties.getAppId().equals(params.get(AliPayConstants.APP_ID))) {
                logger.info("与付款时的appid不同，此为异常通知，应忽略！");
                message =  "failed";
            }else{
                String outTradeNo = params.get(AliPayConstants.OUT_TRADE_NO);
                //!!!在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
                String status = params.get(AliPayConstants.TRADE_STATUS);
                if (status.equals(AliPayConstants.WAIT_BUYER_PAY)) {
                    // 如果状态是正在等待用户付款
                    logger.info(outTradeNo + "订单的状态正在等待用户付款");
                } else if (status.equals(AliPayConstants.TRADE_CLOSED)) {
                    // 如果状态是未付款交易超时关闭，或支付完成后全额退款
                    logger.info(outTradeNo + "订单的状态已经关闭");
                } else if (status.equals(AliPayConstants.TRADE_SUCCESS) || status.equals(AliPayConstants.TRADE_FINISHED)) {
                    // 如果状态是已经支付成功
                    logger.info("(支付宝订单号:"+outTradeNo+"付款成功)");
                    /**
                     * ！！！这里 根据实际业务场景 做相应的操作
                     */

                } else {

                }
            }
        } else { // 如果验证签名没有通过
            message =  "failed";
            logger.info("验证签名失败！");
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(message.getBytes());
        out.flush();
        out.close();
    }

    /**
     * @Title: returnUrl
     * @Description: 支付宝支付PC端前台回调
     * @Author: lxt
     * @param: request
     * @Date: 2019-02-21 15:12
     * @return: java.lang.String
     * @throws:
     */
    @RequestMapping("/returnUrl")
    public String  returnUrl(Model model, HttpServletRequest request){
        model.addAttribute("result","支付失败");
        try {
            //获取支付宝GET过来反馈信息
            Map<String,String> params = new HashMap<String,String>(20);
            Map<String,String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(CharConstants.ISO_8859_1), CharConstants.UTF_8);
                params.put(name, valueStr);
            }
            //商户订单号
            String orderNo = new String(request.getParameter(AliPayConstants.OUT_TRADE_NO).getBytes(CharConstants.ISO_8859_1),CharConstants.UTF_8);
            //前台回调验证签名 v1 or v2
            boolean signVerified = alipayService.rsaCheckV1(params);
            if(signVerified) {
                logger.info("订单号"+orderNo+"验证签名结果[成功].");
                /**
                 * !!!!!!处理业务逻辑
                 */
                model.addAttribute("result","支付成功");
            }else {
                logger.info("订单号"+orderNo+"验证签名结果[失败].");
            }
        } catch (Exception e) {
            logger.error("支付宝支付PC端前台回调异常",e);
        }

        //支付成功、跳转到成功页面
        return BASE_URL+"success";
    }
}
