package com.lxt.lxtpay.service.impl;

import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.service.PayCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName: WxPayCallbackServiceImpl
 * @Description: 微信支付回调业务处理
 * @Author: lxt
 * @Date: 2019-03-11 10:36
 * @Version 1.0
 **/
@Service(PayConstants.WX_PAY_CALLBACK)
public class WxPayCallbackServiceImpl implements PayCallbackService {
    private final static Logger logger = LoggerFactory.getLogger(WxPayCallbackServiceImpl.class);
    /**
     * @Title: payCallback
     * @Description: 微信支付结束回调业务处理
     * @Author: lxt 
     * @param: payCallbackModel
     * @Date: 2019-03-11 10:45 
     * @throws: 
     */
    @Override
    public void payCallback(PayCallbackModel payCallbackModel) {
        logger.info("微信支付回调处理开始......");
        logger.info("微信宝支付回调处理结束......");
    }
}
