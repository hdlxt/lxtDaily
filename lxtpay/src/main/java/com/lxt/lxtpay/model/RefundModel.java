package com.lxt.lxtpay.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: RefundModel
 * @Description: 退款模型类
 * @Author: lxt
 * @Date: 2019-02-21 14:06
 * @Version 1.0
 **/
public class RefundModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商户订单号(唯一)
     */
    private String outTradeNo;
    /**
     * 第三方支付交易号
     */
    private String tradeNo;
    /**
     * 退款金额(单位是元)
     */
    @NotBlank(message = "金额不可为空")
    private String refundAmount;
    /**
     * 订单总金额 单位：元
     */
    private String totalAmount;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 商户 退款订单号
     * 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     */
    private String outRequestNo;
    /**
     * 微信退款单号 只有微信返回
     */
    private String refundId;
    /**
     * 订单发送时间
     */
    private String txnTime;
    /**
     * 原交易流水号
     */
    private String origQryId;
    /**
     * 支付类型(1:支付宝 2:微信 3:银联)
     */
    private String payType;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public RefundModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public RefundModel setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public RefundModel setRefundReason(String refundReason) {
        this.refundReason = refundReason;
        return this;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public RefundModel setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public RefundModel setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public RefundModel setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getRefundId() {
        return refundId;
    }

    public RefundModel setRefundId(String refundId) {
        this.refundId = refundId;
        return this;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public RefundModel setTxnTime(String txnTime) {
        this.txnTime = txnTime;
        return this;
    }

    public String getOrigQryId() {
        return origQryId;
    }

    public RefundModel setOrigQryId(String origQryId) {
        this.origQryId = origQryId;
        return this;
    }

    public String getPayType() {
        return payType;
    }

    public RefundModel setPayType(String payType) {
        this.payType = payType;
        return this;
    }

    @Override
    public String toString() {
        return "RefundModel{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", refundAmount='" + refundAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", outRequestNo='" + outRequestNo + '\'' +
                ", refundId='" + refundId + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", origQryId='" + origQryId + '\'' +
                ", payType=" + payType +
                '}';
    }
}
