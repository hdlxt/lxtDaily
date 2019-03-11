package com.lxt.lxtpay.module.wxpay.sdk;


import java.util.HashMap;
import java.util.Map;
import com.lxt.lxtpay.module.wxpay.sdk.WXPayConstants.SignType;
import org.apache.http.client.HttpClient;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";

    /**
     * API返回状态码 key和value
     */
    public static final String RESULT_CODE = "result_code";
    public static final String RETURN_MSG = "return_msg";
    public static final String RETURN_CODE = "return_code";
    /**
     * 错误码
     */
    public static final String ERR_CODE = "err_code";
    /**
     * 商户订单号
     */
    public static final String OUT_TRADE_NO = "out_trade_no";
    /**
     * 商户退款单号 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    public static final String OUT_REFUND_NO = "out_refund_no";
    /**
     *微信退款单号  微信退款后返回
     */
    public static final String REFUND_ID = "refund_id";
    /**
     * 下载对账单的日期，格式：20140603
     */
    public static final String BILL_DATE = "bill_date";
    /**
     * 对账单类型
     */
    public static final String BILL_TYPE = "bill_type";
    public static final String BILL_TYPE_ALL = "ALL";
    /**
     * 订单 总金额
     */
    public static final String TOTAL_FEE = "total_fee";
    /**
     * 退款成功时间
     */
    public static final String REFUND_SUCCESS_TIME = "refund_success_time";
    /**
     * 退款金额
     */
    public static final String REFUND_FEE = "refund_fee";
    /**
     * 退款查询返回的结果数量
     */
    public static final String REFUND_COUNT = "refund_count";
    /**
     * 微信订单号
     */
    public static final String TRANSACTION_ID = "transaction_id";
    /**
     * 订单状态
     */
    public static final String TRADE_STATE = "trade_state";
    /**
     * 订单状态描述
     */
    public static final String TRADE_STATE_DESC = "trade_state_desc";
    /**
     * 支付方式
     */
    public static final String TRADE_TYPE = "trade_type";
    /**
     * 用户标识
     */
    public static final String OPENID = "openid";
    /**
     * 二维码短连接
     */
    public static final String CODE_URL = "code_url";

    /**
     * 错误码=>错误信息
     */
    public static final Map<String,String> ERR_DES_MAP = new HashMap<>();
    static {
        ERR_DES_MAP.put("SYSTEMERROR","系统异常，请重新调用该API");
        ERR_DES_MAP.put("ORDERPAID","订单已支付");
        ERR_DES_MAP.put("ORDERCLOSED","订单已关闭");
        ERR_DES_MAP.put("APPID_MCHID_NOT_MATCH","appid和mch_id不匹配");
        ERR_DES_MAP.put("OUT_TRADE_NO_USED","商户订单号重复");
        ERR_DES_MAP.put("LACK_PARAMS","缺少参数");
        ERR_DES_MAP.put("SIGNERROR","签名错误");
        ERR_DES_MAP.put("XML_FORMAT_ERROR","XML格式错误");
        ERR_DES_MAP.put("REQUIRE_POST_METHOD","请检查请求参数是否通过post方法提交");
        ERR_DES_MAP.put("POST_DATA_EMPTY","post数据不能为空");
        ERR_DES_MAP.put("NOT_UTF8","编码格式错误");
        ERR_DES_MAP.put("APPID_NOT_EXIST","参数中缺少APPID");
        ERR_DES_MAP.put("MCHID_NOT_EXIST","参数中缺少MCHID");
        ERR_DES_MAP.put("BIZERR_NEED_RETRY","退款业务流程错误，需要商户触发重试来解决");
        ERR_DES_MAP.put("TRADE_OVERDUE","订单已经超过退款期限");
        ERR_DES_MAP.put("ERROR","申请退款业务发生错误");
        ERR_DES_MAP.put("REFUND_FEE_MISMATCH","订单金额或退款金额与之前请求不一致，请核实后再试");
        ERR_DES_MAP.put("USER_ACCOUNT_ABNORMAL","退款请求失败");
        ERR_DES_MAP.put("INVALID_REQ_TOO_MUCH","无效请求过多");
        ERR_DES_MAP.put("NOTENOUGH","商户可用退款余额不足");
        ERR_DES_MAP.put("INVALID_TRANSACTIONID","请求参数未按指引进行填写");
        ERR_DES_MAP.put("PARAM_ERROR","请求参数未按指引进行填写");
        ERR_DES_MAP.put("ORDERNOTEXIST","订单号不存在");
        ERR_DES_MAP.put("FREQUENCY_LIMITED","该笔退款未受理，请降低频率后重试");
        ERR_DES_MAP.put("REFUNDNOTEXIST","退款订单查询失败");
    }
}

