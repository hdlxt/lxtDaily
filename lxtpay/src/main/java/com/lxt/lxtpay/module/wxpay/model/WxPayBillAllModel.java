package com.lxt.lxtpay.module.wxpay.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: WxPayBillAllModel
 * @Description: 获取解析对账单之后的信息模型
 * @Author: lxt 
 * @Date: 2019-02-27 10:12
 * @Version 1.0
 **/
public class WxPayBillAllModel implements Serializable {
    private List<WxPayBillDetailModel> wxPayBillDetailModelList;
    private WPayBillDetailSummaryModel wPayBillDetailSummaryModel;

    public List<WxPayBillDetailModel> getWxPayBillDetailModelList() {
        return wxPayBillDetailModelList;
    }

    public WxPayBillAllModel setWxPayBillDetailModelList(List<WxPayBillDetailModel> wxPayBillDetailModelList) {
        this.wxPayBillDetailModelList = wxPayBillDetailModelList;
        return this;
    }

    public WPayBillDetailSummaryModel getwPayBillDetailSummaryModel() {
        return wPayBillDetailSummaryModel;
    }

    public WxPayBillAllModel setwPayBillDetailSummaryModel(WPayBillDetailSummaryModel wPayBillDetailSummaryModel) {
        this.wPayBillDetailSummaryModel = wPayBillDetailSummaryModel;
        return this;
    }

    @Override
    public String toString() {
        return "WXPayBillAllModel{" +
                "wxPayBillDetailModelList=" + wxPayBillDetailModelList +
                ", wxPayBillDetailSummaryModel=" + wPayBillDetailSummaryModel +
                '}';
    }
}
