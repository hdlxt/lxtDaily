package com.lxt.lxtpay.module.unionpay.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: UnionPayZMEBillModel
 * @Description: 商户差错交易明细流水文件记录格式
 * @Author: lxt
 * @Date: 2019-02-28 10:33
 * @Version 1.0
 **/
public class UnionPayZMEBillModel {
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
     *商户类型
     */
    private String merCatCode;
    /**
     *终端类型
     */
    private String termType;
    /**
     *支付卡类型
     */
    private String payCardType;
    /**
     *上一笔交易的系统跟踪号
     */
    private String lastTraceNo;
    /**
     *上一笔交易的日期时间
     */
    private String lastOrderDate;
    /**
     *上一笔交易清算日期
     */
    private String lastSettleDate;
    /**
     *上一笔交易金额
     */
    private String lastTxnAmt;
    /**
     *商户手续费
     */
    private String merHandlingFee;
    /**
     *结算金额
     */
    private String settleTxtAmt;
    /**
     *集团商户代码
     */
    private String groupMerId;
    /**
     *商户代码
     */
    private String merId;
    /**
     *分账清算标识
     */
    private String splitSettleFlag;
    /**
     *分期付款附加手续费
     */
    private String hireServiceFee;
    /**
     *分期付款期数
     */
    private String hireFeeNum;
    /**
     *保留使用
     */
    private String ext;
    /**
     * @Title: build
     * @Description: 创建商户差错交易明细流水实例
     * @Author: lxt 
     * @param: mapList
     * @Date: 2019-02-28 11:01 
     * @return: com.design.background.pay.unionpay.model.UnionPayZMEBillModel
     * @throws: 
     */
    public List<UnionPayZMEBillModel> build(List<Map<Integer,String>> mapList){
        List<UnionPayZMEBillModel> unionPayZMEBillModelList = new ArrayList<>();
        Optional.ofNullable(mapList).ifPresent(
                ml->{
                    mapList.stream().forEach(m->{
                        UnionPayZMEBillModel unionPayZMEBillModel = new UnionPayZMEBillModel();
                        unionPayZMEBillModel.setTradeCode(m.get(0).trim());
                        unionPayZMEBillModel.setAcqInsCode(m.get(1).trim());
                        unionPayZMEBillModel.setSendIdentityCode(m.get(2).trim());
                        unionPayZMEBillModel.setTraceNo(m.get(3).trim());
                        unionPayZMEBillModel.setTxnTime(m.get(4).trim());
                        unionPayZMEBillModel.setPayCardNo(m.get(5).trim());
                        unionPayZMEBillModel.setTxtAmt(m.get(6).trim());
                        unionPayZMEBillModel.setMerCatCode(m.get(7).trim());
                        unionPayZMEBillModel.setTermType(m.get(8).trim());
                        unionPayZMEBillModel.setPayCardType(m.get(9).trim());
                        unionPayZMEBillModel.setLastTraceNo(m.get(10).trim());
                        unionPayZMEBillModel.setLastOrderDate(m.get(11).trim());
                        unionPayZMEBillModel.setLastSettleDate(m.get(12).trim());
                        unionPayZMEBillModel.setLastTxnAmt(m.get(13).trim());
                        unionPayZMEBillModel.setMerHandlingFee(m.get(14).trim());
                        unionPayZMEBillModel.setSettleTxtAmt(m.get(15).trim());
                        unionPayZMEBillModel.setGroupMerId(m.get(16).trim());
                        unionPayZMEBillModel.setMerId(m.get(17).trim());
                        unionPayZMEBillModel.setSplitSettleFlag(m.get(18).trim());
                        unionPayZMEBillModel.setHireServiceFee(m.get(19).trim());
                        unionPayZMEBillModel.setMerHandlingFee(m.get(20).trim());
                        unionPayZMEBillModel.setExt(m.get(21).trim());
                        unionPayZMEBillModelList.add(unionPayZMEBillModel);
                    });
                }
        );
        return unionPayZMEBillModelList;
    }
    public String getTradeCode() {
        return tradeCode;
    }

