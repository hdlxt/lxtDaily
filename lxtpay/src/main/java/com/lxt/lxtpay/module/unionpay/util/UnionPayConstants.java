package com.lxt.lxtpay.module.unionpay.util;

/**
 * @ClassName: UnionPayConstants
 * @Description: 银联支付相关常量
 * @Author: lxt
 * @Date: 2019-02-27 15:37
 * @Version 1.0
 **/
public class UnionPayConstants {
    /**
     * 应答码 key
     */
    public final static String RESP_CODE = "respCode";
    /**
     * 应答码 value ===========
     */
    /**
     * 成功
     */
    public final static String RESP_CODE_00 = "00";
    /**
     * 交易通讯超时，请发起查询交易
     */
    public final static String RESP_CODE_03 = "03";
    /**
     * 交易状态未明，请查询对账结果
     */
    public final static String RESP_CODE_04 = "04";
    /**
     * 交易已受理，请稍后查询交易结果
     */
    public final static String RESP_CODE_05 = "05";
    /**
     * 返回信息
     */
    public final static String RESP_MSG = "respMsg";
    /**
     * 对账单下载使用 DEV为测试
     */
    public final static String PROFILE_DEV = "DEV";
    public final static String PROFILE_PRO = "PRO";
    public final static String DEV_SETTLE_DATE = "0119";
    public final static String DEV_MER_ID = "700000000000001";
    /**
     * 对账单文件标识
     */
    public final static String BILL_ZM_ = "ZM_";
    public final static String BILL_ZME_ = "ZME_";
    /**
     *产品类型
     */
    public final static String BIZ_TYPE = "bizType";
    /**
     *商户订单号(唯一)
     */
    public final static String ORDER_ID = "orderId";
    /**
     *清算金额
     */
    public final static String SETTLE_AMT = "settleAmt";
    /**
     *清算币种
     */
    public final static String SETTLE_CURRENCY_CODE = "settleCurrencyCode";
    /**
     *清算日期
     */
    public final static String SETTLE_DATE = "settleDate";
    /**
     *交易类型
     */
    public final static String TXN_TYPE = "txnType";
    /**
     * 交易类型：
     *      01=>消费（下单）
     *      04=> 退货（退款）
     *      31=>消费撤销（关闭订单）
     *      76=>对账单文件下载
     */
    public final static String TXN_TYPE_01 = "01";
    public final static String TXN_TYPE_04 = "04";
    public final static String TXN_TYPE_31 = "31";
    public final static String TXN_TYPE_76 = "76";
    /**
     *交易子类
     */
    public final static String TXN_SUB_TYPE = "txnSubType";
    /**
     *原始流水号
     */
    public final static String QUERY_ID = "queryId";
    /**
     *发送订单时间
     */
    public final static String TXN_TIME = "txnTime";
    /**
     *买家卡号 eg:6216***********0018
     */
    public final static String ACC_NO = "accNo";
    /**
     *原交易应答信息
     */
    public final static String ORIG_RESP_MSG = "origRespMsg";
    /**
     *原交易应答码
     */
    public final static String ORIG_RESP_CODE = "origRespCode";
    /**
     *交易总金额
     */
    public final static String TXN_AMT = "txnAmt";
    /**
     * 接入类型
     * 0：商户直连接入
     * 1：收单机构接入
     * 2：平台商户接入
     */
    public final static String ACCESS_TYPE = "accessType";
    /**
     * 交易币种
     */
    public final static String CURRENCY_CODE = "currencyCode";
}
