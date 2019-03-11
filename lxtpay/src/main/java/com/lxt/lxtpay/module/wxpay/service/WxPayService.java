package com.lxt.lxtpay.module.wxpay.service;


import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.model.RefundQueryModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayBillAllModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayTradeQueryModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: WxPayService
 * @Description: 微信支付接口
 * @Author: lxt
 * @Date: 2019-02-25 16:38
 * @Version 1.0
 **/
public interface WxPayService {
    /**
     * @Title: payPC
     * @Description: 电脑网站支付
     * @Author: lxt 
     * @param: productModel
     *      注：
     *          outTradeNo【必填】商户订单号
     *          totalFee【必填】订单金额 单位：元
     * @Date: 2019-02-25 16:44
     * @return: com.design.background.model.ResultData
     * @throws: 
     */
    ResultData<String> payPC(ProductModel product);
    /**
     * @Title: payCallback
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt
     * @param: notifyData
     * @Date: 2019-02-26 13:44
     * @return: java.lang.String
     * @throws:
     */
    String payCallback(String notifyData);
    /**
     * @Title: orderQuery
     * @Description: 查询订单
     * @Author: lxt 
     * @param: transactionId 微信的订单号，建议优先使用
     * @param: outTradeNo 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     *      注：【transactionId和outTradeNo至少一个不为空】
     * @Date: 2019-02-26 14:46 
     * @return: com.design.background.model.ResultData<com.design.background.pay.wxpay.model.WXPayTradeQueryModel>
     * @throws: 
     */
    ResultData<WxPayTradeQueryModel> orderQuery(String transactionId, String outTradeNo);
    /**
     * @Title: closeOrder
     * @Description: 关闭订单
     *  以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     *  系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * @Author: lxt 
     * @param: outTradeNo【必填】 商户订单号
     * @Date: 2019-02-26 15:05 
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws: 
     */
    ResultData<String> closeOrder(String outTradeNo);
   /**
    * @Title: refund
    * @Description: 退款接口
    * @Author: lxt 
    * @param: refundModel
    *  注：
    *      outTradeNo和tradeNo至少一个不为空
    *      outRequestNo【可选】 退款单号
    *      refundAmount【必填】 订单金额
    * @Date: 2019-02-26 15:33
    * @return: com.design.background.model.ResultData<java.lang.String>
    * @throws: 
    */
    ResultData<String>  refund(RefundModel refundModel);
    /**
     * @Title: refundQuery
     * @Description: 退款查询接口
     * @Author: lxt
     * @param: refundModel
     *  注：
     *      outTradeNo和tradeNo至少一个不为空
     *      outRequestNo【可选】
     *      refundId【可选】
     * @Date: 2019-02-26 15:33
     * @return: com.design.background.model.ResultData<java.util.List<com.design.background.pay.model.RefundQueryModel>>
     * @throws: 
     */
    ResultData<List<RefundQueryModel>> refundQuery(RefundModel refundModel);
    /**
     * @Title: downloadBill
     * @Description: 下载对账单 下载对账单的日期，格式：20140603
     *  商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     *  微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * @Author: lxt
     * @param: billDate【必填】 下载对账单的日期，格式：yyyyMMdd eg:20180101
     * @Date: 2019-02-26 16:23
     * @return: com.design.background.model.ResultData<com.design.background.pay.wxpay.model.WXPayBillAllModel>
     * @throws: 
     */
    ResultData<WxPayBillAllModel> downloadBill(String billDate);
    /**
     * @Title: payCallback
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt
     * @param: request
     * @param: response
     * @Date: 2019-03-11 10:00
     * @throws:
     */
    void payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
