package com.lxt.lxtpay.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: ProductVo
 * @Description: 产品模型类
 * @Author: lxt
 * @Date: 2019-02-21 14:06
 * @Version 1.0
 **/
public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 订单名称
     */
    private String subject;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 总金额(单位是元)
     */
    @NotBlank(message="订单金额不可为空")
    private String totalFee;
    /**
     * 订单号(唯一)
     */
    @NotBlank(message="订单号不可为空")
    private String outTradeNo;
    /**
     * 发起人IP地址
     */
    private String spbillCreateIp;
    /**
     * 发送订单时间（银联必填）
     * 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
     */
    private String txnTime;
    /**
     * 附件数据主要用于商户携带订单的自定义数据
     */
    private String attach;
    /**
     * 支付类型(1:支付宝 2:微信 3:银联)
     */
    private String payType;
    /**
     * 前台回调地址  非扫码支付使用
     */
    private String frontUrl;

    public String getProductId() {
        return productId;
    }

    public ProductModel setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ProductModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ProductModel setBody(String body) {
        this.body = body;
        return this;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public ProductModel setTotalFee(String totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public ProductModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public ProductModel setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public ProductModel setAttach(String attach) {
        this.attach = attach;
        return this;
    }


    public String getFrontUrl() {
        return frontUrl;
    }

    public ProductModel setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
        return this;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public ProductModel setTxnTime(String txnTime) {
        this.txnTime = txnTime;
        return this;
    }

    public String getPayType() {
        return payType;
    }

    public ProductModel setPayType(String payType) {
        this.payType = payType;
        return this;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productId='" + productId + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", attach='" + attach + '\'' +
                ", payType='" + payType + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                '}';
    }
}
