package com.lxt.lxtpay.module.alipay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.lxt.lxtpay.common.CharConstants;
import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.module.alipay.model.AliPayBillAllModel;
import com.lxt.lxtpay.module.alipay.model.AliPayBillDetailModel;
import com.lxt.lxtpay.module.alipay.model.AliPayBillDetailSummaryModel;
import com.lxt.lxtpay.module.alipay.model.AliPayBillSummaryModel;
import com.lxt.lxtpay.properties.AlipayProerties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @ClassName: AliPayConfig
 * @Description: 支付宝支付工具类
 * @Author: lxt 
 * @Date: 2019-02-21 11:39
 * @Version 1.0
 **/
public final class AliPayUtil {
    private final static Logger logger = LoggerFactory.getLogger(AliPayUtil.class);
    /**
     * 注入配置
     */
    private static AlipayProerties aliPayProerties;
    /**
     * 定义客户端变量
     */
    private  volatile static AlipayClient alipayClient = null;
	 /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private AliPayUtil(){};
    /**
     * @Title: initConfig
     * @Description: 初始化配置
     * @Author: lxt
     * @param: aliPayProerties
     * @Date: 2019-02-21 11:41
     * @throws:
     */
    public static void initConfig(AlipayProerties aliPayProertiesInit){
        aliPayProerties=aliPayProertiesInit;
    }
    /**
     * @Title: getInstance
     * @Description: 双重检查　单例模式获取客户端类
     * @Author: lxt 
     * @Date: 2019-02-21 12:01 
     * @return: com.alipay.api.AlipayClient
     * @throws: 
     */
    private static AlipayClient getInstance(){
        if (alipayClient == null) {
            synchronized (AlipayClient.class) {
                if (alipayClient == null) {
                    alipayClient = new DefaultAlipayClient(aliPayProerties.getOpenApiDomain(), aliPayProerties.getAppId(),
                            aliPayProerties.getAppPrivateKey(), aliPayProerties.getParamType(), aliPayProerties.getCharSet()
                            , aliPayProerties.getAlipayPublicKey(), aliPayProerties.getSignType());
                }
            }
        }
        return alipayClient;
    }
    /**
     * @Title: getAlipayClient
     * @Description: 获取支付宝客户端实例
     * @Author: lxt
     * @Date: 2019-02-21 11:39
     * @return: com.alipay.api.AlipayClient
     * @throws:
     */
    public static AlipayClient getAlipayClient(){
        return getInstance();
    }
    /**
     * @Title: readCvsZipFile
     * @Description: 读取csv zip压缩包的内容
     * @Author: lxt 
     * @param: file csv压缩包文件
     * @param: type 可选，默认为所有
     *         AlipayAccountConstants.TYPE_DETAIL 获取业务明细列表信息
     *         AlipayAccountConstants.TYPE_DETAIL_SUMARRY 获取业务明细列表结束后的汇总信息
     *         AlipayAccountConstants.TYPE_SUMARRY 获取业务明细汇总表的信息
     *         AlipayAccountConstants.TYPE_ALL 获取所有信息(默认)
     * @Date: 2019-02-23 16:45
     * @return: com.design.background.pay.alipay.model.AccountAllModel
     * @throws: 
     */
    public static ResultData<AliPayBillAllModel> readCvsZipFile(File file, String ... types){
        try(
                ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file), Charset.forName(CharConstants.GBK));
                //不解压直接读取,加上gbk解决乱码问题
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,CharConstants.GBK));) {
            //初始化构建器
            AliPayBillAllModel.Builder builder = new AliPayBillAllModel.Builder();
            ZipEntry zipFile;
            //循环读取zip中的cvs文件，无法使用jdk自带，因为文件名中有中文
            while ((zipFile=inputStream.getNextEntry())!=null) {
                if (!zipFile.isDirectory()){
                    //获得cvs名字
                    String fileName = zipFile.getName();
                    if(types == null || types.length == 0){
                        if(fileName.contains(AliPayBillConstants.BILL_DETAIL) && !fileName.contains(AliPayBillConstants.BILL_SUMMARY)){
                            AliPayBillAllModel aliPayBillAllModel = getAccountDetail(bufferedReader,true,true);
                            builder.setAliPayBillDetailModels(aliPayBillAllModel.getAliPayBillDetailModelList())
                                    .setAliPayBillDetailSummaryModel1(aliPayBillAllModel.getAliPayBillDetailSummaryModel());
                        }else if(fileName.contains(AliPayBillConstants.BILL_SUMMARY)){
                            builder.setAliPayBillSummaryModel1(getAccountSummary(bufferedReader));
                        }
                    }else{
                        for (String type : types) {
                            switch (type){
                                case AliPayBillConstants.TYPE_ALL:
                                    if(fileName.contains(AliPayBillConstants.BILL_DETAIL)){
                                        AliPayBillAllModel aliPayBillAllModel = getAccountDetail(bufferedReader,true,true);
                                        builder.setAliPayBillDetailModels(aliPayBillAllModel.getAliPayBillDetailModelList())
                                                .setAliPayBillDetailSummaryModel1(aliPayBillAllModel.getAliPayBillDetailSummaryModel());
                                    }else if(fileName.contains(AliPayBillConstants.BILL_SUMMARY)){
                                        builder.setAliPayBillSummaryModel1(getAccountSummary(bufferedReader));
                                    }
                                    break;
                                case AliPayBillConstants.TYPE_DETAIL:
                                    if(fileName.contains(AliPayBillConstants.BILL_DETAIL)){
                                        AliPayBillAllModel aliPayBillAllModel = getAccountDetail(bufferedReader,true,false);
                                        builder.setAliPayBillDetailModels(aliPayBillAllModel.getAliPayBillDetailModelList());
                                    }
                                    break;
                                case AliPayBillConstants.TYPE_DETAIL_SUMARRY:
                                    if(fileName.contains(AliPayBillConstants.BILL_DETAIL)){
                                        AliPayBillAllModel aliPayBillAllModel = getAccountDetail(bufferedReader,false,true);
                                        builder.setAliPayBillDetailSummaryModel1(aliPayBillAllModel.getAliPayBillDetailSummaryModel());
                                    }
                                    break;
                                case AliPayBillConstants.TYPE_SUMARRY:
                                    if(fileName.contains(AliPayBillConstants.BILL_SUMMARY)){
                                        builder.setAliPayBillSummaryModel1(getAccountSummary(bufferedReader));
                                    }
                                    break;
                                default:
                                    return ResultData.fail("type值类型不正确");
                            }
                        }
                    }
                }
            }
            // 删除已解析完成的对账单
            if(file.delete()){
                logger.info("删除已解析完成的对账单成功："+file);
            }else{
                logger.error("删除已解析完成的对账单失败："+file);
            }
            return ResultData.oK().setData(builder.build());
        }catch (Exception e){
            logger.error("读取csv文件异常",e);
        }
        return ResultData.fail("读取csv文件异常");
    }
    /**
     * @Title: getAccountDetail
     * @Description: 获取取对账单【业务明细】详情
     * @Author: lxt
     * @param: reader
     * @param: needSummayFalg 是否需要获取业务列表内容
     * @param: needSummayFalg 是否需要获取业务列表结束之后的汇总内容
     * @Date: 2019-02-23 14:23
     * @return: java.util.List<com.design.background.pay.alipay.model.AccountDetailModel>
     * @throws:
     */
    private static AliPayBillAllModel getAccountDetail(BufferedReader reader, boolean needDetailFalg, boolean needSummayFalg) {
        try {
            // 第一行信息，为标题信息，不用,如果需要，注释掉
            reader.read();
            String line;
            String[] titleArr = null;
            // 存储标题的map，内容为key 数组下标为 value
            Map<Integer,String> titleMap = new HashMap<>(50);
            boolean flag = false;
            //初始化构建器
            AliPayBillAllModel.Builder builder = new AliPayBillAllModel.Builder();
            List<AliPayBillDetailModel> alipayBillDetailModelList = new ArrayList<>();
            AliPayBillDetailSummaryModel accountDetailSummaryModel = new AliPayBillDetailSummaryModel();
            while ((line = reader.readLine()) != null) {
                // CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String[] item = line.split("\r\n");
                // 字符串数据
                String content = item[item.length - 1];
                if(content.contains(AliPayBillConstants.BILL_DETAIL_LIST_END)){
                    flag = false;
                }
                if(needDetailFalg && flag){
                    if(content.contains(AliPayBillConstants.TRADE_NO)){
                        // 标题
                        if (content.contains(AliPayBillConstants.STRORE_CODE)) {
                            titleArr = content.split(",");
                            for (int i = 0; i < titleArr.length; i++) {
                                titleMap.put(i,titleArr[i]);
                            }
                        }
                    }else{
                        alipayBillDetailModelList.add(new AliPayBillDetailModel().build(content.split(","),titleMap));
                    }
                }
                if(content.contains(AliPayBillConstants.BILL_DETAIL_LIST) && !content.contains(AliPayBillConstants.BILL_DETAIL_LIST_END)){
                    flag = true;
                }
                if(needSummayFalg && content.contains(AliPayBillConstants.TRADE_SUM_VAL)){
                    // 获取交易合计信息
                    accountDetailSummaryModel
                            .setTradeSum(content.substring(content.indexOf(AliPayBillConstants.TRADE_SUM_VAL)+ AliPayBillConstants.TRADE_SUM_VAL.length(),content.indexOf(AliPayBillConstants.BI)))
                            .setTradeSumMoney(content.substring(content.indexOf(AliPayBillConstants.TRADE_SUM_MONEY_VAL)+ AliPayBillConstants.TRADE_SUM_MONEY_VAL.length(),content.indexOf(AliPayBillConstants.YUAN)))
                            .setTradeSumCouponMoney(content.substring(content.indexOf(AliPayBillConstants.COUPON_SUM_MONEY_VAL)+ AliPayBillConstants.COUPON_SUM_MONEY_VAL.length(),content.lastIndexOf(AliPayBillConstants.YUAN)));
                }
                if(needSummayFalg && content.contains(AliPayBillConstants.REFUND_SUM_VAL)){
                    // 退款合计信息
                    accountDetailSummaryModel
                            .setRefundTradeSum(content.substring(content.indexOf(AliPayBillConstants.REFUND_SUM_VAL)+ AliPayBillConstants.REFUND_SUM_VAL.length(),content.indexOf(AliPayBillConstants.BI)))
                            .setRefundTradeSumMoney(content.substring(content.indexOf(AliPayBillConstants.REFUND_SUM_MONEY_VAL)+ AliPayBillConstants.REFUND_SUM_MONEY_VAL.length(),content.indexOf(AliPayBillConstants.YUAN)))
                            .setRefundTradeSumCouponMoney(content.substring(content.indexOf(AliPayBillConstants.COUPON_REFUND_SUM_MONEY_VAL)+ AliPayBillConstants.COUPON_REFUND_SUM_MONEY_VAL.length(),content.lastIndexOf(AliPayBillConstants.YUAN)));
                }
            }
            return builder.setAliPayBillDetailModels(alipayBillDetailModelList)
                    .setAliPayBillDetailSummaryModel1(accountDetailSummaryModel)
                    .build();
        } catch (Exception e) {
            logger.error("获取取对账单【业务明细】详情异常",e);
        }
        return null;

    }
    /**
     * @Title: getAccountSummary
     * @Description: 获取【业务汇总】详情
     * @Author: lxt
     * @param: filePath
     * @param: charset 编码 默认为GBK
     * @Date: 2019-02-23 14:24
     * @return: com.design.background.pay.alipay.model.AccountSummaryModel
     * @throws:
     */
    private static AliPayBillSummaryModel getAccountSummary(BufferedReader reader) {
        try {
            String line;
            // 存储 标题内容
            String[] titleArr=null;
            // 存储标题的map，内容为key 数组下标为 value
            Map<Integer,String> titleMap = new HashMap<>(20);
            while ((line = reader.readLine()) != null) {
                String[] item = line.split("\r\n");
                String content = item[item.length - 1];
                if (content.contains(AliPayBillConstants.STRORE_CODE)) {
                    titleArr = content.split(",");
                    for (int i = 0; i < titleArr.length; i++) {
                        titleMap.put(i,titleArr[i]);
                    }
                }
                if (content.contains(AliPayBillConstants.SUMMARY)) {
                    return new AliPayBillSummaryModel().build(content.split(","),titleMap);
                }
            }
        } catch (Exception e) {
            logger.error("获取【业务汇总】详情异常",e);
        }
        return null;
    }
}
