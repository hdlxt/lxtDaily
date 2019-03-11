package com.lxt.lxtpay.module.alipay.model;

import java.io.Serializable;

/**
 * @ClassName: AliPayBillDetailSummaryModel
 * @Description: 每日对账单业务明细列表结束之后的汇总信息模型类
 *   末班必须固定如下:
 *      #交易合计：4笔，交易金额共0.08元，优惠金额共0.00元
 *      #退款合计：0笔，交易退款金额共0.00元，优惠退款金额共0.00元
 * @Author: lxt 
 * @Date: 2019-02-23 14:27
 * @Version 1.0
 **/
public class AliPayBillDetailSummaryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *交易订单总笔数
     */
    private String tradeSum;
    /**
     *交易订单总金额
     */
    private String tradeSumMoney;
    /**
     * 交易优惠总金额
     */
    private String tradeSumCouponMoney;
    /**
     *退款订单总笔数
     */
    private String refundTradeSum;
    /**
     *退款订单总金额
     */
    private String refundTradeSumMoney;
    /**
     *退款订单优惠退款总金额
     */
    private String refundTradeSumCouponMoney;

    public String getTradeSum() {
        return tradeSum;
    }

    public AliPayBillDetailSummaryModel setTradeSum(String tradeSum) {
        this.tradeSum = tradeSum;
        return this;
    }

    public String getTradeSumMoney() {
        return tradeSumMoney;
    }

    public AliPayBillDetailSummaryModel setTradeSumMoney(String tradeSumMoney) {
        this.tradeSumMoney = tradeSumMoney;
        return this;
    }

    public String getTradeSumCouponMoney() {
        return tradeSumCouponMoney;
    }

    public AliPayBillDetailSummaryModel setTradeSumCouponMoney(String tradeSumCouponMoney) {
        this.tradeSumCouponMoney = tradeSumCouponMoney;
        return this;
    }

    public String getRefundTradeSum() {
        return refundTradeSum;
    }

    public AliPayBillDetailSummaryModel setRefundTradeSum(String refundTradeSum) {
        this.refundTradeSum = refundTradeSum;
        return this;
    }

    public String getRefundTradeSumMoney() {
        return refundTradeSumMoney;
    }

    public AliPayBillDetailSummaryModel setRefundTradeSumMoney(String refundTradeSumMoney) {
        this.refundTradeSumMoney = refundTradeSumMoney;
        return this;
    }

    public String getRefundTradeSumCouponMoney() {
        return refundTradeSumCouponMoney;
    }

    public AliPayBillDetailSummaryModel setRefundTradeSumCouponMoney(String refundTradeSumCouponMoney) {
        this.refundTradeSumCouponMoney = refundTradeSumCouponMoney;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayBillDetailSummaryModel{" +
                "tradeSum='" + tradeSum + '\'' +
                ", tradeSumMoney='" + tradeSumMoney + '\'' +
                ", tradeSumCouponMoney='" + tradeSumCouponMoney + '\'' +
                ", refundTradeSum='" + refundTradeSum + '\'' +
                ", refundTradeSumMoney='" + refundTradeSumMoney + '\'' +
                ", refundTradeSumCouponMoney='" + refundTradeSumCouponMoney + '\'' +
                '}';
    }
}
