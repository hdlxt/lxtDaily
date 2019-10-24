package com.lxt.lxtpay.model;

import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayConstants;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: RefundQueryModel
 * @Description: 退款交易查询模型类
 * @Author: lxt 
 * @Date: 2019-02-22 15:44
 * @Version 1.0
 **/
public class RefundQueryModel implements Serializable {
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
     * 退款请求号
     */
    private String outRequestNo;
    /**
     * 微信退款单号 只有微信返回
     */
    private String refundId;
    /**
     * 交易总金额
     */
    private String totalAmount;
    /**
     * 退款金额
     */
    private String refundAmount;
    /**
     * 请求返回内容
     */
    private String body;
    /**
     * 错误信息，查询失败时赋值
     */
    private String errMsg;
    /**
     * 退款成功时间
     */
    private String refundSuccessTime;
    /**
     * @Title: build
     * @Description:  创建支付宝退款查询结果
     * @Author: lxt 
     * @param: alipayTradeFastpayRefundQueryResponse
     * @Date: 2019-02-22 16:16 
     * @return: com.design.background.pay.vo.RefundQueryModel
     * @throws: 
     */
    public RefundQueryModel build(AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse){
        this.outTradeNo = alipayTradeFastpayRefundQueryResponse.getOutTradeNo();
        this.tradeNo = alipayTradeFastpayRefundQueryResponse.getTradeNo();
        this.outRequestNo = alipayTradeFastpayRefundQueryResponse.getOutRequestNo();
        this.refundAmount = alipayTradeFastpayRefundQueryResponse.getRefundAmount();
        this.totalAmount = alipayTradeFastpayRefundQueryResponse.getTotalAmount();
        return this;
    }
    /**
     * @Title: build
     * @Description: 创建微信退款查询结果
     * @Author: lxt 
     * @param: map
     * @Date: 2019-02-26 16:05 
     * @return: com.design.background.pay.model.RefundQueryModel
     * @throws: 
     */
    public RefundQueryModel build(Map<String, String> map,int i){
        this.outTradeNo = map.get(WXPayConstants.OUT_TRADE_NO);
        this.tradeNo = map.get(WXPayConstants.TRANSACTION_ID);
        this.outRequestNo = map.get(WXPayConstants.OUT_REFUND_NO+"_"+i);
        this.refundId = map.get(WXPayConstants.REFUND_ID+"_"+i);
        this.refundAmount = map.get(WXPayConstants.REFUND_FEE+"_"+i);
        this.totalAmount = map.get(WXPayConstants.TOTAL_FEE);
        this.refundSuccessTime = map.get(WXPayConstants.REFUND_SUCCESS_TIME+"_"+i);
        return this;
    }


    public String getOutTradeNo() {
        return outTradeNo;
    }
    public RefundQueryModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public RefundQueryModel setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public RefundQueryModel setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public RefundQueryModel setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public RefundQueryModel setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RefundQueryModel setBody(String body) {
        this.body = body;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public RefundQueryModel setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public String getRefundSuccessTime() {
        return refundSuccessTime;
    }

    public RefundQueryModel setRefundSuccessTime(String refundSuccessTime) {
        this.refundSuccessTime = refundSuccessTime;
        return this;
    }

    public String getRefundId() {
        return refundId;
    }

    public RefundQueryModel setRefundId(String refundId) {
        this.refundId = refundId;
        return this;
    }

    @Override
    public String toString() {
        return "RefundQueryModel{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", outRequestNo='" + outRequestNo + '\'' +
                ", refundId='" + refundId + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", refundAmount='" + refundAmount + '\'' +
                ", body='" + body + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", refundSuccessTime='" + refundSuccessTime + '\'' +
                '}';
    }
}
