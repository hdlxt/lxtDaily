package com.lxt.lxtpay.service.impl;

import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.service.PayCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UnionPayCallbackServiceImpl
 * @Description: 银联支付回调业务处理
 * @Author: lxt
 * @Date: 2019-03-11 10:36
 * @Version 1.0
 **/
@Service(PayConstants.UNION_PAY_CALLBACK)
public class UnionPayCallbackServiceImpl implements PayCallbackService {
    private final static Logger logger = LoggerFactory.getLogger(UnionPayCallbackServiceImpl.class);
    /**
     * @Title: payCallback
     * @Description: 银联支付结束回调业务处理
     * @Author: lxt 
     * @param: payCallbackModel
     * @Date: 2019-03-11 10:46 
     * @throws: 
     */
    @Override
    public void payCallback(PayCallbackModel payCallbackModel) {
        logger.info("银联支付回调处理开始......");
        logger.info("银联宝支付回调处理结束......");
    }
}
