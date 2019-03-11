package com.lxt.lxtpay.module.alipay.util;

/**
 * @ClassName: AlipayConstants
 * @Description: 支付宝s相关常量
 * @Author: lxt 
 * @Date: 2019-02-21 14:28
 * @Version 1.0
 **/
public class AliPayConstants {
    /**
     * 应用ID
     */
    public final static String APP_ID = "app_id";
    /**
     * 订单编号
     */
    public final static String OUT_TRADE_NO = "out_trade_no";
    /**
     * 支付宝交易号
     */
    public final static String TRADE_NO = "trade_no";
    /**
     * 交易状态
     */
    public final static String TRADE_STATUS = "trade_status";
    /**
     * 交易状态
     *  WAIT_BUYER_PAY（交易创建，等待买家付款）、
     *  TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、T
     *  TRADE_SUCCESS（交易支付成功）、
     *  TRADE_FINISHED（交易结束，不可退款）
     */
    public final static String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    public final static String TRADE_CLOSED = "TRADE_CLOSED";
    public final static String TRADE_SUCCESS = "TRADE_SUCCESS";
    public final static String TRADE_FINISHED = "TRADE_FINISHED";
    /**
     * 总金额（单位：元）
     */
    public final static String TOTAL_AMOUNT = "total_amount";
    /**
     *订单名称
     */
    public final static String SUBJECT = "subject";
    /**
     *商户id
     */
    public final static String SELLER_ID = "seller_id";
    /**
     *销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
     */
    public final static String PRODUCT_CODE = "product_code";
    public final static String PRODUCT_CODE_VALUE = "FAST_INSTANT_TRADE_PAY";
    /**
     *商品描述
     */
    public final static String BODY = "body";
    /**
     *PC扫码支付的方式，支持前置模式和跳转模式。
     * 前置模式是将二维码前置到商户的订单确认页的模式。需要商户在自己的页面中以iframe方式请求支付宝页面。具体分为以下几种：
     * 0：订单码-简约前置模式，对应iframe宽度不能小于600px，高度不能小于300px；
     * 1：订单码-前置模式，对应iframe宽度不能小于300px，高度不能小于600px；
     * 3：订单码-迷你前置模式，对应iframe宽度不能小于75px，高度不能小于75px；
     * 4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。
     *
     * 跳转模式下，用户的扫码界面是由支付宝生成的，不在商户的域名下。
     *
     */
    public final static String QR_PAY_MODE = "qr_pay_mode";
    /**
     * 2：订单码-跳转模式
     */
    public final static String QR_PAY_MODE_VALUE = "2";
    /**
     * 账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：trade、signcustomer；
     *      trade指商户基于支付宝交易收单的业务账单；
     *      signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；
     */
    public final static String BILL_TYPE = "bill_type";
    public final static String BILL_TYPE_TRADE = "trade";
    public final static String BILL_TYPE_SIGNCUSTOMER = "signcustomer";
    /**
     * 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     */
    public final static String BILL_DATE = "bill_date";
    /**
     *标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     */
    public final static String OUT_REQUEST_NO = "out_request_no";
    /**
     * 退款原因，可以说明用户退款原因，方便为商家后台提供统计
     */
    public final static String REFUND_REASON = "refund_reason";
    /**
     * 退款金额，该金额必须小于等于订单的支付金额，单位为元
     */
    public final static String REFUND_AMOUNT = "refund_amount";
    /**
     * 退款金额，该金额必须小于等于订单的支付金额，单位为元
     */
    public final static String OPERATOR_ID = "operator_id";

}
