package com.lxt.lxtpay.module.unionpay.model;


import com.lxt.lxtpay.module.unionpay.util.UnionPayConstants;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: WXPayTradeQueryModel
 * @Description: 微信订单查询结果模型类
 * @Author: lxt 
 * @Date: 2019-02-28 09:26
 * @Version 1.0
 **/

public class UnionPayTradeQueryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 产品类型
     */
    private String bizType;
    /**
     * 商户订单号(唯一)
     */
    private String orderId;
    /**
     * 原始流水号
     */
    private String queryId;
    /**
     * 买家卡号 eg:6216***********0018
     */
    private String accNo;
    /**
     * 发送订单时间
     */
    private String txnTime;
    /**
     * 交易类型
     */
    private String txnType;
    /**
     * 交易子类
     */
    private String txnSubType;
    /**
     * 接入类型
     * 0：商户直连接入
     * 1：收单机构接入
     * 2：平台商户接入
     */
    private String accessType;
    /**
     * 交易总金额
     */
    private String txnAmt;
    /**
     * 清算金额
     */
    private String settleAmt;
    /**
     * 清算币种
     */
    private String settleCurrencyCode;
    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 原交易应答码
     */
    private String origRespCode;
    /**
     * 原交易应答信息
     */
    private String origRespMsg;
    /**
     * 交易币种
     */
    private String currencyCode;
    /**
     * @Title: build
     * @Description: 创建银联查询实体
     * @Author: lxt 
     * @param: map
     * @Date: 2019-02-28 09:52 
     * @return: com.design.background.pay.unionpay.model.UnionPayTradeQueryModel
     * @throws: 
     */
    public UnionPayTradeQueryModel build(Map<String,String> map){
        this.bizType = map.get(UnionPayConstants.BIZ_TYPE);
        this.orderId = map.get(UnionPayConstants.ORDER_ID);
        this.settleAmt = map.get(UnionPayConstants.SETTLE_AMT);
        this.settleCurrencyCode = map.get(UnionPayConstants.SETTLE_CURRENCY_CODE);
        this.settleDate = map.get(UnionPayConstants.SETTLE_DATE);
        this.txnType = map.get(UnionPayConstants.TXN_TYPE);
        this.txnSubType = map.get(UnionPayConstants.TXN_SUB_TYPE);
        this.queryId = map.get(UnionPayConstants.QUERY_ID);
        this.txnTime = map.get(UnionPayConstants.TXN_TIME);
        this.accNo = map.get(UnionPayConstants.ACC_NO);
        this.origRespMsg = map.get(UnionPayConstants.ORIG_RESP_MSG);
        this.origRespCode = map.get(UnionPayConstants.ORIG_RESP_CODE);
        this.txnAmt = map.get(UnionPayConstants.TXN_AMT);
        this.accessType = map.get(UnionPayConstants.ACCESS_TYPE);
        this.currencyCode = map.get(UnionPayConstants.CURRENCY_CODE);
        return this;
    }

    public String getBizType() {
        return bizType;
    }

    public UnionPayTradeQueryModel setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public UnionPayTradeQueryModel setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getQueryId() {
        return queryId;
    }

    public UnionPayTradeQueryModel setQueryId(String queryId) {
        this.queryId = queryId;
        return this;
    }

    public String getAccNo() {
        return accNo;
    }

    public UnionPayTradeQueryModel setAccNo(String accNo) {
        this.accNo = accNo;
        return this;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public UnionPayTradeQueryModel setTxnTime(String txnTime) {
        this.txnTime = txnTime;
        return this;
    }

    public String getTxnType() {
        return txnType;
    }

    public UnionPayTradeQueryModel setTxnType(String txnType) {
        this.txnType = txnType;
        return this;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public UnionPayTradeQueryModel setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
        return this;
    }

    public String getAccessType() {
        return accessType;
    }

    public UnionPayTradeQueryModel setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public UnionPayTradeQueryModel setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
        return this;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public UnionPayTradeQueryModel setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
        return this;
    }

    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }

    public UnionPayTradeQueryModel setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
        return this;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public UnionPayTradeQueryModel setSettleDate(String settleDate) {
        this.settleDate = settleDate;
        return this;
    }

    public String getOrigRespCode() {
        return origRespCode;
    }

    public UnionPayTradeQueryModel setOrigRespCode(String origRespCode) {
        this.origRespCode = origRespCode;
        return this;
    }

    public String getOrigRespMsg() {
        return origRespMsg;
    }

    public UnionPayTradeQueryModel setOrigRespMsg(String origRespMsg) {
        this.origRespMsg = origRespMsg;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public UnionPayTradeQueryModel setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    @Override
    public String toString() {
        return "UnionPayTradeQueryModel{" +
                "bizType='" + bizType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", queryId='" + queryId + '\'' +
                ", accNo='" + accNo + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", txnType='" + txnType + '\'' +
                ", txnSubType='" + txnSubType + '\'' +
                ", accessType='" + accessType + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", settleAmt='" + settleAmt + '\'' +
                ", settleCurrencyCode='" + settleCurrencyCode + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", origRespCode='" + origRespCode + '\'' +
                ", origRespMsg='" + origRespMsg + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
