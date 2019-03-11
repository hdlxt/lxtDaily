package com.lxt.lxtpay.module.alipay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.lxt.lxtpay.common.CharConstants;
import com.lxt.lxtpay.common.FileConstants;
import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.model.RefundQueryModel;
import com.lxt.lxtpay.module.alipay.model.AliPayBillAllModel;
import com.lxt.lxtpay.module.alipay.model.AliPayTradeQueryModel;
import com.lxt.lxtpay.module.alipay.service.AliPayService;
import com.lxt.lxtpay.module.alipay.util.AliPayConstants;
import com.lxt.lxtpay.module.alipay.util.AliPayUtil;
import com.lxt.lxtpay.properties.AlipayProerties;
import com.lxt.lxtpay.service.PayCallbackService;
import com.lxt.lxtpay.utill.DesignFileUtils;
import com.lxt.lxtpay.utill.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.*;

/**
 * @ClassName: AliPayServiceImpl
 * @Description: 支付宝支付实现
 * @Author: lxt
 * @Date: 2019-02-21 12:31
 * @Version 1.0
 **/
@Service
public class AliPayServiceImpl implements AliPayService {
    private final static Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
    @Autowired
    private AlipayProerties alipayProerties;
    @Resource(name= PayConstants.ALI_PAY_CALLBACK)
    private PayCallbackService payCallbackService;
    /**
     * @Title: pcPay
     * @Description: pc支付方法 统一收单下单并支付页面接口
     * @Author: lxt
     * @param: productModel
     *      注：
     *      outTradeNo 【必填】 订单号　
     *      totalFee【必填】 订单金额
     * @Date: 2019-02-21 14:06
     * @return: java.lang.String
     * @throws:
     */
    @Override
    public ResultData<String> payPC(ProductModel productModel) {
        if(StringUtils.isBlank(productModel.getOutTradeNo()) || StringUtils.isBlank(productModel.getTotalFee())
            || StringUtils.isBlank(productModel.getSubject())){
            return ResultData.fail("订单信息不完整");
        }
        logger.info("支付宝PC支付下单开始，商品信息："+ productModel);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //前台通知
        alipayRequest.setReturnUrl(alipayProerties.getReturnUrl());
        //后台回调
        alipayRequest.setNotifyUrl(alipayProerties.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put(AliPayConstants.OUT_TRADE_NO, productModel.getOutTradeNo());
        bizContent.put(AliPayConstants.TOTAL_AMOUNT, productModel.getTotalFee());
        bizContent.put(AliPayConstants.SUBJECT, productModel.getSubject());
        bizContent.put(AliPayConstants.SELLER_ID, alipayProerties.getPid());
        //销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
        bizContent.put(AliPayConstants.PRODUCT_CODE, AliPayConstants.PRODUCT_CODE_VALUE);
        bizContent.put(AliPayConstants.BODY, productModel.getBody());
        bizContent.put(AliPayConstants.QR_PAY_MODE, AliPayConstants.QR_PAY_MODE_VALUE);
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        logger.info("业务参数:"+alipayRequest.getBizContent());
        String form = "";
        try {
            form = AliPayUtil.getAlipayClient().pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("支付宝构造表单失败",e);
            return ResultData.fail("支付宝构造表单失败");
        }
        return ResultData.oK(form);
    }
    /**
     * @Title: rsaCheckV1
     * @Description: 验证签名
     * @Author: lxt
     * @param: params
     * @Date: 2019-02-21 15:11
     * @return: boolean
     * @throws:
     */
    @Override
    public boolean rsaCheckV1(Map<String, String> params) {
        //验证签名 校验签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayProerties.getAlipayPublicKey(), CharConstants.UTF_8,alipayProerties.getSignType());
        } catch (AlipayApiException e) {
            logger.error("验证签名异常",e);
        }
        return signVerified;
    }
    /**
     * @Title: refund
     * @Description: 统一收单交易退款接口
     * @Author: lxt
     * @param: refundModel
     *      注：
     *      outTradeNo 【tradeNo和outTradeNo至少一个不为空】 订单号　
     *      tradeNo 【tradeNo和outTradeNo至少一个不为空】 支付宝交易号　
     *      outRequestNo【必填】
     * @Date: 2019-02-21 18:25
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws:
     */
    @Override
    public ResultData<String>  refund(RefundModel refundModel) {
        if(StringUtils.isBlank(refundModel.getOutTradeNo()) && StringUtils.isBlank(refundModel.getTradeNo())){
            logger.error("退款：订单号和 支付宝交易号不能同时为空");
            return ResultData.fail("订单号和 支付宝交易号不能同时为空");
        }
        if(StringUtils.isBlank(refundModel.getOutRequestNo())){
            logger.error("商户退款单号不可为空");
            return ResultData.fail("商户退款单号不可为空");
        }
        String orderNo = StringUtils.isBlank(refundModel.getOutTradeNo()) ? refundModel.getTradeNo() : refundModel.getOutTradeNo();
        logger.info("订单号："+orderNo+"支付宝退款");
        // 默认退款成功
        String  message = ResultData.CODE_SUCCESS;
        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        /**
         * 参数赋值
         */
        JSONObject bizContent = new JSONObject();
        if(StringUtils.isNoneBlank(refundModel.getOutTradeNo())){
            // 外部订单号，需要退款交易的商户外部订单号
            bizContent.put(AliPayConstants.OUT_TRADE_NO, refundModel.getOutTradeNo());
        }
        if(StringUtils.isNoneBlank(refundModel.getTradeNo())){
            // 支付宝交易号
            bizContent.put(AliPayConstants.TRADE_NO, refundModel.getTradeNo());
        }
        bizContent.put(AliPayConstants.REFUND_AMOUNT, refundModel.getRefundAmount());
        bizContent.put(AliPayConstants.REFUND_REASON, refundModel.getRefundReason());
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        bizContent.put(AliPayConstants.OUT_REQUEST_NO, refundModel.getOutRequestNo());
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        AlipayTradeRefundResponse response = null;
        try {
            response = AliPayUtil.getAlipayClient().execute(alipayRequest);
        } catch (AlipayApiException e) {
            logger.error("订单号："+ refundModel.getOutTradeNo()+"支付宝退款异常",e);
            return ResultData.fail("订单号："+ refundModel.getOutTradeNo()+"支付宝退款异常");
        }
        if(response.isSuccess()){
            logger.info("订单号："+ orderNo+"支付宝退款成功");
            return ResultData.oK("订单号："+ orderNo+"支付宝退款成功");
        } else {
            logger.error("订单号："+ orderNo+"支付宝退款失败，原因："+response.getSubMsg());
            return ResultData.fail("订单号："+ orderNo+"支付宝退款失败，原因："+response.getSubMsg());
        }
    }
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
    @Override
    public ResultData<String> downloadUrl(String billDate) {
        logger.info("获取对账单下载地址："+billDate);
        //默认退款成功
        String  message = ResultData.CODE_SUCCESS;
        // 创建获取对账单请求，设置请求参数
        AlipayDataDataserviceBillDownloadurlQueryRequest alipayRequest = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        /**
         * 参数赋值
         */
        JSONObject bizContent = new JSONObject();
        bizContent.put(AliPayConstants.BILL_TYPE, AliPayConstants.BILL_TYPE_TRADE);
        bizContent.put(AliPayConstants.BILL_DATE, billDate);
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
        try {
            response = AliPayUtil.getAlipayClient().execute(alipayRequest);
        } catch (AlipayApiException e) {
            logger.error("获取对账单下载地址异常："+billDate,e);
            return ResultData.fail("获取对账单下载地址异常");
        }
        if(response.isSuccess()){
            logger.info("获取对账单下载地址成功："+response.getBillDownloadUrl());
            return ResultData.oK().setData(response.getBillDownloadUrl());
        } else {
            logger.error("获取对账单下载地址失败："+response.getSubMsg());
            return ResultData.fail("获取对账单下载地址失败：+response.getSubMsg()");
        }
    }
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
    @Override
    public ResultData<AliPayTradeQueryModel> tradeQuery(String outTradeNo, String tradeNo) {
        AliPayTradeQueryModel aliPayTradeQueryModel = new AliPayTradeQueryModel();
        if(StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)){
            logger.error("交易查询失败：订单号和 支付宝交易号不能同时为空");
            ResultData.fail("交易查询失败：订单号和 支付宝交易号不能同时为空");
        }
        logger.info("交易查询："+outTradeNo == null ? tradeNo : outTradeNo);
        //默认退款成功
        String  message = ResultData.CODE_SUCCESS;
        // 创建获取对交易查询请求，设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        /**
         * 参数赋值
         */
        JSONObject bizContent = new JSONObject();
        if(StringUtils.isNoneBlank(outTradeNo)){
            bizContent.put(AliPayConstants.OUT_TRADE_NO,outTradeNo);
        }
        if(StringUtils.isNoneBlank(tradeNo)){
            bizContent.put(AliPayConstants.TRADE_NO, tradeNo);
        }
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        AlipayTradeQueryResponse response = null;
        try {
            response = AliPayUtil.getAlipayClient().execute(alipayRequest);
        } catch (AlipayApiException e) {
            return ResultData.fail("交易查询异常");
        }
        if(response.isSuccess()){
            logger.info("交易查询成功："+outTradeNo == null ? tradeNo : outTradeNo);
            return ResultData.oK().setData(aliPayTradeQueryModel.build(response));
        } else {
            logger.error("交易查询失败："+outTradeNo == null ? tradeNo : outTradeNo+";原因："+response.getSubMsg());
            return  ResultData.fail("交易查询失败："+outTradeNo == null ? tradeNo : outTradeNo+";原因："+response.getSubMsg());
        }
    }

    /**
     * @Title: refundQuery
     * @Description: 统一收单交易退款查询接口
     * @Author: lxt
     * @param: refundModel
     *      注：
     *      outTradeNo 【tradeNo和outTradeNo至少一个不为空】 订单号　
     *      tradeNo 支付宝交易号　
     *      outRequestNo【必填】
     * @Date: 2019-02-22 15:43
     * @return: com.design.background.model.ResultData<com.design.background.pay.model.RefundQueryModel>
     * @throws:
     */
    @Override
    public ResultData<List<RefundQueryModel>> refundQuery(RefundModel refundModel) {
        if(StringUtils.isBlank(refundModel.getOutTradeNo()) && StringUtils.isBlank(refundModel.getTradeNo())){
            logger.error("退款交易查询失败：订单号和 支付宝交易号不能同时为空");
            return  ResultData.fail("退款交易查询失败：订单号和 支付宝交易号不能同时为空");
        }
        if(StringUtils.isBlank(refundModel.getOutRequestNo())){
            logger.error("退款单号不可为空");
            return ResultData.fail("退款单号不可为空");
        }
        String orderNo = StringUtils.isBlank(refundModel.getOutTradeNo()) ? refundModel.getTradeNo() : refundModel.getOutTradeNo();
        logger.info("退款交易查询："+orderNo);
        //默认退款成功
        String  message = ResultData.CODE_SUCCESS;
        // 创建获取对退款查询请求，设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        /**
         * 参数赋值
         */
        JSONObject bizContent = new JSONObject();
        if(StringUtils.isNoneBlank(refundModel.getOutTradeNo())){
            bizContent.put(AliPayConstants.OUT_TRADE_NO,refundModel.getOutTradeNo());
        }
        if(StringUtils.isNoneBlank(refundModel.getTradeNo())){
            bizContent.put(AliPayConstants.TRADE_NO, refundModel.getTradeNo());
        }
        bizContent.put(AliPayConstants.OUT_REQUEST_NO, refundModel.getOutRequestNo());
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = AliPayUtil.getAlipayClient().execute(alipayRequest);
        } catch (AlipayApiException e) {
            logger.error("退款交易查询异常："+orderNo,e);
            return  ResultData.fail("退款交易查询异常");
        }
        if(response.isSuccess()){
            logger.info("退款交易查询成功："+orderNo);
            List<RefundQueryModel> refundQueryModelList = new ArrayList<>();
            refundQueryModelList.add(new RefundQueryModel().build(response));
            return  ResultData.oK().setData(refundQueryModelList);
        } else {
            return  ResultData.fail("退款交易查询失败："+orderNo+";原因："+response.getSubMsg());
        }
    }
    /**
     * @Title: closeTrade
     * @Description: 统一收单交易关闭
     * @Author: lxt
     * @param: outTradeNo  订单号　
     * @param: tradeNo  支付宝交易号　
     *      注：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     *          trade_no,out_trade_no如果同时存在优先取trade_no
     * @param: operatorId【可选】  卖家端自定义的的操作员 ID　
     * @Date: 2019-02-22 16:22
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws:
     */
    @Override
    public ResultData<String> closeTrade(String outTradeNo, String tradeNo,String operatorId) {
        if(StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)){
            logger.error("交易查询失败：订单号和 支付宝交易号不能同时为空");
            return  ResultData.fail("订单号和 支付宝交易号不能同时为空");
        }
        logger.info("交易关闭："+outTradeNo == null ? tradeNo : outTradeNo);
        //默认退款成功
        String  message = ResultData.CODE_SUCCESS;
        // 创建获取对交易查询请求，设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        /**
         * 参数赋值
         */
        JSONObject bizContent = new JSONObject();
        if(StringUtils.isNoneBlank(outTradeNo)){
            bizContent.put(AliPayConstants.OUT_TRADE_NO,outTradeNo);
        }
        if(StringUtils.isNoneBlank(tradeNo)){
            bizContent.put(AliPayConstants.TRADE_NO, tradeNo);
        }
        if(StringUtils.isNoneBlank(operatorId)){
            bizContent.put(AliPayConstants.OPERATOR_ID, operatorId);
        }
        String biz = bizContent.toString();
        alipayRequest.setBizContent(biz);
        AlipayTradeCloseResponse response = null;
        try {
            response = AliPayUtil.getAlipayClient().execute(alipayRequest);
        } catch (AlipayApiException e) {
            logger.error("交易关闭异常："+outTradeNo == null ? tradeNo : outTradeNo,e);
            return  ResultData.fail("交易关闭异常");
        }
        if(response.isSuccess()){
            logger.info("交易关闭成功："+outTradeNo == null ? tradeNo : outTradeNo);
            return  ResultData.oK("交易关闭成功");
        } else {
            logger.error("交易关闭失败："+outTradeNo == null ? tradeNo : outTradeNo+";原因："+response.getSubMsg());
            return  ResultData.fail("交易关闭失败："+outTradeNo == null ? tradeNo : outTradeNo+";原因："+response.getSubMsg());
        }
    }
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
    @Override
    public ResultData<AliPayBillAllModel> getBillAllModel(String billDate, String... types){
        ResultData<String> resultData = downloadUrl(billDate);
        if(resultData.isFail()){
            return ResultData.fail(resultData.getMessage());
        }
        //获取对账单压缩包
        File file = DesignFileUtils.downloadByUrlPath(resultData.getData(), FileConstants.DOWNLOAD, Utility.getRandomStrByNum(6)+FileConstants.SUFFIX_ZIP);
        if(file == null){
         return ResultData.fail("下载对账单失败");
        }
        return AliPayUtil.readCvsZipFile(file,types);
    }
    /**
     * @Title: notifyUrl
     * @Description: 支付宝支付结束后台回调验签&业务处理方法
     * @Author: lxt
     * @param: request
     * @param: response
     * @Date: 2019-03-11 09:56
     * @throws:
     */
    @Override
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String  message = "success";
        Map<String, String> params = new HashMap<String, String>(20);
        // 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        //验证签名 校验签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayProerties.getAlipayPublicKey(), alipayProerties.getCharSet(), alipayProerties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            message =  "failed";
        }
        if (signVerified) {
            logger.info("支付宝验证签名成功！");
            // 若参数中的appid和填入的appid不相同，则为异常通知
            if (!alipayProerties.getAppId().equals(params.get(AliPayConstants.APP_ID))) {
                logger.info("与付款时的appid不同，此为异常通知，应忽略！");
                message =  "failed";
            }else{
                String outTradeNo = params.get(AliPayConstants.OUT_TRADE_NO);
                //!!!在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
                String status = params.get(AliPayConstants.TRADE_STATUS);
                logger.info("订单号：{}，订单状态：{}",outTradeNo,status);
                if (status.equals(AliPayConstants.WAIT_BUYER_PAY)) {
                    // 如果状态是正在等待用户付款
                    logger.info(outTradeNo + "订单的状态正在等待用户付款");
                } else if (status.equals(AliPayConstants.TRADE_CLOSED)) {
                    // 如果状态是未付款交易超时关闭，或支付完成后全额退款
                    logger.info(outTradeNo + "订单的状态已经关闭");
                } else if (status.equals(AliPayConstants.TRADE_SUCCESS) || status.equals(AliPayConstants.TRADE_FINISHED)) {
                    // 如果状态是已经支付成功
                    logger.info("(支付宝订单号:"+outTradeNo+"付款成功)");
                    /**
                     * 在支付回调方法中写业务
                     */
                    payCallbackService.payCallback(new PayCallbackModel());
                } else {

                }
            }
        } else { // 如果验证签名没有通过
            message =  "failed";
            logger.info("验证签名失败！");
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(message.getBytes());
        out.flush();
        out.close();
    }
    /**
     * @Title: returnUrl
     * @Description:  支付宝支付结束 前台回调验签跳转 支付结果页面方法
     * @Author: lxt 
     * @param: model
     * @param: request
     * @param: request
     * @param: successUrl
     * @Date: 2019-03-11 09:57
     * @return: java.lang.String
     * @throws: 
     */
    @Override
    public String  returnUrl(Model model, HttpServletRequest request, String successUrl){
        model.addAttribute("result","支付失败");
        try {
            //获取支付宝GET过来反馈信息
            Map<String,String> params = new HashMap<String,String>(20);
            Map<String,String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(CharConstants.ISO_8859_1), CharConstants.UTF_8);
                params.put(name, valueStr);
            }
            //商户订单号
            String orderNo = new String(request.getParameter(AliPayConstants.OUT_TRADE_NO).getBytes(CharConstants.ISO_8859_1),CharConstants.UTF_8);
            //前台回调验证签名 v1 or v2
            boolean signVerified = rsaCheckV1(params);
            if(signVerified) {
                logger.info("订单号"+orderNo+"验证签名结果[成功].");
                /**
                 * !!!!!!处理业务逻辑
                 */
                model.addAttribute("result","支付成功");
            }else {
                logger.info("订单号"+orderNo+"验证签名结果[失败].");
            }
        } catch (Exception e) {
            logger.error("支付宝支付PC端前台回调异常",e);
        }

        //支付成功、跳转到成功页面
        return successUrl;
    }
}
