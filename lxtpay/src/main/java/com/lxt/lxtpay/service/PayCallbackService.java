package com.lxt.lxtpay.service;


import com.lxt.lxtpay.model.PayCallbackModel;

/**
 * @ClassName: CallbackService
 * @Description: 支付回调业务接口
 * @Author: lxt
 * @Date: 2019-03-11 10:33
 * @Version 1.0
 **/
public interface PayCallbackService {
    /**
     * @Title: payCallback
     * @Description: 支付回调业务处理接口
     * @Author: lxt 
     * @param: payCallbackModel
     * @Date: 2019-03-11 10:35 
     * @throws: 
     */
    void payCallback(PayCallbackModel payCallbackModel);
}
