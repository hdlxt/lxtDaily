package com.lxt.lxtpay.module.wxpay.util;

/**
 * @ClassName: WxPayBillConstants
 * @Description: 微信支付对账单常量
 * @Author: lxt
 * @Date: 2019-02-27 09:43
 * @Version 1.0
 **/
public class WxPayBillConstants {
    /**
     * 对账单列数 27列
     */
    public final static int BILL_CLOUNM_COUNT = 27;
    /**
     * 汇总数据列数 7列
     */
    public final static int BILL_SUMMARY_CLOUNM_COUNT = 7;
    /**
     * 返回的对账单数据
     */
    public final static String BILL_DATA = "data";
    /**
     * 业务明细相关=============
     */
    public final static String TRADE_TIME = "交易时间";
    public final static String APP_ID = "公众账号ID";
    public final static String MCH_ID = "商户号";
    public final static String SUB_MCH_ID = "特约商户号";
    public final static String DEVICE_INFO = "设备号";
    public final static String TRANSACTION_ID = "微信订单号";
    public final static String OUT_TRADE_NO_ = "商户订单号";
    public final static String OPEN_ID = "用户标识";
    public final static String TRADE_TYPE = "交易类型";
    public final static String TRADE_STATE = "交易状态";
    public final static String BANK_TYPE = "付款银行";
    public final static String FEE_TYPE = "货币种类";
    public final static String SETTLEMENT_TOTAL_FEE = "应结订单金额";
    public final static String COUPON_FEE = "代金券金额";
    public final static String REFUND_ID = "微信退款单号";
    public final static String OUT_REFUND_NO_ = "商户退款单号";
    public final static String REFUND_FEE_ = "退款金额";
    public final static String RECHARGE_COUPON_REFUND_FEE = "充值券退款金额";
    public final static String REFUND_TYPE = "退款类型";
    public final static String REFUND_STATUS = "退款状态";
    public final static String TRADE_NAME = "商品名称";
    public final static String TRADE_DATA_PACK = "商户数据包";
    public final static String SERVICE_CHARGE = "手续费";
    public final static String RATE = "费率";
    public final static String TOTAL_FEE = "订单金额";
    public final static String APPLY_REFUND_FEE = "申请退款金额";
    public final static String RATE_REMARK = "费率备注";
    /**
     * 业务明细汇总相关=============
     */
    public final static String TRADE_SUM = "总交易单数";
    public final static String PAYABLE_TRADE_FEE_SUM = "应结订单总金额";
    public final static String REFUND_FEE_SUM = "退款总金额";
    public final static String RECHARGE_COUPON_REFUND_FEE_SUM = "充值券退款总金额";
    public final static String SERVICE_CHARGE_SUM = "手续费总金额";
    public final static String TRADE_FEE_SUM = "订单总金额";
    public final static String APPLY_REFUND_FEE_SUM = "申请退款总金额";
}
