package com.lxt.lxtpay.module.alipay.model;


import com.lxt.lxtpay.module.alipay.util.AliPayBillConstants;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: AliPayBillDetailModel
 * @Description: 每日对账单业务明细模型类
 * @Author: lxt 
 * @Date: 2019-02-23 11:22
 * @Version 1.0
 **/
public class AliPayBillDetailModel extends AliPayBillModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *支付宝交易号
     */
    private String tradeNo;
    /**
     *商户订单号
     */
    private String outTradeNo;
    /**
     *业务类型
     */
    private String businessType;
    /**
     *商品名称
     */
    private String productName;
    /**
     *创建时间
     */
    private String creTime;
    /**
     *完成时间
     */
    private String endTime;
    /**
     *操作员
     */
    private String operator;
    /**
     *终端号
     */
    private String terminalId;
    /**
     *对方账户
     */
    private String buyUserId;
    /**
     *支付宝红包（元）
     */
    private Double alipayRedPacket;
    /**
     *集分宝（元）
     */
    private Double jiBaoBao;
    /**
     *券核销金额（元）
     */
    private Double couponWriteOff;
    /**
     *券名称
     */
    private String couponName;
    /**
     *商家红包消费金额（元）
     */
    private Double storeRedPacket;
    /**
     *退款批次号/请求号
     */
    private String outRequestNo;
    /**
     *备注
     */
    private String remark;
    /**
     * @Title: build
     * @Description: 构建业务明细实例
     * @Author: lxt 
     * @param: detailArr
     * @param: titleMap
     * @Date: 2019-02-23 14:15 
     * @return: com.design.background.pay.alipay.model.AliPayBillDetailModel
     * @throws: 
     */
    public AliPayBillDetailModel build(String[] detailArr, Map<Integer,String> titleMap){
        for (int i = 0; i < detailArr.length; i++) {
            String str = detailArr[i].trim();
            switch (titleMap.get(i)){
                case AliPayBillConstants.STRORE_CODE:
                    setStroreCode(str);
                    break;
                case AliPayBillConstants.STRORE_NAME:
                    setStroreName(str);
                    break;
                case AliPayBillConstants.ORDER_SUM_MONEY:
                    setTradeSumMoney(Double.valueOf(str));
                    break;
                case AliPayBillConstants.STORE_FACT_MONEY:
                    setStoreFactMoney(Double.valueOf(str));
                    break;
                case AliPayBillConstants.ALIPAY_DISCOUNT:
                    setAlipayDiscount(Double.valueOf(str));
                    break;
                case AliPayBillConstants.STRORE_DISCOUNT:
                    setStroreDiscount(Double.valueOf(str));
                    break;
                case AliPayBillConstants.CARD_MONEY:
                    setCardMoney(Double.valueOf(str));
                    break;
                case AliPayBillConstants.SERVICE_MONEY:
                    setServiceMoney(Double.valueOf(str));
                    break;
                case AliPayBillConstants.SHARE_PROFIT:
                    setShareProfit(Double.valueOf(str));
                    break;
                case AliPayBillConstants.TRADE_NO:
                    this.tradeNo = str;
                    break;
                case AliPayBillConstants.OUT_TRADE_NO:
                    this.outTradeNo = str;
                    break;
                case AliPayBillConstants.BUSINESS_TYPE:
                    this.businessType = str;
                    break;
                case AliPayBillConstants.PRODUCT_NAME:
                    this.productName = str;
                    break;
                case AliPayBillConstants.CRE_TIME:
                    this.creTime = str;
                    break;
                case AliPayBillConstants.END_TIME:
                    this.endTime = str;
                    break;
                case AliPayBillConstants.OPERATOR:
                    this.operator = str;
                    break;
                case AliPayBillConstants.TERMINAL_ID:
                    this.terminalId = str;
                    break;
                case AliPayBillConstants.BUY_USER_ID:
                    this.buyUserId = str;
                    break;
                case AliPayBillConstants.ALIPAY_RED_PACKET:
                    this.alipayRedPacket = Double.valueOf(str);
                    break;
                case AliPayBillConstants.JI_BAO_BAO:
                    this.jiBaoBao = Double.valueOf(str);
                    break;
                case AliPayBillConstants.COUPON_WRITE_OFF:
                    this.couponWriteOff = Double.valueOf(str);
                    break;
                case AliPayBillConstants.COUPON_NAME:
                    this.couponName = str;
                    break;
                case AliPayBillConstants.STORE_RED_PACKET:
                    this.storeRedPacket = Double.valueOf(str);
                    break;
                case AliPayBillConstants.OUT_REQUEST_NO:
                    this.outRequestNo = str;
                    break;
                case AliPayBillConstants.REMARK:
                    this.remark = str;
                    break;
                default:
            }
        }
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public AliPayBillDetailModel setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public AliPayBillDetailModel setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getBusinessType() {
        return businessType;
    }

    public AliPayBillDetailModel setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public AliPayBillDetailModel setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getCreTime() {
        return creTime;
    }

    public AliPayBillDetailModel setCreTime(String creTime) {
        this.creTime = creTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public AliPayBillDetailModel setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public AliPayBillDetailModel setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public AliPayBillDetailModel setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public String getBuyUserId() {
        return buyUserId;
    }

    public AliPayBillDetailModel setBuyUserId(String buyUserId) {
        this.buyUserId = buyUserId;
        return this;
    }

    public Double getAlipayRedPacket() {
        return alipayRedPacket;
    }

    public AliPayBillDetailModel setAlipayRedPacket(Double alipayRedPacket) {
        this.alipayRedPacket = alipayRedPacket;
        return this;
    }

    public Double getJiBaoBao() {
        return jiBaoBao;
    }

    public AliPayBillDetailModel setJiBaoBao(Double jiBaoBao) {
        this.jiBaoBao = jiBaoBao;
        return this;
    }

    public Double getCouponWriteOff() {
        return couponWriteOff;
    }

    public AliPayBillDetailModel setCouponWriteOff(Double couponWriteOff) {
        this.couponWriteOff = couponWriteOff;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public AliPayBillDetailModel setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public Double getStoreRedPacket() {
        return storeRedPacket;
    }

    public AliPayBillDetailModel setStoreRedPacket(Double storeRedPacket) {
        this.storeRedPacket = storeRedPacket;
        return this;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public AliPayBillDetailModel setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AliPayBillDetailModel setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayBillDetailModel{" +
                "tradeNo='" + tradeNo + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", businessType='" + businessType + '\'' +
                ", productName='" + productName + '\'' +
                ", creTime='" + creTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", operator='" + operator + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", buyUserId='" + buyUserId + '\'' +
                ", alipayRedPacket=" + alipayRedPacket +
                ", jiBaoBao=" + jiBaoBao +
                ", couponWriteOff=" + couponWriteOff +
                ", couponName='" + couponName + '\'' +
                ", storeRedPacket=" + storeRedPacket +
                ", outRequestNo='" + outRequestNo + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
