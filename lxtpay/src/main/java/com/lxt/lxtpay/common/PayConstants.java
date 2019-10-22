package com.lxt.lxtpay.common;

/**
 * @ClassName: PayConstants
 * @Description: 第三方支付常量类
 * @Author: lxt
 * @Date: 2019-03-06 11:36
 * @Version 1.0
 **/
public class PayConstants {
    /**
     * 支付类型(1:支付宝 2:微信 3:银联)
     */
    public final static String PAY_TYPE_ALIPAY = "1";
    public final static String PAY_TYPE_WXPAY = "2";
    public final static String PAY_TYPE_UNIONPAY = "3";
    /**
     * 支付回调接口 注册bean名称
     */
    public final static String ALI_PAY_CALLBACK = "aliPayPayCallbackService";
    public final static String WX_PAY_CALLBACK = "unionPayCallbackService";
    public final static String UNION_PAY_CALLBACK = "wxPayCallbackService";
}