    public UnionPayZMEBillModel setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
        return this;
    }

    public String getAcqInsCode() {
        return acqInsCode;
    }

    public UnionPayZMEBillModel setAcqInsCode(String acqInsCode) {
        this.acqInsCode = acqInsCode;
        return this;
    }

    public String getSendIdentityCode() {
        return sendIdentityCode;
    }

    public UnionPayZMEBillModel setSendIdentityCode(String sendIdentityCode) {
        this.sendIdentityCode = sendIdentityCode;
        return this;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public UnionPayZMEBillModel setTraceNo(String traceNo) {
        this.traceNo = traceNo;
        return this;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public UnionPayZMEBillModel setTxnTime(String txnTime) {
        this.txnTime = txnTime;
        return this;
    }

    public String getPayCardNo() {
        return payCardNo;
    }

    public UnionPayZMEBillModel setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
        return this;
    }

    public String getSettleTxtAmt() {
        return settleTxtAmt;
    }

    public UnionPayZMEBillModel setSettleTxtAmt(String settleTxtAmt) {
        this.settleTxtAmt = settleTxtAmt;
        return this;
    }

    public String getMerCatCode() {
        return merCatCode;
    }

    public UnionPayZMEBillModel setMerCatCode(String merCatCode) {
        this.merCatCode = merCatCode;
        return this;
    }

    public String getTermType() {
        return termType;
    }

    public UnionPayZMEBillModel setTermType(String termType) {
        this.termType = termType;
        return this;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public UnionPayZMEBillModel setPayCardType(String payCardType) {
        this.payCardType = payCardType;
        return this;
    }

    public String getLastTraceNo() {
        return lastTraceNo;
    }

    public UnionPayZMEBillModel setLastTraceNo(String lastTraceNo) {
        this.lastTraceNo = lastTraceNo;
        return this;
    }

    public String getLastOrderDate() {
        return lastOrderDate;
    }

    public UnionPayZMEBillModel setLastOrderDate(String lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
        return this;
    }

    public String getLastSettleDate() {
        return lastSettleDate;
    }

    public UnionPayZMEBillModel setLastSettleDate(String lastSettleDate) {
        this.lastSettleDate = lastSettleDate;
        return this;
    }

    public String getLastTxnAmt() {
        return lastTxnAmt;
    }

    public UnionPayZMEBillModel setLastTxnAmt(String lastTxnAmt) {
        this.lastTxnAmt = lastTxnAmt;
        return this;
    }

    public String getMerHandlingFee() {
        return merHandlingFee;
    }

    public UnionPayZMEBillModel setMerHandlingFee(String merHandlingFee) {
        this.merHandlingFee = merHandlingFee;
        return this;
    }

    public String getTxtAmt() {
        return txtAmt;
    }

    public UnionPayZMEBillModel setTxtAmt(String txtAmt) {
        this.txtAmt = txtAmt;
        return this;
    }

    public String getGroupMerId() {
        return groupMerId;
    }

    public UnionPayZMEBillModel setGroupMerId(String groupMerId) {
        this.groupMerId = groupMerId;
        return this;
    }

    public String getMerId() {
        return merId;
    }

    public UnionPayZMEBillModel setMerId(String merId) {
        this.merId = merId;
        return this;
    }

    public String getSplitSettleFlag() {
        return splitSettleFlag;
    }

    public UnionPayZMEBillModel setSplitSettleFlag(String splitSettleFlag) {
        this.splitSettleFlag = splitSettleFlag;
        return this;
    }

    public String getHireServiceFee() {
        return hireServiceFee;
    }

    public UnionPayZMEBillModel setHireServiceFee(String hireServiceFee) {
        this.hireServiceFee = hireServiceFee;
        return this;
    }

    public String getHireFeeNum() {
        return hireFeeNum;
    }

    public UnionPayZMEBillModel setHireFeeNum(String hireFeeNum) {
        this.hireFeeNum = hireFeeNum;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public UnionPayZMEBillModel setExt(String ext) {
        this.ext = ext;
        return this;
    }

    @Override
    public String toString() {
        return "UnionPayZMEBillModel{" +
                "tradeCode='" + tradeCode + '\'' +
                ", acqInsCode='" + acqInsCode + '\'' +
                ", sendIdentityCode='" + sendIdentityCode + '\'' +
                ", traceNo='" + traceNo + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", payCardNo='" + payCardNo + '\'' +
                ", txtAmt='" + txtAmt + '\'' +
                ", merCatCode='" + merCatCode + '\'' +
                ", termType='" + termType + '\'' +
                ", payCardType='" + payCardType + '\'' +
                ", lastTraceNo='" + lastTraceNo + '\'' +
                ", lastOrderDate='" + lastOrderDate + '\'' +
                ", lastSettleDate='" + lastSettleDate + '\'' +
                ", lastTxnAmt='" + lastTxnAmt + '\'' +
                ", merHandlingFee='" + merHandlingFee + '\'' +
                ", settleTxtAmt='" + settleTxtAmt + '\'' +
                ", groupMerId='" + groupMerId + '\'' +
                ", merId='" + merId + '\'' +
                ", splitSettleFlag='" + splitSettleFlag + '\'' +
                ", hireServiceFee='" + hireServiceFee + '\'' +
                ", hireFeeNum='" + hireFeeNum + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
