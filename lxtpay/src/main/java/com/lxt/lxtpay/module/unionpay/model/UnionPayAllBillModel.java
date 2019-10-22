package com.lxt.lxtpay.module.unionpay.model;

import java.util.List;

/**
 * @ClassName: UnionPayZMBill
 * @Description: 银联对账单内容
 * @Author: lxt
 * @Date: 2019-02-28 10:33
 * @Version 1.0
 **/
public class UnionPayAllBillModel {
    private List<UnionPayZMBillModel> unionPayZMBillModelList;
    private List<UnionPayZMEBillModel> unionPayZMEBillModelList;

    public List<UnionPayZMBillModel> getUnionPayZMBillModelList() {
        return unionPayZMBillModelList;
    }

    public UnionPayAllBillModel setUnionPayZMBillModelList(List<UnionPayZMBillModel> unionPayZMBillModelList) {
        this.unionPayZMBillModelList = unionPayZMBillModelList;
        return this;
    }

    public List<UnionPayZMEBillModel> getUnionPayZMEBillModelList() {
        return unionPayZMEBillModelList;
    }

    public UnionPayAllBillModel setUnionPayZMEBillModelList(List<UnionPayZMEBillModel> unionPayZMEBillModelList) {
        this.unionPayZMEBillModelList = unionPayZMEBillModelList;
        return this;
    }

    @Override
    public String toString() {
        return "UnionPayAllBillModel0{" +
                "unionPayZMBillModelList=" + unionPayZMBillModelList +
                ", unionPayZMEBillModelList=" + unionPayZMEBillModelList +
                '}';
    }
}
