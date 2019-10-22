package com.lxt.lxtpay.module.wxpay.model;


import com.lxt.lxtpay.module.wxpay.sdk.WXPayConstants;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: WxPayTradeQueryModel
 * @Description: 微信订单查询结果模型类
 * @Author: lxt 
 * @Date: 2019-02-28 09:26
 * @Version 1.0
 **/
public class WxPayTradeQueryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商户订单号(唯一)
     */
    private String outTradeNo;
    /**
     * 微信唯一订单号
     */
    private String transactionId;
    /**
     * 支付方式
     */
    private String tradeType;
    /**
     * 订单状态
     */
    private String tradeStatus;
    /**
     * 订单状态描述
     */
    private String tradeStateDesc;
    /**
     * 买家账号
     */
    private String openId;
    /**
     * 交易总金额
     */
    private String totalFee;
    /**
     * @Title: build
     * @Description: 创建订单查询结果实体
     * @Author: lxt 
     * @param: map
     * @Date: 2019-02-28 09:23 
     * @return: com.design.background.pay.wxpay.model.WXTradeQueryModel
     * @throws: 
     */
    public WxPayTradeQueryModel build(Map<String,String> map){
        this.outTradeNo = map.get(WXPayConstants.OUT_TRADE_NO);
        this.transactionId = map.get(WXPayConstants.TRANSACTION_ID);
        this.tradeStatus = map.get(WXPayConstants.TRADE_STATE);
        this.tradeStateDesc = map.get(WXPayConstants.TRADE_STATE_DESC);
        this.openId = map.get(WXPayConstants.OPENID);
        this.totalFee = map.get(WXPayConstants.TOTAL_FEE);
        this.tradeType = map.get(WXPayConstants.TRADE_TYPE);
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public WxPayTradeQueryModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public WxPayTradeQueryModel setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public WxPayTradeQueryModel setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public WxPayTradeQueryModel setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
        return this;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public WxPayTradeQueryModel setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public WxPayTradeQueryModel setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public WxPayTradeQueryModel setTotalFee(String totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    @Override
    public String toString() {
        return "WXTradeQueryModel{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", tradeStateDesc='" + tradeStateDesc + '\'' +
                ", openId='" + openId + '\'' +
                ", totalFee='" + totalFee + '\'' +
                '}';
    }
}
