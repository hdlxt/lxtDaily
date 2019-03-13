package com.lxt.file.properties;


import com.lxt.file.util.OssUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName: OssProerties
 * @Description: 阿里云 对象云存储配置类
 * @Author: lxt
 * @Date: 2019-02-14 09:37
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "oss.aliyun")
public class OssProerties {
    private  String accessKeyId;
    private  String accessKeySecret;
    private  String bucketName;
    private  String endpoint;
    private  String bucket;

    @PostConstruct
    public void init(){
        //Oss工具类配置初始化
        OssUtil.initConfig(this);
    }

    //过期时间
    private  int  expiration;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public OssProerties setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public OssProerties setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public OssProerties setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public OssProerties setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public OssProerties setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public int getExpiration() {
        return expiration;
    }

    public OssProerties setExpiration(int expiration) {
        this.expiration = expiration;
        return this;
    }

    @Override
    public String toString() {
        return "OssPro{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", bucket='" + bucket + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
