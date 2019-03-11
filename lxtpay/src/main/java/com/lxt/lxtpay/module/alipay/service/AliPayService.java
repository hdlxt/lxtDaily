package com.lxt.lxtpay.module.alipay.service;


import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.model.RefundQueryModel;
import com.lxt.lxtpay.module.alipay.model.AliPayBillAllModel;
import com.lxt.lxtpay.module.alipay.model.AliPayTradeQueryModel;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AliPayService
 * @Description: 支付宝支付Service接口
 * @Author: lxt
 * @Date: 2019-02-21 12:30
 * @Version 1.0
 **/
public interface AliPayService {
    /**
     * @Title: pcPay
     * @Description: pc支付方法 统一收单下单并支付页面接口
     * @Author: lxt 
     * @param: productModel
     *      注：
     *      outTradeNo 【必填】 订单号　
     *      totalFee【必填】 订单金额 单位元
     * @Date: 2019-02-21 14:06
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws: 
     */
    ResultData<String> payPC(ProductModel productModel);
    /**
     * @Title: rsaCheckV1
     * @Description: 验证签名
     * @Author: lxt
     * @param: params
     * @Date: 2019-02-21 15:11 
     * @return: boolean
     * @throws: 
     */
    boolean rsaCheckV1(Map<String, String> params);
    /**
     * @Title: refund
     * @Description: 统一收单交易退款接口
     * @Author: lxt
     * @param: refundModel
     *      注：
     *      outTradeNo 【tradeNo和outTradeNo至少一个不为空】 订单号　
     *      tradeNo  支付宝交易号　
     *      outRequestNo【必填】
     * @Date: 2019-02-21 18:25
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws: 
     */
    ResultData<String>  refund(RefundModel refundModel);
    /**
     * @Title: downloadUrl
     * @Description: 查询对账单下载地址 注：不可获取当日或当月的对账单地址，会返回【入参不合法】
     * @Author: lxt
     * @param: billDate 【必填】 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     * @Date: 2019-02-22 13:55
     * @return: com.design.background.model.ResultData<java.lang.String>
     *     成功返回下载地址：账单下载地址链接，获取连接后【30秒】后未下载，链接地址失效。
     *     失败返回失败信息
     * @throws: 
     */
    ResultData<String> downloadUrl(String billDate);
    /**
     * @Title: tradeQuery
     * @Description: 统一收单线下交易查询
     * @Author: lxt
     * @param: outTradeNo 订单号
     * @param: tradeNo 支付宝交易号
     *      注：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     *          trade_no,out_trade_no如果同时存在优先取trade_no
     * @Date: 2019-02-22 14:51
     * @return: com.design.background.model.ResultData<com.design.background.pay.alipay.model.AliPayTradeQueryModel>
     * @throws: 
     */
    ResultData<AliPayTradeQueryModel> tradeQuery(String outTradeNo, String tradeNo);
    /**
     * @Title: refundQuery
     * @Description: 统一收单交易退款查询接口
     * @Author: lxt
     * @param: refundModel
     *      注：
     *      outTradeNo 【tradeNo和outTradeNo至少一个不为空】 订单号　
     *      tradeNo支付宝交易号　
     *      outRequestNo【必填】
     * @Date: 2019-02-22 15:43
     * @return: com.design.background.model.ResultData<com.design.background.pay.model.RefundQueryModel>
     * @throws: 
     */
    ResultData<List<RefundQueryModel>> refundQuery(RefundModel refundModel);
    /**
     * @Title: closeTrade
     * @Description: 统一收单交易关闭
     * @Author: lxt
     * @param: outTradeNo 订单号　
     * @param: tradeNo 支付宝交易号　
     *      注：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     *          trade_no,out_trade_no如果同时存在优先取trade_no
     * @param: operatorId【可选】  卖家端自定义的的操作员 ID　
     * @Date: 2019-02-22 16:22
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws: 
     */
    ResultData<String>  closeTrade(String outTradeNo, String tradeNo, String operatorId);
    /**
     * @Title: getBillAllModel
     * @Description: 分析对账单内容
     * @Author: lxt
     * @param: zipUrl【必填】
     * @param: type 【可选，默认为所有】
     *         AlipayAccountConstants.TYPE_DETAIL 获取业务明细列表信息
     *         AlipayAccountConstants.TYPE_DETAIL_SUMARRY 获取业务明细列表结束后的汇总信息
     *         AlipayAccountConstants.TYPE_SUMARRY 获取业务明细汇总表的信息
     *         AlipayAccountConstants.TYPE_ALL 获取所有信息(默认)
     * @Date: 2019-02-23 16:48
     * @return: com.design.background.model.ResultData<com.design.background.pay.alipay.model.AliPayBillAllModel>
     * @throws: 
     */
    ResultData<AliPayBillAllModel> getBillAllModel(String billDate, String... types);
    /**
     * @Title: notifyUrl
     * @Description: 支付宝支付成功后台回调验签&业务处理方法
     * @Author: lxt
     * @param: request
     * @param: response
     * @Date: 2019-03-11 09:56
     * @throws:
     */
    void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception;
    /**
     * @Title: returnUrl
     * @Description:  支付宝支付结束 前台回调验签跳转 支付结果页面方法
     * @Author: lxt
     * @param: model
     * @param: request
     * @param: successUrl
     * @Date: 2019-03-11 09:57
     * @return: java.lang.String
     * @throws:
     */
    String  returnUrl(Model model, HttpServletRequest request, String successUrl);
}
