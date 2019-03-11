package com.lxt.lxtpay.module.unionpay.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: UnionPayZMBill
 * @Description: 商户一般交易明细流水文件记录格式
 * @Author: lxt
 * @Date: 2019-02-28 10:33
 * @Version 1.0
 **/
public class UnionPayZMBillModel {
    /**
     *交易代码
     */
    private String tradeCode;
    /**
     *代理机构标识码
     */
    private String acqInsCode;
    /**
     *发送机构标识码
     */
    private String sendIdentityCode;
    /**
     *系统跟踪号
     */
    private String traceNo;
    /**
     *交易传输时间
     */
    private String txnTime;
    /**
     *帐号
     */
    private String payCardNo;
    /**
     *交易金额
     */
    private String txtAmt;
    /**
     *商户类别
     */
    private String merCatCode;
    /**
     *终端类型
     */
    private String termType;
    /**
     *查询流水号
     */
    private String queryId;
    /**
     *支付方式（旧）
     */
    private String payTypeOld;
    /**
     *商户订单号
     */
    private String orderId;
    /**
     *支付卡类型
     */
    private String payCardType;
    /**
     *原始交易的系统跟踪号
     */
    private String origTraceNo;
    /**
     *原始交易日期时间
     */
    private String origTxtTime;
    /**
     *商户手续费
     */
    private String merHandlingFee;
    /**
     *结算金额
     */
    private String settleTxtAmt;
    /**
     *支付方式
     */
    private String payType;
    /**
     *集团商户代码
     */
    private String groupMerId;
    /**
     *交易类型
     */
    private String txnType;
    /**
     *交易子类
     */
    private String txnSubType;
    /**
     *业务类型
     */
    private String bizType;
    /**
     *帐号类型
     */
    private String accType;
    /**
     *账单类型
     */
    private String billType;
    /**
     *账单号码
     */
    private String billNo;
    /**
     *交互方式
     */
    private String interactMode;
    /**
     *原交易查询流水号
     */
    private String origQryId;
    /**
     *商户代码
     */
    private String merId;
    /**
     *分账入账方式
     */
    private String splitAccountType;
    /**
     *二级商户代码
     */
    private String subMerId;
    /**
     *二级商户简称
     */
    private String subMerAbbr;
    /**
     *二级商户分账入账金额
     */
    private String subMerAbbrSplitFee;
    /**
     *清算净额
     */
    private String cleanCettleTxtAmt;
    /**
     *终端号
     */
    private String termId;
    /**
     *商户自定义域
     */
    private String merReserved;
    /**
     *优惠金额
     */
    private String couponFee;
    /**
     *发票金额
     */
    private String invoiceFee;
    /**
     *分期付款附加手续费
     */
    private String hireServiceFee;
    /**
     *分期付款期数
     */
    private String hireFeeNum;
    /**
     *交易介质
     */
    private String tradeMedium;
    /**
     *原始交易订单号
     */
    private String origOrderId;
    /**
     *保留使用
     */
    private String ext;

