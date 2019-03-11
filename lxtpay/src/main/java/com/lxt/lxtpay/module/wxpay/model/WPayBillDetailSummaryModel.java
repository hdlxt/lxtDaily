package com.lxt.lxtpay.module.wxpay.model;

import java.io.Serializable;

/**
 * @ClassName: WPayBillDetailSummaryModel
 * @Description: 汇总信息
 * @Author: lxt 
 * @Date: 2019-02-27 10:12
 * @Version 1.0
 **/
public class WPayBillDetailSummaryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总交易单数
     */
    private Double tradeSum;
    /**
     *应结订单总金额
     */
    private Double payableTradeFeeSum;
    /**
     *退款总金额
     */
    private Double refundFeeSum;
    /**
     *充值券退款总金额
     */
    private Double rechargeCouponRefundFeeSum;
    /**
     *手续费总金额
     */
    private Double serviceChargeSum;
    /**
     *申请退款总金额
     */
    private Double tradeFeeSum;
    /**
     *申请退款总金额
     */
    private Double applyRefundFeeSum;

    public Double getTradeSum() {
        return tradeSum;
    }

    public WPayBillDetailSummaryModel setTradeSum(Double tradeSum) {
        this.tradeSum = tradeSum;
        return this;
    }

    public Double getPayableTradeFeeSum() {
        return payableTradeFeeSum;
    }

    public WPayBillDetailSummaryModel setPayableTradeFeeSum(Double payableTradeFeeSum) {
        this.payableTradeFeeSum = payableTradeFeeSum;
        return this;
    }

    public Double getRefundFeeSum() {
        return refundFeeSum;
    }

    public WPayBillDetailSummaryModel setRefundFeeSum(Double refundFeeSum) {
        this.refundFeeSum = refundFeeSum;
        return this;
    }

    public Double getRechargeCouponRefundFeeSum() {
        return rechargeCouponRefundFeeSum;
    }

    public WPayBillDetailSummaryModel setRechargeCouponRefundFeeSum(Double rechargeCouponRefundFeeSum) {
        this.rechargeCouponRefundFeeSum = rechargeCouponRefundFeeSum;
        return this;
    }

    public Double getServiceChargeSum() {
        return serviceChargeSum;
    }

    public WPayBillDetailSummaryModel setServiceChargeSum(Double serviceChargeSum) {
        this.serviceChargeSum = serviceChargeSum;
        return this;
    }

    public Double getTradeFeeSum() {
        return tradeFeeSum;
    }

    public WPayBillDetailSummaryModel setTradeFeeSum(Double tradeFeeSum) {
        this.tradeFeeSum = tradeFeeSum;
        return this;
    }

    public Double getApplyRefundFeeSum() {
        return applyRefundFeeSum;
    }

    public WPayBillDetailSummaryModel setApplyRefundFeeSum(Double applyRefundFeeSum) {
        this.applyRefundFeeSum = applyRefundFeeSum;
        return this;
    }

    @Override
    public String toString() {
        return "WXPayBillDetailSummaryModel{" +
                "tradeSum=" + tradeSum +
                ", payableTradeFeeSum=" + payableTradeFeeSum +
                ", refundFeeSum=" + refundFeeSum +
                ", rechargeCouponRefundFeeSum=" + rechargeCouponRefundFeeSum +
                ", serviceChargeSum=" + serviceChargeSum +
                ", tradeFeeSum=" + tradeFeeSum +
                ", applyRefundFeeSum=" + applyRefundFeeSum +
                '}';
    }
}
