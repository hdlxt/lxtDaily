package com.lxt.lxtpay.service.impl;

import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.module.alipay.util.AliPayConstants;
import com.lxt.lxtpay.service.PayCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName: AliPayCallbackServiceImpl
 * @Description: 支付宝支付回调业务处理
 * @Author: lxt
 * @Date: 2019-03-11 10:36
 * @Version 1.0
 **/
@Service(PayConstants.ALI_PAY_CALLBACK)
public class AliPayPayCallbackServiceImpl implements PayCallbackService {
    private final static Logger logger = LoggerFactory.getLogger(AliPayPayCallbackServiceImpl.class);
    /**
     * @Title: payCallback
     * @Description: 支付宝支付结束之后回调业务处理
     * @Author: lxt 
     * @param: payCallbackModel
     * @Date: 2019-03-11 10:37 
     * @throws: 
     */
    @Override
    public void payCallback(PayCallbackModel payCallbackModel) {
        logger.info("支付宝支付回调处理开始......");
        Map<String,String> params = payCallbackModel.getData();
        String outTradeNo = params.get(AliPayConstants.OUT_TRADE_NO);
        //!!!在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
        String status = params.get(AliPayConstants.TRADE_STATUS);
        logger.info("订单号：{}，订单状态：{}",outTradeNo,status);
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
             * 在支付回调方法中写业务
             */
        } else {

        }
        logger.info("支付宝支付回调处理结束......");
    }
}
