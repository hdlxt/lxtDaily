package com.lxt.lxtpay.module.alipay.model;

import com.alipay.api.response.AlipayTradeQueryResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @ClassName: AliPayTradeQueryModel
 * @Description: 交易查询结果模型类
 * @Author: lxt
 * @Date: 2019-02-22 15:09
 * @Version 1.0
 **/
public class AliPayTradeQueryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单号(唯一)
     */
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 订单状态
     */
    private String tradeStatus;
    /**
     * 买家支付宝账号
     */
    private String buyerLogonId;
    /**
     * 交易总金额
     */
    private String totalAmount;
    /**
     *买家用户类型。CORPORATE:企业用户；PRIVATE:个人用户
     */
    private String buyerUserType;
    /**
     * 订单标题（只在间连场景下返回）
     */
    private String subject;
    /**
     * 订单描述（只在间连场景下返回）
     */
    private String body;
    /**
     * 错误信息，查询失败时赋值
     */
    private String errMsg;
    /**
     * @Title: build
     * @Description: 创建交易订单信息
     * @Author: lxt 
     * @param: alipayTradeQueryResponse
     * @Date: 2019-02-22 15:16 
     * @return: com.design.background.pay.vo.AliPayTradeQueryModel
     * @throws: 
     */
    public AliPayTradeQueryModel build(AlipayTradeQueryResponse alipayTradeQueryResponse){
        if(StringUtils.isBlank(alipayTradeQueryResponse.getOutTradeNo())){
            return setErrMsg("未查询到交易信息");
        }
        this.outTradeNo = alipayTradeQueryResponse.getOutTradeNo();
        this.tradeNo = alipayTradeQueryResponse.getTradeNo();
        this.tradeStatus = alipayTradeQueryResponse.getTradeStatus();
        this.buyerLogonId = alipayTradeQueryResponse.getBuyerLogonId();
        this.totalAmount = alipayTradeQueryResponse.getTotalAmount();
        this.buyerUserType = alipayTradeQueryResponse.getBuyerUserType();
        return this;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public AliPayTradeQueryModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public AliPayTradeQueryModel setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public AliPayTradeQueryModel setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
        return this;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public AliPayTradeQueryModel setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public AliPayTradeQueryModel setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public AliPayTradeQueryModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public AliPayTradeQueryModel setBody(String body) {
        this.body = body;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public AliPayTradeQueryModel setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayTradeQueryModel{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", buyerLogonId='" + buyerLogonId + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", buyerUserType='" + buyerUserType + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
