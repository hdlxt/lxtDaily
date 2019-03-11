package com.lxt.lxtpay.module.wxpay.sdk;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: WXPayProerties
 * @Description: 微信支付配置类
 * @Author: lxt
 * @Date: 2019-02-25 16:47
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "pay.wxpay")
public class WXPayProerties extends WXPayConfig {
    /**
     * 应用授权ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     *  API 密钥
     */
    private String key;
    /**
     * 加载证书
     */
    private byte [] certData;
    /**
     * @Title: WXPayProerties
     * @Description: 构造函数初始化证书
     * @Author: lxt
     * @Date: 2019-02-26 17:22
     * @throws:
     */
    public WXPayProerties() throws IOException {
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cer/wxpay/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    private String notifyUrl;
    @Override
    public String getAppId() {
        return appId;
    }
    @Override
    public String getMchId() {
        return mchId;
    }
    @Override
    public String getKey() {
        return key;
    }
    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(certData);
    }
    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    @Override
    public IWXPayDomain getWXPayDomain(){
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
    public WXPayProerties setAppId(String appId) {
        this.appId = appId;
        return this;
    }
    public WXPayProerties setMchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public WXPayProerties setKey(String key) {
        this.key = key;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public WXPayProerties setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    @Override
    public String toString() {
        return "WXPayProerties{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
