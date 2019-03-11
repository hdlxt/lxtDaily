package com.lxt.lxtpay.module.unionpay.service.impl;

import com.lxt.lxtpay.common.CharConstants;
import com.lxt.lxtpay.common.PayConstants;
import com.lxt.lxtpay.common.ResultData;
import com.lxt.lxtpay.model.PayCallbackModel;
import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.model.RefundModel;
import com.lxt.lxtpay.module.unionpay.model.UnionPayAllBillModel;
import com.lxt.lxtpay.module.unionpay.model.UnionPayTradeQueryModel;
import com.lxt.lxtpay.module.unionpay.model.UnionPayZMBillModel;
import com.lxt.lxtpay.module.unionpay.model.UnionPayZMEBillModel;
import com.lxt.lxtpay.module.unionpay.sdk.AcpService;
import com.lxt.lxtpay.module.unionpay.sdk.LogUtil;
import com.lxt.lxtpay.module.unionpay.sdk.SDKConfig;
import com.lxt.lxtpay.module.unionpay.sdk.SDKConstants;
import com.lxt.lxtpay.module.unionpay.service.UnionPayService;
import com.lxt.lxtpay.module.unionpay.util.UnionPayConstants;
import com.lxt.lxtpay.module.unionpay.util.UnionPayUtil;
import com.lxt.lxtpay.service.PayCallbackService;
import com.lxt.lxtpay.utill.DateUtil;
import com.lxt.lxtpay.utill.DesignFileUtils;
import com.lxt.lxtpay.utill.PayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @ClassName: UnionPayServiceImpl
 * @Description: 银联支付实现类
 * @Author: lxt
 * @Date: 2019-02-25 11:29
 * @Version 1.0
 **/
@Service
public class UnionPayServiceImpl implements UnionPayService {
    private final static Logger logger = LoggerFactory.getLogger(UnionPayServiceImpl.class);
    @Value("${pay.unionpay.acpsdk.backUrl}")
    private String backUrl;
    @Value("${pay.unionpay.acpsdk.frontUrl}")
    private String frontUrl;
    @Resource(name= PayConstants.UNION_PAY_CALLBACK)
    private PayCallbackService payCallbackService;

