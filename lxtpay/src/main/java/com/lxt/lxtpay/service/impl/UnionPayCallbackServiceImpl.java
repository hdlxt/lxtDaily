package com.lxt.lxtpay.service.impl;

import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.module.unionpay.util.UnionPayConstants;
import com.lxt.lxtpay.service.PayCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName: UnionPayCallbackServiceImpl
 * @Description: 银联支付回调业务处理
 * @Author: lxt
 * @Date: 2019-03-11 10:36
 * @Version 1.0
 **/
@Service(PayConstants.UNION_PAY_CALLBACK)
public class UnionPayCallbackServiceImpl implements PayCallbackService {
    private final static Logger logger = LoggerFactory.getLogger(UnionPayCallbackServiceImpl.class);
    /**
     * @Title: payCallback
     * @Description: 银联支付结束回调业务处理
     * @Author: lxt 
     * @param: payCallbackModel
     * @Date: 2019-03-11 10:46 
     * @throws: 
     */
    @Override
    public void payCallback(PayCallbackModel payCallbackModel) {
        logger.info("银联支付回调处理开始......");
        //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
        Map<String,String> reqParam = payCallbackModel.getData();
        //获取后台通知的数据，其他字段也可用类似方式获取
        // 商户订单号
        String orderId =reqParam.get(UnionPayConstants.ORDER_ID);
        // 应答码
        String respCode = reqParam.get(UnionPayConstants.RESP_CODE);
        // 原交易流水号
        String queryId = reqParam.get(UnionPayConstants.QUERY_ID);
        //!!!判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。.
        String txnType = reqParam.get(UnionPayConstants.TXN_TYPE);
        switch (txnType){
            case UnionPayConstants.TXN_TYPE_01:
                logger.info("下单业务后台验签成功....");
                break;
            case UnionPayConstants.TXN_TYPE_04:
                logger.info("退货业务后台验签成功....");
                break;
            case UnionPayConstants.TXN_TYPE_31:
                logger.info("消费撤销业务后台验签成功....");
                break;
            default:
        }

        logger.info("银联宝支付回调处理结束......");
    }
}
