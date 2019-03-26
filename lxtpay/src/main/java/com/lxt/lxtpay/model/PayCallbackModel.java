package com.lxt.lxtpay.model;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName: PayCallbackModel
 * @Description: 支付回调参数模型
 * @Author: lxt
 * @Date: 2019-03-11 10:29
 * @Version 1.0
 **/
@Data
public class PayCallbackModel {

    private Map<String,String> data;
    private String code;

    public PayCallbackModel setData(Map<String, String> data) {
        this.data = data;
        return this;
    }
}