    /**
     * @Title: initConfig
     * @Description: 初始化配置
     * @Author: lxt
     * @Date: 2019-03-09 14:16
     * @throws:
     */
    @PostConstruct
    public void initConfig(){
        // 加载银联配置文件
        SDKConfig.getConfig().loadPropertiesFromSrc();
        //重置
        SDKConfig.getConfig().setBackUrl(backUrl);
        SDKConfig.getConfig().setFrontUrl(frontUrl);
    }
    /**
     * @Title: payPC
     * @Description: 银联电脑支付
     * @Author: lxt 
     * @param: productModel
     *      注：
     *          outTradeNo【必填】商户订单号
     *          txnTime【必填】订单发送时间，需要存储，用于后续查询
     *          totalFee【必填】订单金额 单位：元
     * @Date: 2019-02-25 13:49 
     * @return: java.lang.String
     * @throws: 
     */
    @Override
    public ResultData<String> payPC(ProductModel productModel) {
        try {
            if(StringUtils.isBlank(productModel.getOutTradeNo()) || StringUtils.isBlank(productModel.getTotalFee())
                    || StringUtils.isBlank(productModel.getTxnTime())){
                return ResultData.fail("订单信息不完整");
            }
            String html = null;
            Map<String, String> requestData = new HashMap<String, String>(30);
            //版本号，全渠道默认值
            requestData.put("version", SDKConfig.getConfig().getVersion());
            //字符集编码，可以使用UTF-8,GBK两种方式
            requestData.put("encoding", CharConstants.UTF_8);
            //签名方法
            requestData.put("signMethod", SDKConfig.getConfig().getSignMethod());
            //交易类型 ，01：消费
            requestData.put("txnType", "01");
            //交易子类型， 01：自助消费
            requestData.put("txnSubType", "01");
            //业务类型，B2C网关支付，手机wap支付
            requestData.put("bizType", "000201");
            //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
            requestData.put("channelType", "07");
            //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
            requestData.put("merId", SDKConfig.getConfig().getMerId());
            //接入类型，0：直连商户
            requestData.put("accessType", "0");
            //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
            requestData.put("orderId",productModel.getOutTradeNo());
            //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
            requestData.put("txnTime",productModel.getTxnTime());
            //交易币种（境内商户一般是156 人民币）
            requestData.put("currencyCode", "156");
            //交易金额，单位分，不要带小数点
            requestData.put("txnAmt", PayUtil.handleTotalFee(productModel.getTotalFee()));
            //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
            //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
            //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
            requestData.put("frontUrl", SDKConfig.getConfig().getFrontUrl());
            //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
            //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
            //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
            //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
            //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
            requestData.put("backUrl", SDKConfig.getConfig().getBackUrl());
            // 订单超时时间。
            // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
            // 此时间建议取支付时的北京时间加15分钟。
            // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
            requestData.put("payTimeout", DateUtil.format(DateUtil.plusDate(null, ChronoUnit.MINUTES,15),DateUtil.PATTERN_yyyyMMddHHmmss));
            //////////////////////////////////////////////////
            //
            //       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
            //
            //////////////////////////////////////////////////
            /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
            //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> submitFromData = AcpService.sign(requestData,CharConstants.UTF_8);
            //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
            String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
            //生成自动跳转的Html表单
            html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,CharConstants.UTF_8);
            logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
            return ResultData.oK(html);
        } catch (Exception e) {
            logger.error("银联构造表单异常",e);
            return ResultData.fail("银联构造表单异常");
        }
    }
    /**
     * @Title: tradeQuery
     * @Description: 查询订单
     * @Author: lxt
     * @param: orderId【必填】 商户订单号
     * @param: txnTime【必填】 发送下单时间 格式为YYYYMMDDhhmmss
     * @Date: 2019-02-27 13:49
     * @return: com.design.background.model.ResultData<com.design.background.pay.unionpay.model.UnionPayTradeQueryModel>
     * @throws:
     */
    @Override
    public ResultData<UnionPayTradeQueryModel> tradeQuery(String orderId, String txnTime) {
        try {
            Map<String, String> data = new HashMap<String, String>(20);
            // 版本号
            data.put("version", SDKConfig.getConfig().getVersion());
            // 字符集编码 可以使用UTF-8,GBK两种方式
            data.put("encoding", CharConstants.UTF_8);
            // 签名方法
            data.put("signMethod", SDKConfig.getConfig().getSignMethod());
            // 交易类型 00-默认
            data.put("txnType", "00");
            // 交易子类型  默认00
            data.put("txnSubType", "00");
            // 业务类型 B2C网关支付，手机wap支付
            data.put("bizType", "000201");
            // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
            data.put("merId", SDKConfig.getConfig().getMerId());
            // 接入类型，商户接入固定填0，不需修改
            data.put("accessType", "0");
            // 商户订单号，每次发交易测试需修改为被查询的交易的订单号
            data.put("orderId", orderId);
            // 订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
            data.put("txnTime", txnTime);
            // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> reqData = AcpService.sign(data,CharConstants.UTF_8);
            // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
            String url = SDKConfig.getConfig().getSingleQueryUrl();
            // 这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
            Map<String, String> rspData = AcpService.post(reqData,url,CharConstants.UTF_8);
            // 应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
            if(!rspData.isEmpty()){
                if(AcpService.validate(rspData, CharConstants.UTF_8)){
                    logger.info("验证签名成功");
                    if(UnionPayConstants.RESP_CODE_00.equals(rspData.get(UnionPayConstants.RESP_CODE))){
                        String origRespCode = rspData.get(UnionPayConstants.ORIG_RESP_CODE);
                        //处理被查询交易的应答码逻辑
                        if(UnionPayConstants.RESP_CODE_00.equals(origRespCode)){
                            //交易成功，更新商户订单状态
                            //TODO
                            logger.info("查询成功，已有结果");
                            return ResultData.oK().setData(new UnionPayTradeQueryModel().build(rspData));
                        }
                    }
                }
            }
            return ResultData.fail(rspData.get(UnionPayConstants.RESP_MSG));
        }catch (Exception e){
            logger.error("查询订单异常",e);
            return ResultData.fail("查询订单异常");
        }
    }
    /**
     * @Title: closeTrade
     * @Description: 消费撤销(关闭订单)
     * @Author: lxt
     * @param: txnAmt【必填】　撤销金额　消费撤销时必须和原消费金额相同
     * @param: origQryId【必填】　原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
     * @Date: 2019-02-27 14:46
     * @return: com.design.background.model.ResultData<String>
     * @throws:
     */
    @Override
    public ResultData<String> closeTrade(String txnAmt, String origQryId) {
        try {
            if(StringUtils.isBlank(txnAmt) || StringUtils.isBlank(origQryId) ){
                return ResultData.fail("参数有误！");
            }
            Map<String, String> data = new HashMap<String, String>(30);
            //　版本号
            data.put("version", SDKConfig.getConfig().getVersion());
            //　字符集编码 可以使用UTF-8,GBK两种方式
            data.put("encoding", CharConstants.UTF_8);
            //　签名方法
            data.put("signMethod", SDKConfig.getConfig().getSignMethod());
            //　交易类型 31-消费撤销
            data.put("txnType", "31");
            //　交易子类型  默认00
            data.put("txnSubType", "00");
            //　业务类型 B2C网关支付，手机wap支付
            data.put("bizType", "000201");
            //　渠道类型，07-PC，08-手机
            data.put("channelType", "07");
            //　商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
            data.put("merId", SDKConfig.getConfig().getMerId());
            //　接入类型，商户接入固定填0，不需修改
            data.put("accessType", "0");
            //　商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
            data.put("orderId", UnionPayUtil.getOrderId());
            //　订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
            data.put("txnTime", UnionPayUtil.getCurrentTime());
            //　【撤销金额】，消费撤销时必须和原消费金额相同
            data.put("txnAmt", PayUtil.handleTotalFee(txnAmt));
            //　交易币种(境内商户一般是156 人民币)
            data.put("currencyCode", "156");
            //　后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费撤销交易 商户通知,其他说明同消费交易的商户通知
            data.put("backUrl", SDKConfig.getConfig().getBackUrl());
            // 【原始交易流水号】，原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
            data.put("origQryId", origQryId);
            //　报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> reqData  = AcpService.sign(data,CharConstants.UTF_8);
            //　交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
            String reqUrl = SDKConfig.getConfig().getBackRequestUrl();
            //　发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
            Map<String,String> rspData = AcpService.post(reqData,reqUrl,CharConstants.UTF_8);
            //　应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
            if(!rspData.isEmpty()){
                if(AcpService.validate(rspData, CharConstants.UTF_8)){
                    LogUtil.writeLog("验证签名成功============>:"+rspData);
                    if(UnionPayConstants.RESP_CODE_00.equals(rspData.get(UnionPayConstants.RESP_CODE))){
                        //　交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。
                        LogUtil.writeLog("撤销交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。");
                        return ResultData.oK("撤销交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。");
                    }
                }
            }
            return ResultData.fail(rspData.get(UnionPayConstants.RESP_MSG));
        }catch (Exception e){
            logger.error("撤销交易异常",e);
            return ResultData.fail("撤销交易异常");
        }
    }
    /**
     * @Title: refund
     * @Description:
     * @Author: lxt
     * @param: RefundModel
     *              txnAmt【必填】 退款金额 退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
     *              origQryId【必填】 原始交易流水号
     * @Date: 2019-02-27 15:42
     * @return: com.design.background.model.ResultData<java.lang.String>
     * @throws:
     */
    @Override
    public ResultData<String> refund(RefundModel refundModel) {
        try {
            if(StringUtils.isBlank(refundModel.getOrigQryId()) || StringUtils.isBlank(refundModel.getRefundAmount())){
                return ResultData.fail("退款信息不完整");
            }
            Map<String, String> data = new HashMap<String, String>(20);
            // 版本号
            data.put("version", SDKConfig.getConfig().getVersion());
            //字符集编码 可以使用UTF-8,GBK两种方式
            data.put("encoding", CharConstants.UTF_8);
            //签名方法
            data.put("signMethod", SDKConfig.getConfig().getSignMethod());
            //交易类型 04-退货
            data.put("txnType", "04");
            //交易子类型  默认00
            data.put("txnSubType", "00");
            //业务类型 B2C网关支付，手机wap支付
            data.put("bizType", "000201");
            //渠道类型，07-PC，08-手机
            data.put("channelType", "07");
            //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
            data.put("merId", SDKConfig.getConfig().getMerId());
            //接入类型，商户接入固定填0，不需修改
            data.put("accessType", "0");
            //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
            data.put("orderId", UnionPayUtil.getOrderId());
            //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
            data.put("txnTime", UnionPayUtil.getCurrentTime());
            //交易币种（境内商户一般是156 人民币）
            data.put("currencyCode", "156");
            //退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
            data.put("txnAmt", PayUtil.handleTotalFee(refundModel.getRefundAmount()));
            //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
            data.put("backUrl", SDKConfig.getConfig().getBackUrl());
            //原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
            data.put("origQryId", refundModel.getOrigQryId());
            //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> reqData  = AcpService.sign(data,CharConstants.UTF_8);
            //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
            String url = SDKConfig.getConfig().getBackRequestUrl();
            //这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
            Map<String, String> rspData = AcpService.post(reqData,url,CharConstants.UTF_8);
            //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
            if(!rspData.isEmpty()){
                if(AcpService.validate(rspData, CharConstants.UTF_8)){
                    LogUtil.writeLog("验证签名成功");
                    LogUtil.writeLog("退款结果："+rspData);
                    if(UnionPayConstants.RESP_CODE_00.equals(rspData.get(UnionPayConstants.RESP_CODE))){
                        //　交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。
                        LogUtil.writeLog("退款交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。");
                        return ResultData.oK("退款交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。");
                    }
                }
            }
            return ResultData.fail(rspData.get(UnionPayConstants.RESP_MSG));
        }catch (Exception e){
            logger.error("退款异常",e);
            return ResultData.fail("退款异常");
        }
    }
    /**
     * @Title: dowloadBill
     * @Description: 下载对账单
     * @Author: lxt
     * @param: settleDate【线上环境必填】 格式为MMDD
     * @param: profile【可选】 运行环境  UnionPayConstants.PROFILE_DEV=>"DEV" 为测试 其他均为线上环境
     *          原因：测试账户中的商户号【700000000000001】和对账单下载日期【0119】均固定。
     * @Date: 2019-02-27 15:50
     * @return: com.design.background.model.ResultData<com.design.background.pay.unionpay.model.UnionPayAllBillModel>
     * @throws:
     */
    @Override
    public ResultData<UnionPayAllBillModel> dowloadBill(String settleDate, String profile) {
        // 对账单压缩包路径
        String zipFilePath = null;
        // 对账单文件路径
        List<String> fileList = new ArrayList<>() ;
        try {
            Map<String, String> data = new HashMap<String, String>(20);
            //版本号 全渠道默认值
            data.put("version", SDKConfig.getConfig().getVersion());
            //字符集编码 可以使用UTF-8,GBK两种方式
            data.put("encoding", CharConstants.UTF_8);
            //签名方法
            data.put("signMethod", SDKConfig.getConfig().getSignMethod());
            //交易类型 76-对账文件下载
            data.put("txnType", "76");
            //交易子类型 01-对账文件下载
            data.put("txnSubType", "01");
            //业务类型，固定
            data.put("bizType", "000000");
            //接入类型，商户接入填0，不需修改
            data.put("accessType", "0");
            //商户代码
            data.put("merId", UnionPayConstants.PROFILE_DEV.equals(profile) ? UnionPayConstants.DEV_MER_ID : SDKConfig.getConfig().getMerId());
            //清算日期
            data.put("settleDate",UnionPayConstants.PROFILE_DEV.equals(profile) ? UnionPayConstants.DEV_SETTLE_DATE : settleDate);
            //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
            data.put("txnTime",UnionPayUtil.getCurrentTime());
            //文件类型，一般商户填写00即可
            data.put("fileType", "00");
            //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> reqData = AcpService.sign(data,CharConstants.UTF_8);
            //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl
            String url = SDKConfig.getConfig().getFileTransUrl();
            Map<String, String> rspData =  AcpService.post(reqData,url,CharConstants.UTF_8);
            //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
            if(!rspData.isEmpty()){
                if(AcpService.validate(rspData, CharConstants.UTF_8)){
                    LogUtil.writeLog("验证签名成功");
                    if(UnionPayConstants.RESP_CODE_00.equals(rspData.get(UnionPayConstants.RESP_CODE))){
                        String outPutDirectory = DesignFileUtils.getBillPath();
                        // 交易成功，解析返回报文中的fileContent并落地
                        zipFilePath = AcpService.deCodeFileContent(rspData,outPutDirectory,CharConstants.UTF_8);
                        //对落地的zip文件解压缩并解析
                        fileList = UnionPayUtil.unzip(zipFilePath, outPutDirectory);
                        //解析ZM，ZME文件
                        UnionPayAllBillModel unionPayAllBillModel = new UnionPayAllBillModel();
                        for(String file : fileList){
                            if(file.indexOf(UnionPayConstants.BILL_ZM_)!=-1){
                                unionPayAllBillModel.setUnionPayZMBillModelList(new UnionPayZMBillModel().build(UnionPayUtil.parseZMFile(file)));
                            }else if(file.indexOf(UnionPayConstants.BILL_ZME_)!=-1){
                                unionPayAllBillModel.setUnionPayZMEBillModelList(new UnionPayZMEBillModel().build(UnionPayUtil.parseZMEFile(file)));
                            }
                        }
                        if(!unionPayAllBillModel.getUnionPayZMBillModelList().isEmpty() || !unionPayAllBillModel.getUnionPayZMEBillModelList().isEmpty()){
                            logger.info("获取对账单成功");
                            return ResultData.oK().setData(unionPayAllBillModel);
                        }
                    }
                }
            }
            return ResultData.fail(rspData.get(UnionPayConstants.RESP_MSG));
        }catch (Exception e){
            logger.error("下载对账单异常",e);
            return ResultData.fail("下载对账单异常");
        }finally {
            // 删除对账单文件
            try {
                if(StringUtils.isNoneBlank(zipFilePath)){
                    Files.deleteIfExists(Paths.get(zipFilePath));
                    logger.error("对账单压缩包删除成功：{}",zipFilePath);
                }
                for (String f: fileList) {
                    Files.deleteIfExists(Paths.get(f));
                    logger.error("对账单文件删除成功：{}",f);
                }
            } catch (Exception e) {
                logger.error("删除对账单文件异常",e);
            }
        }
    }
    /**
     * @Title: backRcvResponse
     * @Description: 银联后台回调接口
     * @Author: lxt
     * @param: req
     * @param: resp
     * @Date: 2019-03-11 10:01
     * @throws:
     */
    @Override
    public void backRcvResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
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
                    payCallbackService.payCallback(new PayCallbackModel());
                    break;
                case UnionPayConstants.TXN_TYPE_04:
                    logger.info("退货业务后台验签成功....");
                    payCallbackService.payCallback(new PayCallbackModel());
                    break;
                case UnionPayConstants.TXN_TYPE_31:
                    logger.info("消费撤销业务后台验签成功....");
                    payCallbackService.payCallback(new PayCallbackModel());
                    break;
                default:
            }
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态码
        resp.getWriter().print("ok");
    }
    /**
     * @Title: frontRcvResponse
     * @Description: 银联前台回调
     * @Author: lxt
     * @param: model
     * @param: req
     * @param: resp
     * @param: successUrl 支付成功后跳转页面url
     * @Date: 2019-02-25 14:30
     * @return: java.lang.String
     * @throws:
     */
    @Override
    public String frontRcvResponse(Model model, HttpServletRequest req, HttpServletResponse resp, String successUrl) throws UnsupportedEncodingException {
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
        Map<String, String> respParam = getAllRequestParam(req);

        // 打印请求报文
        LogUtil.printRequestLog(respParam);

        Map<String, String> valideData = null;
        StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                page.append("<tr><td width=\"30%\" align=\"right\">" + key
                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!AcpService.validate(valideData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            LogUtil.writeLog("验证签名结果[成功].");
            //其他字段也可用类似方式获取
            System.out.println(valideData.get(UnionPayConstants.ORDER_ID));
            String respCode = valideData.get(UnionPayConstants.RESP_CODE);
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        }
        model.addAttribute("result", page.toString());
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
        return successUrl;
    }
    /**
     * 获取请求参数中所有的信息
     * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>(30);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
