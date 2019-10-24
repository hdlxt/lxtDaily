package com.lxt.lxtpay.module.wxpay.service.impl;

import com.lxt.lxtpay.common.FileConstants;
import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.model.RefundQueryModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayBillAllModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayTradeQueryModel;
import com.lxt.lxtpay.module.wxpay.sdk.WXPay;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayConstants;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayProerties;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayUtil;
import com.lxt.lxtpay.module.wxpay.service.WxPayService;
import com.lxt.lxtpay.module.wxpay.util.WxPayBillConstants;
import com.lxt.lxtpay.service.PayCallbackService;
import com.lxt.lxtpay.utill.DesignFileUtils;
import com.lxt.lxtpay.utill.PayUtil;
import com.lxt.lxtpay.utill.ZxingUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: WxPayServiceImpl
 * @Description: 微信支付实现
 * @Author: lxt
 * @Date: 2019-02-25 16:38
 * @Version 1.0
 **/
@Service
public class WxPayServiceImpl implements WxPayService {
    private final static Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);
    @Autowired
    private WXPayProerties wxPayProerties;
    @Resource(name= PayConstants.WX_PAY_CALLBACK)
    private PayCallbackService payCallbackService;
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
    @Override
    public ResultData<String> payPC(ProductModel product) {
        if(StringUtils.isBlank(product.getOutTradeNo()) || StringUtils.isBlank(product.getTotalFee())
                || StringUtils.isBlank(product.getSubject())){
            return ResultData.fail("订单信息不完整");
        }
        logger.info("订单号：{}生成微信支付码",product.getOutTradeNo());
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
//            WXPay wxpay = new WXPay(wxPayProerties, false,true);
            Map<String, String> data = new HashMap<String, String>(10);
            data.put("body",product.getSubject());
            data.put("out_trade_no",product.getOutTradeNo());
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");
            data.put("total_fee", PayUtil.handleTotalFee(product.getTotalFee()));
            data.put("notify_url", wxPayProerties.getNotifyUrl());
            // 此处指定为扫码支付
            data.put("trade_type", "NATIVE");
            // 统一下单
            Map<String, String> map = wxpay.unifiedOrder(data);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RESULT_CODE))){
                // 获取二维码暂存路径
                String qrCodePath = DesignFileUtils.getQrCodePath();
                String qrCodeImgPath = qrCodePath + File.separator + product.getOutTradeNo()+ FileConstants.SUFFIX_png;
                // 生成微信支付二维码
                File file = ZxingUtils.getQRCodeImge(map.get(WXPayConstants.CODE_URL),qrCodeImgPath);
                if(file.exists()){
                    logger.info("订单号：{}生成微信支付码成功",product.getOutTradeNo());
                    return ResultData.oK(".."+File.separator+FileConstants.QRCODE+File.separator+product.getOutTradeNo()+ FileConstants.SUFFIX_png)
                            .setData(PayConstants.PAY_TYPE_WXPAY);
                }else{
                    logger.info("订单号：{}生成微信支付码图片失败",product.getOutTradeNo());
                    return ResultData.fail("生成微信支付码图片失败");
                }
            }else{
                logger.info("订单号：{}生成微信支付码失败",product.getOutTradeNo());
                return ResultData.fail("生成微信支付码失败");
            }
        } catch (Exception e) {
            logger.error("生成微信支付码",e);
            return ResultData.fail("生成微信支付码异常");
        }
    }
    /**
     * @Title: payCallback
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt 
     * @param: notifyData
     * @Date: 2019-02-26 13:44 
     * @return: java.lang.String
     * @throws: 
     */
    @Override
    public String payCallback(String notifyData) {
        logger.info("payCallback() start, notifyData={}", notifyData);
        String xmlBack="";
        Map<String, String> notifyMap = null;
        try {
            WXPay wxpay = new WXPay(wxPayProerties);

            notifyMap = WXPayUtil.xmlToMap(notifyData);
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                logger.info("微信支付回调验签成功成功");
                // 签名正确
                // 进行处理。
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                // 状态
                String returnCode = notifyMap.get(WXPayConstants.RESULT_CODE);
                // 订单号
                String outTradeNo = notifyMap.get(WXPayConstants.OUT_TRADE_NO);
                if (StringUtils.isBlank(outTradeNo)) {
                    logger.info("微信支付回调失败订单号: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
                if (WXPayConstants.FAIL.equals(returnCode)) {
                    logger.info("微信支付回调返回失败结果: {}", notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[失败结果]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
                /**
                 * 业务逻辑处理 ****************************
                 */
                logger.info("微信支付回调成功订单号: {}", notifyMap);
                payCallbackService.payCallback(new PayCallbackModel().setData(notifyMap));
                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                return xmlBack;
            } else {
                logger.error("微信支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[签名错误]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            logger.error("微信支付回调通知失败",e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[通知失败]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }
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
    @Override
    public ResultData<WxPayTradeQueryModel> orderQuery(String transactionId, String outTradeNo) {
        if(StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)){
            logger.error("微信订单号和商户订单号不能同时为空");
            return ResultData.fail("微信订单号和商户订单号不能同时为空");
        }
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
            Map<String, String> data = new HashMap<String, String>(5);
            if(StringUtils.isNoneBlank(transactionId)){
                data.put(WXPayConstants.TRANSACTION_ID, transactionId);
            }
            if(StringUtils.isNoneBlank(outTradeNo)){
                data.put(WXPayConstants.OUT_TRADE_NO, outTradeNo);
            }
            Map<String, String> map = wxpay.orderQuery(data);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RESULT_CODE))){
                logger.info("查询订单成功:{}",map);
                return ResultData.oK().setData(new WxPayTradeQueryModel().build(map));
            }else{
                logger.error("未查询到订单:{}",map.get(WXPayConstants.RETURN_MSG));
                return ResultData.fail("未查询到订单");
            }
        } catch (Exception e) {
            logger.error("查询订单异常");
            return ResultData.fail("查询订单异常");
        }
    }
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
    @Override
    public ResultData<String> closeOrder(String outTradeNo) {
        if(StringUtils.isBlank(outTradeNo)){
            logger.error("商户订单号不能为空");
            return ResultData.fail("商户订单号不能为空");
        }
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
            Map<String, String> data = new HashMap<String, String>(2);
            data.put(WXPayConstants.OUT_TRADE_NO, outTradeNo);
            Map<String, String> map = wxpay.closeOrder(data);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RESULT_CODE))){
                logger.info("关闭订单{}成功:{}",outTradeNo,map);
                return ResultData.oK("关闭成功");
            }else{
                logger.error("关闭订单{}失败:{}",outTradeNo,WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
                return ResultData.fail(WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
            }
        } catch (Exception e) {
            logger.error("查询订单异常");
            return ResultData.fail("查询订单异常");
        }
    }
    /**
     * @Title: refund
     * @Description: 退款接口
     * @Author: lxt
     * @param: refundModel
     *  注：
     *      outTradeNo和tradeNo至少一个不为空
     *      outRequestNo【可选】 退款单号
     *      refundAmount【必填】 退款金额
     *      totalAmount【必填】 订单金额
     * @Date: 2019-02-26 15:33
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws:
     */
    @Override
    public ResultData<String> refund(RefundModel refundModel) {
        if(StringUtils.isBlank(refundModel.getOutTradeNo()) && StringUtils.isBlank(refundModel.getTradeNo())){
            logger.error("微信订单号和商户订单号不能同时为空");
            return ResultData.fail("微信订单号和商户订单号不能同时为空");
        }
        if(StringUtils.isBlank(refundModel.getTotalAmount())){
            logger.error("订单金额不可为空");
            return ResultData.fail("订单金额不可为空");
        }
        if(StringUtils.isBlank(refundModel.getRefundAmount())){
            logger.error("退款金额不可为空");
            return ResultData.fail("退款金额不可为空");
        }
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
            Map<String, String> data = new HashMap<String, String>(10);
            if(StringUtils.isNoneBlank(refundModel.getOutTradeNo())){
                data.put(WXPayConstants.OUT_TRADE_NO, refundModel.getOutTradeNo());
            }
            if(StringUtils.isNoneBlank(refundModel.getTradeNo())){
                data.put(WXPayConstants.TRANSACTION_ID, refundModel.getTradeNo());
            }
            data.put(WXPayConstants.OUT_REFUND_NO, refundModel.getOutRequestNo());
            data.put(WXPayConstants.TOTAL_FEE, PayUtil.handleTotalFee(refundModel.getTotalAmount()));
            data.put(WXPayConstants.REFUND_FEE, PayUtil.handleTotalFee(refundModel.getRefundAmount()));
            Map<String, String> map = wxpay.refund(data);
            logger.info("退款结果:{}",map);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RESULT_CODE))){
                return ResultData.oK("退款成功");
            }else{
                logger.error("退款失败:{}",WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
                return ResultData.fail(WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
            }
        } catch (Exception e) {
            logger.error("退款异常",e);
            return ResultData.fail("退款异常");
        }
    }
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
    @Override
    public ResultData<List<RefundQueryModel>> refundQuery(RefundModel refundModel) {
        if(StringUtils.isBlank(refundModel.getOutTradeNo()) && StringUtils.isBlank(refundModel.getTradeNo())){
            logger.error("退款交易查询失败：微信订单号和商户订单号不能同时为空");
            return  ResultData.fail("退款交易查询失败：微信订单号和商户订单号不能同时为空");
        }
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
            Map<String, String> data = new HashMap<String, String>(5);
            if(StringUtils.isNoneBlank(refundModel.getOutTradeNo())){
                data.put(WXPayConstants.OUT_TRADE_NO, refundModel.getOutTradeNo());
            }
            if(StringUtils.isNoneBlank(refundModel.getTradeNo())){
                data.put(WXPayConstants.TRANSACTION_ID, refundModel.getTradeNo());
            }
            if(StringUtils.isNoneBlank(refundModel.getOutRequestNo())){
                data.put(WXPayConstants.OUT_REFUND_NO, refundModel.getOutRequestNo());
            }
            if(StringUtils.isNoneBlank(refundModel.getRefundId())){
                data.put(WXPayConstants.REFUND_ID, refundModel.getRefundId());
            }
            Map<String, String> map = wxpay.refundQuery(data);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RESULT_CODE))){
                logger.info("退款查询成功:{}",map);
                int refundCount = Integer.valueOf(map.get(WXPayConstants.REFUND_COUNT));
                List<RefundQueryModel> refundQueryModelList = new ArrayList<>();
                for (int i = 0; i < refundCount; i++) {
                    refundQueryModelList.add(new RefundQueryModel().build(map,i));
                }
                return ResultData.oK().setData(refundQueryModelList);
            }else{
                logger.error("退款查询失败:{}",WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
                return ResultData.fail(WXPayConstants.ERR_DES_MAP.get(map.get(WXPayConstants.ERR_CODE)));
            }
        } catch (Exception e) {
            logger.error("退款交易查询异常",e);
            return ResultData.fail("退款交易查询异常");
        }
    }
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
    @Override
    public ResultData<WxPayBillAllModel> downloadBill(String billDate) {
        if(StringUtils.isBlank(billDate)){
            logger.error("下载对账单的日期");
            return ResultData.fail("下载对账单的日期");
        }
        try {
            WXPay wxpay = new WXPay(wxPayProerties);
            Map<String, String> data = new HashMap<String, String>(3);
            data.put(WXPayConstants.BILL_DATE, billDate);
            data.put(WXPayConstants.BILL_TYPE, WXPayConstants.BILL_TYPE_ALL);
            Map<String, String> map = wxpay.downloadBill(data);
            if(WXPayConstants.SUCCESS.equals(map.get(WXPayConstants.RETURN_CODE))){
                logger.info("对账单下载成功");
                return ResultData.oK().setData(WXPayUtil.analysisBillData(map.get(WxPayBillConstants.BILL_DATA)));
            }else{
                logger.error("对账单下载失败:{}",map);
                return ResultData.fail("对账单下载失败").setData(map);
            }
        } catch (Exception e) {
            logger.error("对账单下载异常",e);
            return ResultData.fail("对账单下载异常");
        }
    }

    /**
     * @Title: payCallback
     * @Description: 支付成功 后台回调 验签 修改订单状态
     * @Author: lxt
     * @param: request
     * @param: response
     * @Date: 2019-03-11 10:00
     * @throws:
     */
    @Override
    public void payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("进入微信支付异步通知");
        String resXml="";
        try{
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            logger.info("微信支付异步通知请求包: {}", resXml);
            resXml = payCallback(resXml);
        }catch (Exception e){
            logger.error("微信支付回调通知异常",e);
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[通知异常]]></return_msg>" + "</xml> ";
        }
        // ------------------------------
        // 处理业务完毕
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
