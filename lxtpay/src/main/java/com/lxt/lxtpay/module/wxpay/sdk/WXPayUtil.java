package com.lxt.lxtpay.module.wxpay.sdk;

import com.lxt.lxtpay.module.wxpay.model.WPayBillDetailSummaryModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayBillAllModel;
import com.lxt.lxtpay.module.wxpay.model.WxPayBillDetailModel;
import com.lxt.lxtpay.module.wxpay.util.WxPayBillConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayConstants.SignType;

public class WXPayUtil {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            WXPayUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }


    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, SignType.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put(WXPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, SignType.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }


    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 日志
     * @return
     */
    public static Logger getLogger() {
        Logger logger = LoggerFactory.getLogger("wxpay java sdk");
        return logger;
    }

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * @Title: analysisBillData
     * @Description: 解析对账单数据
     *  第一行为表头: 27列
     *      交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行
     *      ,货币种类,总金额,代金券或立减优惠金额,微信退款单号,商户退款单号,退款金额, 代金券或立减优惠退款金额
     *      ，退款类型，退款状态,商品名称,商户数据包,手续费,费率
     *  从第二行起，为数据记录，各参数以逗号分隔，参数前增加`符号，为标准键盘1左边键的字符，字段顺序与表头一致。
     *  最后一行为统计数据： 7列
     *      总交易单数，总交易额，总退款金额，总代金券或立减优惠退款金额，手续费总金额
     * @Author: lxt
     * @param: data
     * @Date: 2019-02-27 11:17
     * @return: com.design.background.pay.wxpay.model.WXPayBillAllModel
     * @throws:
     */
    public static WxPayBillAllModel analysisBillData(String data){
        if(StringUtils.isBlank(data)){
            return null;
        }
        WxPayBillAllModel wxPayBillAllModel = new WxPayBillAllModel();
        WPayBillDetailSummaryModel wPayBillDetailSummaryModel = new WPayBillDetailSummaryModel();
        List<WxPayBillDetailModel> wxPayBillDetailModelList = new ArrayList<>();
        String[] dataArr = data.replaceAll("\r\n",",")
                .replaceAll("`","")
                .split(",");
        Map<String,Integer> detailTitleMap = new HashMap<>();
        Map<String,Integer> summarylTitleMap = new HashMap<>();
        // 获取明细表头数据
        for (int i = 0; i < WxPayBillConstants.BILL_CLOUNM_COUNT; i++) {
            detailTitleMap.put(dataArr[i],i);
        }
        // 获取统计表头数据
        for (int i = dataArr.length - WxPayBillConstants.BILL_SUMMARY_CLOUNM_COUNT*2; i < dataArr.length - WxPayBillConstants.BILL_SUMMARY_CLOUNM_COUNT; i++) {
            summarylTitleMap.put(dataArr[i],i);
        }
        int count = 0;
        wxPayBillDetailModelList.add(new WxPayBillDetailModel());
        //获取明细数据：
        for (int i = WxPayBillConstants.BILL_CLOUNM_COUNT; i < dataArr.length - WxPayBillConstants.BILL_SUMMARY_CLOUNM_COUNT * 2; i++) {
            if(i > WxPayBillConstants.BILL_CLOUNM_COUNT && i % WxPayBillConstants.BILL_CLOUNM_COUNT == 0){
                count++;
                wxPayBillDetailModelList.add(new WxPayBillDetailModel());
            }
            switch (i % WxPayBillConstants.BILL_CLOUNM_COUNT){
                case 0:
                    wxPayBillDetailModelList.get(count).setTradeTime(dataArr[i]);
                    break;
                case 1:
                    wxPayBillDetailModelList.get(count).setAppId(dataArr[i]);
                    break;
                case 2:
                    wxPayBillDetailModelList.get(count).setMchId(dataArr[i]);
                    break;
                case 3:
                    wxPayBillDetailModelList.get(count).setSubMchId(dataArr[i]);
                    break;
                case 4:
                    wxPayBillDetailModelList.get(count).setDeviceInfo(dataArr[i]);
                    break;
                case 5:
                    wxPayBillDetailModelList.get(count).setTransactionId(dataArr[i]);
                    break;
                case 6:
                    wxPayBillDetailModelList.get(count).setOutTradeNo(dataArr[i]);
                    break;
                case 7:
                    wxPayBillDetailModelList.get(count).setOpenId(dataArr[i]);
                    break;
                case 8:
                    wxPayBillDetailModelList.get(count).setTradeType(dataArr[i]);
                    break;
                case 9:
                    wxPayBillDetailModelList.get(count).setTradeState(dataArr[i]);
                    break;
                case 10:
                    wxPayBillDetailModelList.get(count).setBankType(dataArr[i]);
                    break;
                case 11:
                    wxPayBillDetailModelList.get(count).setFeeType(dataArr[i]);
                    break;
                case 12:
                    wxPayBillDetailModelList.get(count).setSettlementTotalFee(dataArr[i]);
                    break;
                case 13:
                    wxPayBillDetailModelList.get(count).setCouponFee(dataArr[i]);
                    break;
                case 14:
                    wxPayBillDetailModelList.get(count).setRefundId(dataArr[i]);
                    break;
                case 15:
                    wxPayBillDetailModelList.get(count).setOutRefundNo(dataArr[i]);
                    break;
                case 16:
                    wxPayBillDetailModelList.get(count).setRefundFee(dataArr[i]);
                    break;
                case 17:
                    wxPayBillDetailModelList.get(count).setRechargeCouponRefundFee(dataArr[i]);
                    break;
                case 18:
                    wxPayBillDetailModelList.get(count).setRefundType(dataArr[i]);
                    break;
                case 19:
                    wxPayBillDetailModelList.get(count).setRefundStatus(dataArr[i]);
                    break;
                case 20:
                    wxPayBillDetailModelList.get(count).setTradeName(dataArr[i]);
                    break;
                case 21:
                    wxPayBillDetailModelList.get(count).setTradeDataPack(dataArr[i]);
                    break;
                case 22:
                    wxPayBillDetailModelList.get(count).setServiceCharge(dataArr[i]);
                    break;
                case 23:
                    wxPayBillDetailModelList.get(count).setRate(dataArr[i]);
                    break;
                case 24:
                    wxPayBillDetailModelList.get(count).setTotalFee(dataArr[i]);
                    break;
                case 25:
                    wxPayBillDetailModelList.get(count).setApplyRefundFee(dataArr[i]);
                    break;
                case 26:
                    wxPayBillDetailModelList.get(count).setRateRemark(dataArr[i]);
                    break;
                default:
            }
        }
        //获取统计汇总数据：
        for (int i = dataArr.length - WxPayBillConstants.BILL_SUMMARY_CLOUNM_COUNT; i < dataArr.length; i++) {
            switch (i-(dataArr.length - WxPayBillConstants.BILL_SUMMARY_CLOUNM_COUNT)){
                case 0:
                    wPayBillDetailSummaryModel.setTradeSum(Double.valueOf(dataArr[i]));
                    break;
                case 1:
                    wPayBillDetailSummaryModel.setPayableTradeFeeSum(Double.valueOf(dataArr[i]));
                    break;
                case 2:
                    wPayBillDetailSummaryModel.setRefundFeeSum(Double.valueOf(dataArr[i]));
                    break;
                case 3:
                    wPayBillDetailSummaryModel.setRechargeCouponRefundFeeSum(Double.valueOf(dataArr[i]));
                    break;
                case 4:
                    wPayBillDetailSummaryModel.setServiceChargeSum(Double.valueOf(dataArr[i]));
                    break;
                case 5:
                    wPayBillDetailSummaryModel.setTradeFeeSum(Double.valueOf(dataArr[i]));
                    break;
                case 6:
                    wPayBillDetailSummaryModel.setApplyRefundFeeSum(Double.valueOf(dataArr[i]));
                    break;
                default:
            }
        }
        return wxPayBillAllModel.setWxPayBillDetailModelList(wxPayBillDetailModelList)
                .setwPayBillDetailSummaryModel(wPayBillDetailSummaryModel);
    }
}
