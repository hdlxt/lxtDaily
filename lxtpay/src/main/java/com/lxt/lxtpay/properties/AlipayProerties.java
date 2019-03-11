package com.lxt.lxtpay.properties;

import com.lxt.lxtpay.module.alipay.util.AliPayUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName: AliPayProerties
 * @Description: 阿里支付宝支付配置类
 * @Author: lxt 
 * @Date: 2019-02-21 11:51
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayProerties {
    /**
     * 签名方式
     */
    private  String openApiDomain;
    /**
     * 商户id
     */
    private  String pid;
    /**
     * 应用ID
     */
    private  String appId;
    /**
     *RSA私钥
     */
    private  String appPrivateKey;
    /**
     * 支付宝公钥
     */
    private  String alipayPublicKey;
    /**
     * 签名方式
     */
    private  String signType;
    /**
     * 参数类型
     */
    private  String paramType;
    /**
     * 编码
     */
    private  String charSet;
    /**
     * 前台回调地址
     */
    private  String returnUrl;
    /**
     * 后台通知地址
     */
    private  String notifyUrl;

    @PostConstruct
    public void init(){
        //AlipayUtil具类配置初始化
        AliPayUtil.initConfig(this);
    }

    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public AlipayProerties setOpenApiDomain(String openApiDomain) {
        this.openApiDomain = openApiDomain;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public AlipayProerties setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public AlipayProerties setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
        return this;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public AlipayProerties setAlipayPublicKey(String aliPayPublicKey) {
        this.alipayPublicKey = aliPayPublicKey;
        return this;
    }

    public String getSignType() {
        return signType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public AlipayProerties setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public AlipayProerties setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public AlipayProerties setSignType(String signType) {
        this.signType = signType;
        return this;
    }

    public String getParamType() {
        return paramType;
    }

    public AlipayProerties setParamType(String paramType) {
        this.paramType = paramType;
        return this;
    }

    public String getCharSet() {
        return charSet;
    }

    public AlipayProerties setCharSet(String charSet) {
        this.charSet = charSet;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public AlipayProerties setPid(String pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public String toString() {
        return "AlipayProerties{" +
                "openApiDomain='" + openApiDomain + '\'' +
                ", pid='" + pid + '\'' +
                ", appId='" + appId + '\'' +
                ", appPrivateKey='" + appPrivateKey + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", signType='" + signType + '\'' +
                ", paramType='" + paramType + '\'' +
                ", charSet='" + charSet + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
