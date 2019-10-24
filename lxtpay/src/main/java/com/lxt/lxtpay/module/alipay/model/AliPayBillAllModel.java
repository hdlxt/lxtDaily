package com.lxt.lxtpay.module.alipay.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AliPayBillAllModel
 * @Description: 获取解析对账单之后的信息模型
 * @Author: lxt 
 * @Date: 2019-02-23 15:38
 * @Version 1.0
 **/
public class AliPayBillAllModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<AliPayBillDetailModel> aliPayBillDetailModelList;
    private AliPayBillDetailSummaryModel aliPayBillDetailSummaryModel;
    private AliPayBillSummaryModel aliPayBillSummaryModel;
    private AliPayBillAllModel(Builder builder) {
        this.aliPayBillDetailModelList = builder.aliPayBillDetailModels;
        this.aliPayBillDetailSummaryModel = builder.aliPayBillDetailSummaryModel1;
        this.aliPayBillSummaryModel = builder.aliPayBillSummaryModel1;
    }

    public static class  Builder{
        private List<AliPayBillDetailModel> aliPayBillDetailModels;
        private AliPayBillDetailSummaryModel aliPayBillDetailSummaryModel1;
        private AliPayBillSummaryModel aliPayBillSummaryModel1;

        public List<AliPayBillDetailModel> getAliPayBillDetailModels() {
            return aliPayBillDetailModels;
        }

        public Builder setAliPayBillDetailModels(List<AliPayBillDetailModel> aliPayBillDetailModels) {
            this.aliPayBillDetailModels = aliPayBillDetailModels;
            return this;
        }

        public AliPayBillDetailSummaryModel getAliPayBillDetailSummaryModel1() {
            return aliPayBillDetailSummaryModel1;
        }

        public Builder setAliPayBillDetailSummaryModel1(AliPayBillDetailSummaryModel aliPayBillDetailSummaryModel1) {
            this.aliPayBillDetailSummaryModel1 = aliPayBillDetailSummaryModel1;
            return this;
        }

        public AliPayBillSummaryModel getAliPayBillSummaryModel1() {
            return aliPayBillSummaryModel1;
        }

        public Builder setAliPayBillSummaryModel1(AliPayBillSummaryModel aliPayBillSummaryModel1) {
            this.aliPayBillSummaryModel1 = aliPayBillSummaryModel1;
            return this;
        }

        public AliPayBillAllModel build(){
            return new AliPayBillAllModel(this);
        }
    }

    public List<AliPayBillDetailModel> getAliPayBillDetailModelList() {
        return aliPayBillDetailModelList;
    }

    public AliPayBillAllModel setAliPayBillDetailModelList(List<AliPayBillDetailModel> aliPayBillDetailModelList) {
        this.aliPayBillDetailModelList = aliPayBillDetailModelList;
        return this;
    }

    public AliPayBillDetailSummaryModel getAliPayBillDetailSummaryModel() {
        return aliPayBillDetailSummaryModel;
    }

    public AliPayBillAllModel setAliPayBillDetailSummaryModel(AliPayBillDetailSummaryModel aliPayBillDetailSummaryModel) {
        this.aliPayBillDetailSummaryModel = aliPayBillDetailSummaryModel;
        return this;
    }

    public AliPayBillSummaryModel getAliPayBillSummaryModel() {
        return aliPayBillSummaryModel;
    }

    public AliPayBillAllModel setAliPayBillSummaryModel(AliPayBillSummaryModel aliPayBillSummaryModel) {
        this.aliPayBillSummaryModel = aliPayBillSummaryModel;
        return this;
    }

    @Override
    public String toString() {
        return "AliPayBillAllModel{" +
                "aliPayBillDetailModelList=" + aliPayBillDetailModelList +
                ", aliPayBillDetailSummaryModel=" + aliPayBillDetailSummaryModel +
                ", aliPayBillSummaryModel=" + aliPayBillSummaryModel +
                '}';
    }
}
