package com.lxt.lxtpay.module.alipay.util;

/**
 * @ClassName: AlipayBillConstants
 * @Description: 支付宝对账单常量
 * @Author: lxt 
 * @Date: 2019-02-23 11:14
 * @Version 1.0
 **/
public class AliPayBillConstants {
    /**
     *获取业务明细列表信息
     */
    public final static String TYPE_DETAIL = "1000";
    /**
     *获取业务明细列表结束后的汇总信息
     */
    public final static String TYPE_DETAIL_SUMARRY = "1001";
    /**
     *获取业务明细汇总表的信息
     */
    public final static String TYPE_SUMARRY = "1002";
    /**
     * 获取所有信息
     */
    public final static String TYPE_ALL = "1003";
    /**
     * 业务明细和业务明细汇总公用常量
     */
    public final static String STRORE_CODE = "门店编号";
    public final static String STRORE_NAME = "门店名称";
    public final static String ORDER_SUM_MONEY = "订单金额（元）";
    public final static String STORE_FACT_MONEY = "商家实收（元）";
    public final static String ALIPAY_DISCOUNT = "支付宝优惠（元）";
    public final static String STRORE_DISCOUNT = "商家优惠（元）";
    public final static String CARD_MONEY = "卡消费金额（元）";
    public final static String SERVICE_MONEY = "服务费（元）";
    public final static String SHARE_PROFIT = "分润（元）";

    /**
     * 业务明细独有公用常量
     */
    public final static String BILL_DETAIL = "业务明细";
    public final static String TRADE_NO = "支付宝交易号";
    public final static String OUT_TRADE_NO = "商户订单号";
    public final static String BUSINESS_TYPE = "业务类型";
    public final static String PRODUCT_NAME = "商品名称";
    public final static String CRE_TIME = "创建时间";
    public final static String END_TIME = "完成时间";
    public final static String OPERATOR = "操作员";
    public final static String TERMINAL_ID = "终端号";
    public final static String BUY_USER_ID = "对方账户";
    public final static String ALIPAY_RED_PACKET = "支付宝红包（元）";
    public final static String JI_BAO_BAO = "集分宝（元）";
    public final static String COUPON_WRITE_OFF = "券核销金额（元）";
    public final static String COUPON_NAME = "券名称";
    public final static String STORE_RED_PACKET = "商家红包消费金额（元）";
    public final static String OUT_REQUEST_NO = "退款批次号/请求号";
    public final static String REMARK = "备注";
    public final static String BILL_DETAIL_LIST = "业务明细列表";
    public final static String BILL_DETAIL_LIST_END = "业务明细列表结束";

    public final static String TRADE_SUM_VAL = "交易合计：";
    public final static String REFUND_SUM_VAL = "退款合计：";
    public final static String BI = "笔";
    public final static String YUAN = "元";
    public final static String TRADE_SUM_MONEY_VAL = "商家实收共";
    public final static String REFUND_SUM_MONEY_VAL = "商家实收退款共";
    public final static String COUPON_SUM_MONEY_VAL = "商家优惠共";
    public final static String COUPON_REFUND_SUM_MONEY_VAL = "商家优惠退款共";
    /**
     * 业务明细汇总独有常量
     */
    public final static String BILL_SUMMARY = "业务明细(汇总)";
    public final static String TRADE_SUM = "交易订单总笔数";
    public final static String REFUND_TRADE_SUM = "退款订单总笔数";
    public final static String NET_PROCEEDS = "实收净额（元）";
    public final static String SUMMARY = "合计";
}
