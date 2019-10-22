package com.lxt.lxtpay.module.wxpay.model;


import com.lxt.lxtpay.module.alipay.model.AliPayBillModel;

import java.io.Serializable;

/**
 * @ClassName: WxPayBillDetailModel
 * @Description: 每日对账单业务明细模型类
 * @Author: lxt
 * @Date: 2019-02-27 10:12
 * @Version 1.0
 **/
public class WxPayBillDetailModel extends AliPayBillModel implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     *交易时间
     */
    private String tradeTime;
    /**
     *公众账号ID
     */
    private String appId;
    /**
     *商户号
     */
    private String mchId;
    /**
     *特约商户号
     */
    private String subMchId;
    /**
     *设备号
     */
    private String deviceInfo;
    /**
     *微信订单号
     */
    private String transactionId;
    /**
     *商户订单号
     */
    private String outTradeNo;
    /**
     *用户标识
     */
    private String openId;
    /**
     *交易类型
     */
    private String tradeType;
    /**
     *交易状态
     */
    private String tradeState;
    /**
     *付款银行
     */
    private String bankType;
    /**
     *货币种类
     */
    private String feeType;
    /**
     *应结订单金额
     */
    private String settlementTotalFee;
    /**
     *代金券金额
     */
    private String couponFee;
    /**
     *微信退款单号
     */
    private String refundId;
    /**
     *商户退款单号
     */
    private String outRefundNo;
    /**
     *退款金额
     */
    private String refundFee;
    /**
     *充值券退款金额
     */
    private String rechargeCouponRefundFee;
    /**
     *退款类型
     */
    private String refundType;
    /**
     *退款状态
     */
    private String refundStatus;
    /**
     *商品名称
     */
    private String tradeName;
    /**
     *商户数据包
     */
    private String tradeDataPack;
    /**
     *手续费
     */
    private String serviceCharge;
    /**
     *费率
     */
    private String rate;
    /**
     *订单金额
     */
    private String totalFee;
    /**
     *申请退款金额
     */
    private String applyRefundFee;
    /**
     *费率备注
     */
    private String rateRemark;

    public String getTradeTime() {
        return tradeTime;
    }

    public WxPayBillDetailModel setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public WxPayBillDetailModel setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getMchId() {
        return mchId;
    }

    public WxPayBillDetailModel setMchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public WxPayBillDetailModel setSubMchId(String subMchId) {
        this.subMchId = subMchId;
        return this;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public WxPayBillDetailModel setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public WxPayBillDetailModel setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public WxPayBillDetailModel setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public WxPayBillDetailModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public WxPayBillDetailModel setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTradeState() {
        return tradeState;
    }

    public WxPayBillDetailModel setTradeState(String tradeState) {
        this.tradeState = tradeState;
        return this;
    }

    public String getBankType() {
        return bankType;
    }

    public WxPayBillDetailModel setBankType(String bankType) {
        this.bankType = bankType;
        return this;
    }

    public String getFeeType() {
        return feeType;
    }

    public WxPayBillDetailModel setFeeType(String feeType) {
        this.feeType = feeType;
        return this;
    }

    public String getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public WxPayBillDetailModel setSettlementTotalFee(String settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
        return this;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public WxPayBillDetailModel setCouponFee(String couponFee) {
        this.couponFee = couponFee;
        return this;
    }

    public String getRefundId() {
        return refundId;
    }

    public WxPayBillDetailModel setRefundId(String refundId) {
        this.refundId = refundId;
        return this;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public WxPayBillDetailModel setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
        return this;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public WxPayBillDetailModel setRefundFee(String refundFee) {
        this.refundFee = refundFee;
        return this;
    }

    public String getRechargeCouponRefundFee() {
        return rechargeCouponRefundFee;
    }

    public WxPayBillDetailModel setRechargeCouponRefundFee(String rechargeCouponRefundFee) {
        this.rechargeCouponRefundFee = rechargeCouponRefundFee;
        return this;
    }

    public String getRefundType() {
        return refundType;
    }

    public WxPayBillDetailModel setRefundType(String refundType) {
        this.refundType = refundType;
        return this;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public WxPayBillDetailModel setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
        return this;
    }

    public String getTradeName() {
        return tradeName;
    }

    public WxPayBillDetailModel setTradeName(String tradeName) {
        this.tradeName = tradeName;
        return this;
    }

    public String getTradeDataPack() {
        return tradeDataPack;
    }

    public WxPayBillDetailModel setTradeDataPack(String tradeDataPack) {
        this.tradeDataPack = tradeDataPack;
        return this;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public WxPayBillDetailModel setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public WxPayBillDetailModel setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public WxPayBillDetailModel setTotalFee(String totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public String getApplyRefundFee() {
        return applyRefundFee;
    }

    public WxPayBillDetailModel setApplyRefundFee(String applyRefundFee) {
        this.applyRefundFee = applyRefundFee;
        return this;
    }

    public String getRateRemark() {
        return rateRemark;
    }

    public WxPayBillDetailModel setRateRemark(String rateRemark) {
        this.rateRemark = rateRemark;
        return this;
    }

    @Override
    public String toString() {
        return "WXPayBillDetailModel{" +
                "tradeTime='" + tradeTime + '\'' +
                ", appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", subMchId='" + subMchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", openId='" + openId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", tradeState='" + tradeState + '\'' +
                ", bankType='" + bankType + '\'' +
                ", feeType='" + feeType + '\'' +
                ", settlementTotalFee='" + settlementTotalFee + '\'' +
                ", couponFee='" + couponFee + '\'' +
                ", refundId='" + refundId + '\'' +
                ", outRefundNo='" + outRefundNo + '\'' +
                ", refundFee='" + refundFee + '\'' +
                ", rechargeCouponRefundFee='" + rechargeCouponRefundFee + '\'' +
                ", refundType='" + refundType + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", tradeDataPack='" + tradeDataPack + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", rate='" + rate + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", applyRefundFee='" + applyRefundFee + '\'' +
                ", rateRemark='" + rateRemark + '\'' +
                '}';
    }
}
