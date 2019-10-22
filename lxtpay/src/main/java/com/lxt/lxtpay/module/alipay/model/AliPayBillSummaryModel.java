package com.lxt.lxtpay.module.alipay.model;


import com.lxt.lxtpay.module.alipay.util.AliPayBillConstants;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: AliPayBillSummaryModel
 * @Description: 每日对账单业务明细汇总模型类
 * @Author: lxt 
 * @Date: 2019-02-23 11:22
 * @Version 1.0
 **/
public class AliPayBillSummaryModel extends AliPayBillModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *交易订单总笔数
     */
    private Integer tradeSum;
    /**
     *退款订单总笔数
     */
    private Integer refundTradeSum;
    /**
     *实收净额（元）
     */
    private Double netProceeds;
    /**
     * @Title: build
     * @Description:  创建业务明细汇总模型实例
     * @Author: lxt 
     * @param: summaryArr
     * @param: titleMap
     * @Date: 2019-02-23 11:29 
     * @return: com.design.background.pay.alipay.model.AliPayBillSummaryModel
     * @throws: 
     */
    public AliPayBillSummaryModel build(String[] summaryArr, Map<Integer,String> titleMap){
        for (int i = 0; i < summaryArr.length; i++) {
            String str = summaryArr[i].trim();
            switch (titleMap.get(i)){
                case AliPayBillConstants.STRORE_CODE:
                    setStroreCode(str);
                    break;
                case AliPayBillConstants.STRORE_NAME:
                    setStroreName(str);
                    break;
                case AliPayBillConstants.TRADE_SUM:
                    this.tradeSum = Integer.valueOf(str);
                    break;
                case AliPayBillConstants.REFUND_TRADE_SUM:
                    this.refundTradeSum = Integer.valueOf(str);
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
                case AliPayBillConstants.NET_PROCEEDS:
                    this.netProceeds = Double.valueOf(str);
                    break;
                default:
            }
        }
        return this;
    }

    public Integer getTradeSum() {
        return tradeSum;
    }

    public AliPayBillSummaryModel setTradeSum(Integer tradeSum) {
        this.tradeSum = tradeSum;
        return this;
    }

    public Integer getRefundTradeSum() {
        return refundTradeSum;
    }

    public AliPayBillSummaryModel setRefundTradeSum(Integer refundTradeSum) {
        this.refundTradeSum = refundTradeSum;
        return this;
    }

    public Double getNetProceeds() {
        return netProceeds;
    }

    public AliPayBillSummaryModel setNetProceeds(Double netProceeds) {
        this.netProceeds = netProceeds;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayBillSummaryModel{" +
                "tradeSum=" + tradeSum +
                ", refundTradeSum=" + refundTradeSum +
                ", netProceeds=" + netProceeds +
                '}';
    }
}