    public String getTradeCode() {
        return tradeCode;
    }
    public List<UnionPayZMBillModel> build(List<Map<Integer,String>> mapList) {
        List<UnionPayZMBillModel> unionPayZMBillModelList = new ArrayList<>();
        Optional.ofNullable(mapList).ifPresent(
                ml -> {
                    mapList.stream().forEach(m -> {
                        UnionPayZMBillModel unionPayZMBillModel = new UnionPayZMBillModel();
                        unionPayZMBillModel.setTradeCode(m.get(0).trim());
                        unionPayZMBillModel.setAcqInsCode(m.get(1).trim());
                        unionPayZMBillModel.setSendIdentityCode(m.get(2).trim());
                        unionPayZMBillModel.setTraceNo(m.get(3).trim());
                        unionPayZMBillModel.setTxnTime(m.get(4).trim());
                        unionPayZMBillModel.setPayCardNo(m.get(5).trim());
                        unionPayZMBillModel.setTxtAmt(m.get(6).trim());
                        unionPayZMBillModel.setMerCatCode(m.get(7).trim());
                        unionPayZMBillModel.setTermType(m.get(8).trim());
                        unionPayZMBillModel.setQueryId(m.get(9).trim());
                        unionPayZMBillModel.setPayTypeOld(m.get(10).trim());
                        unionPayZMBillModel.setOrderId(m.get(11).trim());
                        unionPayZMBillModel.setPayCardType(m.get(12).trim());
                        unionPayZMBillModel.setOrigTraceNo(m.get(13).trim());
                        unionPayZMBillModel.setOrigTxtTime(m.get(14).trim());
                        unionPayZMBillModel.setMerHandlingFee(m.get(15).trim());
                        unionPayZMBillModel.setSettleTxtAmt(m.get(16).trim());
                        unionPayZMBillModel.setPayType(m.get(17).trim());
                        unionPayZMBillModel.setGroupMerId(m.get(18).trim());
                        unionPayZMBillModel.setTxnType(m.get(19).trim());
                        unionPayZMBillModel.setTxnSubType(m.get(20).trim());
                        unionPayZMBillModel.setBizType(m.get(21).trim());
                        unionPayZMBillModel.setAccType(m.get(22).trim());
                        unionPayZMBillModel.setBillType(m.get(23).trim());
                        unionPayZMBillModel.setBillNo(m.get(24).trim());
                        unionPayZMBillModel.setInteractMode(m.get(25).trim());
                        unionPayZMBillModel.setOrigQryId(m.get(26).trim());
                        unionPayZMBillModel.setMerId(m.get(27).trim());
                        unionPayZMBillModel.setSplitAccountType(m.get(28).trim());
                        unionPayZMBillModel.setSubMerId(m.get(29).trim());
                        unionPayZMBillModel.setSubMerAbbr(m.get(30).trim());
                        unionPayZMBillModel.setSubMerAbbrSplitFee(m.get(31).trim());
                        unionPayZMBillModel.setCleanCettleTxtAmt(m.get(32).trim());
                        unionPayZMBillModel.setTermId(m.get(33).trim());
                        unionPayZMBillModel.setMerReserved(m.get(34).trim());
                        unionPayZMBillModel.setCouponFee(m.get(35).trim());
                        unionPayZMBillModel.setInvoiceFee(m.get(36).trim());
                        unionPayZMBillModel.setHireServiceFee(m.get(37).trim());
                        unionPayZMBillModel.setHireFeeNum(m.get(38).trim());
                        unionPayZMBillModel.setTradeMedium(m.get(39).trim());
                        unionPayZMBillModel.setOrigOrderId(m.get(40).trim());
                        unionPayZMBillModel.setExt(m.get(41).trim());
                        unionPayZMBillModelList.add(unionPayZMBillModel);
                    });
                }
        );
        return unionPayZMBillModelList;
    }
    public UnionPayZMBillModel setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
        return this;
    }

    public String getAcqInsCode() {
        return acqInsCode;
    }

    public UnionPayZMBillModel setAcqInsCode(String acqInsCode) {
        this.acqInsCode = acqInsCode;
        return this;
    }

    public String getSendIdentityCode() {
        return sendIdentityCode;
    }

    public UnionPayZMBillModel setSendIdentityCode(String sendIdentityCode) {
        this.sendIdentityCode = sendIdentityCode;
        return this;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public UnionPayZMBillModel setTraceNo(String traceNo) {
        this.traceNo = traceNo;
        return this;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public UnionPayZMBillModel setTxnTime(String txnTime) {
        this.txnTime = txnTime;
        return this;
    }

    public String getPayCardNo() {
        return payCardNo;
    }

    public UnionPayZMBillModel setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
        return this;
    }

    public String getTxtAmt() {
        return txtAmt;
    }

    public UnionPayZMBillModel setTxtAmt(String txtAmt) {
        this.txtAmt = txtAmt;
        return this;
    }

    public String getMerCatCode() {
        return merCatCode;
    }

    public UnionPayZMBillModel setMerCatCode(String merCatCode) {
        this.merCatCode = merCatCode;
        return this;
    }

    public String getTermType() {
        return termType;
    }

    public UnionPayZMBillModel setTermType(String termType) {
        this.termType = termType;
        return this;
    }

    public String getQueryId() {
        return queryId;
    }

    public UnionPayZMBillModel setQueryId(String queryId) {
        this.queryId = queryId;
        return this;
    }

    public String getPayTypeOld() {
        return payTypeOld;
    }

    public UnionPayZMBillModel setPayTypeOld(String payTypeOld) {
        this.payTypeOld = payTypeOld;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public UnionPayZMBillModel setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public UnionPayZMBillModel setPayCardType(String payCardType) {
        this.payCardType = payCardType;
        return this;
    }

    public String getOrigTraceNo() {
        return origTraceNo;
    }

    public UnionPayZMBillModel setOrigTraceNo(String origTraceNo) {
        this.origTraceNo = origTraceNo;
        return this;
    }

    public String getOrigTxtTime() {
        return origTxtTime;
    }

    public UnionPayZMBillModel setOrigTxtTime(String origTxtTime) {
        this.origTxtTime = origTxtTime;
        return this;
    }

    public String getMerHandlingFee() {
        return merHandlingFee;
    }

    public UnionPayZMBillModel setMerHandlingFee(String merHandlingFee) {
        this.merHandlingFee = merHandlingFee;
        return this;
    }

    public String getSettleTxtAmt() {
        return settleTxtAmt;
    }

    public UnionPayZMBillModel setSettleTxtAmt(String settleTxtAmt) {
        this.settleTxtAmt = settleTxtAmt;
        return this;
    }

    public String getPayType() {
        return payType;
    }

    public UnionPayZMBillModel setPayType(String payType) {
        this.payType = payType;
        return this;
    }

    public String getGroupMerId() {
        return groupMerId;
    }

    public UnionPayZMBillModel setGroupMerId(String groupMerId) {
        this.groupMerId = groupMerId;
        return this;
    }

    public String getTxnType() {
        return txnType;
    }

    public UnionPayZMBillModel setTxnType(String txnType) {
        this.txnType = txnType;
        return this;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public UnionPayZMBillModel setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
        return this;
    }

    public String getBizType() {
        return bizType;
    }

    public UnionPayZMBillModel setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public String getAccType() {
        return accType;
    }

    public UnionPayZMBillModel setAccType(String accType) {
        this.accType = accType;
        return this;
    }

    public String getBillType() {
        return billType;
    }

    public UnionPayZMBillModel setBillType(String billType) {
        this.billType = billType;
        return this;
    }

    public String getBillNo() {
        return billNo;
    }

    public UnionPayZMBillModel setBillNo(String billNo) {
        this.billNo = billNo;
        return this;
    }

    public String getInteractMode() {
        return interactMode;
    }

    public UnionPayZMBillModel setInteractMode(String interactMode) {
        this.interactMode = interactMode;
        return this;
    }

    public String getOrigQryId() {
        return origQryId;
    }

    public UnionPayZMBillModel setOrigQryId(String origQryId) {
        this.origQryId = origQryId;
        return this;
    }

    public String getMerId() {
        return merId;
    }

    public UnionPayZMBillModel setMerId(String merId) {
        this.merId = merId;
        return this;
    }

    public String getSplitAccountType() {
        return splitAccountType;
    }

    public UnionPayZMBillModel setSplitAccountType(String splitAccountType) {
        this.splitAccountType = splitAccountType;
        return this;
    }

    public String getSubMerId() {
        return subMerId;
    }

    public UnionPayZMBillModel setSubMerId(String subMerId) {
        this.subMerId = subMerId;
        return this;
    }

    public String getSubMerAbbr() {
        return subMerAbbr;
    }

    public UnionPayZMBillModel setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
        return this;
    }

    public String getSubMerAbbrSplitFee() {
        return subMerAbbrSplitFee;
    }

    public UnionPayZMBillModel setSubMerAbbrSplitFee(String subMerAbbrSplitFee) {
        this.subMerAbbrSplitFee = subMerAbbrSplitFee;
        return this;
    }

    public String getCleanCettleTxtAmt() {
        return cleanCettleTxtAmt;
    }

    public UnionPayZMBillModel setCleanCettleTxtAmt(String cleanCettleTxtAmt) {
        this.cleanCettleTxtAmt = cleanCettleTxtAmt;
        return this;
    }

    public String getTermId() {
        return termId;
    }

    public UnionPayZMBillModel setTermId(String termId) {
        this.termId = termId;
        return this;
    }

    public String getMerReserved() {
        return merReserved;
    }

    public UnionPayZMBillModel setMerReserved(String merReserved) {
        this.merReserved = merReserved;
        return this;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public UnionPayZMBillModel setCouponFee(String couponFee) {
        this.couponFee = couponFee;
        return this;
    }

    public String getInvoiceFee() {
        return invoiceFee;
    }

    public UnionPayZMBillModel setInvoiceFee(String invoiceFee) {
        this.invoiceFee = invoiceFee;
        return this;
    }

    public String getHireServiceFee() {
        return hireServiceFee;
    }

    public UnionPayZMBillModel setHireServiceFee(String hireServiceFee) {
        this.hireServiceFee = hireServiceFee;
        return this;
    }

    public String getHireFeeNum() {
        return hireFeeNum;
    }

    public UnionPayZMBillModel setHireFeeNum(String hireFeeNum) {
        this.hireFeeNum = hireFeeNum;
        return this;
    }

    public String getTradeMedium() {
        return tradeMedium;
    }

    public UnionPayZMBillModel setTradeMedium(String tradeMedium) {
        this.tradeMedium = tradeMedium;
        return this;
    }

    public String getOrigOrderId() {
        return origOrderId;
    }

    public UnionPayZMBillModel setOrigOrderId(String origOrderId) {
        this.origOrderId = origOrderId;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public UnionPayZMBillModel setExt(String ext) {
        this.ext = ext;
        return this;
    }

    @Override
    public String toString() {
        return "UnionPayZMBillModel{" +
                "tradeCode='" + tradeCode + '\'' +
                ", acqInsCode='" + acqInsCode + '\'' +
                ", sendIdentityCode='" + sendIdentityCode + '\'' +
                ", traceNo='" + traceNo + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", payCardNo='" + payCardNo + '\'' +
                ", txtAmt='" + txtAmt + '\'' +
                ", merCatCode='" + merCatCode + '\'' +
                ", termType='" + termType + '\'' +
                ", queryId='" + queryId + '\'' +
                ", payTypeOld='" + payTypeOld + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payCardType='" + payCardType + '\'' +
                ", origTraceNo='" + origTraceNo + '\'' +
                ", origTxtTime='" + origTxtTime + '\'' +
                ", merHandlingFee='" + merHandlingFee + '\'' +
                ", settleTxtAmt='" + settleTxtAmt + '\'' +
                ", payType='" + payType + '\'' +
                ", groupMerId='" + groupMerId + '\'' +
                ", txnType='" + txnType + '\'' +
                ", txnSubType='" + txnSubType + '\'' +
                ", bizType='" + bizType + '\'' +
                ", accType='" + accType + '\'' +
                ", billType='" + billType + '\'' +
                ", billNo='" + billNo + '\'' +
                ", interactMode='" + interactMode + '\'' +
                ", origQryId='" + origQryId + '\'' +
                ", merId='" + merId + '\'' +
                ", splitAccountType='" + splitAccountType + '\'' +
                ", subMerId='" + subMerId + '\'' +
                ", subMerAbbr='" + subMerAbbr + '\'' +
                ", subMerAbbrSplitFee='" + subMerAbbrSplitFee + '\'' +
                ", cleanCettleTxtAmt='" + cleanCettleTxtAmt + '\'' +
                ", termId='" + termId + '\'' +
                ", merReserved='" + merReserved + '\'' +
                ", couponFee='" + couponFee + '\'' +
                ", invoiceFee='" + invoiceFee + '\'' +
                ", hireServiceFee='" + hireServiceFee + '\'' +
                ", hireFeeNum='" + hireFeeNum + '\'' +
                ", tradeMedium='" + tradeMedium + '\'' +
                ", origOrderId='" + origOrderId + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
