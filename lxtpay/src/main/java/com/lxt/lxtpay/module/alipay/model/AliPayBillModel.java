package com.lxt.lxtpay.module.alipay.model;

import java.io.Serializable;

/**
 * @ClassName: AliPayBillModel
 * @Description: 每日对账单业务明细模型基类
 * @Author: lxt
 * @Date: 2019-02-23 13:54
 * @Version 1.0
 **/
public class AliPayBillModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *门店编号
     */
    private String stroreCode;
    /**
     *门店名称
     */
    private String stroreName;
    /**
     *订单金额（元）
     */
    private Double tradeSumMoney;
    /**
     *商家实收（元）
     */
    private Double storeFactMoney;
    /**
     *支付宝优惠（元）
     */
    private Double alipayDiscount;
    /**
     *商家优惠（元）
     */
    private Double stroreDiscount;
    /**
     *卡消费金额（元）
     */
    private Double cardMoney;
    /**
     *服务费（元）
     */
    private Double serviceMoney;
    /**
     *分润（元）
     */
    private Double shareProfit;

    public String getStroreCode() {
        return stroreCode;
    }

    public AliPayBillModel setStroreCode(String stroreCode) {
        this.stroreCode = stroreCode;
        return this;
    }

    public String getStroreName() {
        return stroreName;
    }

    public AliPayBillModel setStroreName(String stroreName) {
        this.stroreName = stroreName;
        return this;
    }

    public Double getTradeSumMoney() {
        return tradeSumMoney;
    }

    public AliPayBillModel setTradeSumMoney(Double tradeSumMoney) {
        this.tradeSumMoney = tradeSumMoney;
        return this;
    }

    public Double getStoreFactMoney() {
        return storeFactMoney;
    }

    public AliPayBillModel setStoreFactMoney(Double storeFactMoney) {
        this.storeFactMoney = storeFactMoney;
        return this;
    }

    public Double getAlipayDiscount() {
        return alipayDiscount;
    }

    public AliPayBillModel setAlipayDiscount(Double alipayDiscount) {
        this.alipayDiscount = alipayDiscount;
        return this;
    }

    public Double getStroreDiscount() {
        return stroreDiscount;
    }

    public AliPayBillModel setStroreDiscount(Double stroreDiscount) {
        this.stroreDiscount = stroreDiscount;
        return this;
    }

    public Double getCardMoney() {
        return cardMoney;
    }

    public AliPayBillModel setCardMoney(Double cardMoney) {
        this.cardMoney = cardMoney;
        return this;
    }

    public Double getServiceMoney() {
        return serviceMoney;
    }

    public AliPayBillModel setServiceMoney(Double serviceMoney) {
        this.serviceMoney = serviceMoney;
        return this;
    }

    public Double getShareProfit() {
        return shareProfit;
    }

    public AliPayBillModel setShareProfit(Double shareProfit) {
        this.shareProfit = shareProfit;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayBillModel{" +
                "stroreCode='" + stroreCode + '\'' +
                ", stroreName='" + stroreName + '\'' +
                ", tradeSumMoney=" + tradeSumMoney +
                ", storeFactMoney=" + storeFactMoney +
                ", alipayDiscount=" + alipayDiscount +
                ", stroreDiscount=" + stroreDiscount +
                ", cardMoney=" + cardMoney +
                ", serviceMoney=" + serviceMoney +
                ", shareProfit=" + shareProfit +
                '}';
    }
}
