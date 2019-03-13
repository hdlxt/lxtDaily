package com.lxt.file.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.lxt.file.properties.OssProerties;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: OSSUtill
 * @Description: 阿里云 对象云存储工具类
 * @Author: lxt
 * @Date: 2019-02-13 14:38
 * @Version 1.0
 **/
public class OssUtil {
    private final static Logger logger = LoggerFactory.getLogger(OssUtil.class);
    /**
     * 注入配置
     */
    private  static OssProerties ossProerties;
    /**
     * @Title: initConfig
     * @Description: 配置初始化
     * @Author: lxt 
     * @param: ossProertiesInit
     * @Date: 2019-02-14 09:25 
     * @throws: 
     */
    public static void initConfig(OssProerties ossProertiesInit){
        ossProerties = ossProertiesInit;
    }

    /**
     * @Title: generateKey
     * @Description:  生成oss对象名称
     * @Author: lxt
     * @param: fileName
     * @Date: 2019-02-13 15:21
     * @return: java.lang.String
     * @throws:
     */
    public  static String generateKey(String fileName) {
        //对象名称格式：yyyymm.name.6位随机字符.ext
        return new StringBuilder(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"))
                .append(".").append(getRandomStrByNum(6))
                .append(".").append(FilenameUtils.getExtension(fileName))
                .toString();
    }
    /**
     * @Title: upload
     * @Description: 上传方法
     * @Author: lxt 
     * @param: key 对象名称
     * @param: file待上传文件
     * @Date: 2019-02-13 15:35
     * @return: java.lang.String
     * @throws: 
     */
    public  static String upload(String key,File file) {
        if(file == null || !file.exists()){
            logger.error("阿里云上传文件失败【"+file+"】不存在");
            return null;
        }
        logger.info("阿里云上传文件开始：【"+file+"】");
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            ossClient.putObject(ossProerties.getBucketName(),key,file);
            //设置url过期时间
            Date expirationDate = DateUtils.addYears(new Date(), ossProerties.getExpiration());
            String url = ossClient.generatePresignedUrl(ossProerties.getBucketName(), key, expirationDate).toString();
            logger.info("阿里云上传文件结束：【"+file+"】=>【"+url+"】");
            return url;
        }catch(Exception e) {
            logger.error("阿里云上传文件异常【"+file+"】",e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
    /**
     * @Title: upload
     * @Description: 上传方法
     * @Author: lxt
     * @param: key 对象名称
     * @param: file待上传文件
     * @Date: 2019-02-13 15:35
     * @return: java.lang.String
     * @throws:
     */
    public  static String upload(String key,byte[] bytes) {
        if(bytes == null || StringUtils.isBlank(key)){
            logger.error("阿里云上传文件不存在:【"+key+"】");
            return null;
        }
        logger.info("阿里云上传文件开始:【"+key+"】");
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            ossClient.putObject(ossProerties.getBucketName(),key, new ByteArrayInputStream(bytes));
            //设置url过期时间
            Date expirationDate = DateUtils.addYears(new Date(), ossProerties.getExpiration());
            String url = ossClient.generatePresignedUrl(ossProerties.getBucketName(), key, expirationDate).toString();
            logger.info("阿里云上传文件结束：【"+key+"】=>【"+url+"】");
            return url;
        }catch(Exception e) {
            logger.error("阿里云上传文件异常:【"+key+"】",e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
    /**
     * @Title: uploadWithObjectName
     * @Description: 上传方法，返回对象名称和 url
     * @Author: lxt
     * @param: file待上传文件
     * @Date: 2019-02-13 15:35
     * @return: java.lang.Map<String,String>
     * @throws:
     */
    public  static Map<String,String> uploadWithObjectName(File file) {
        if(file == null || !file.exists()){
            logger.error("阿里云上传文件失败【"+file+"】不存在");
            return null;
        }
        Map<String,String> map = new HashMap<>();
        logger.info("阿里云上传文件开始：【"+file+"】");
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            String key = generateKey(file.getName());

            ossClient.putObject(ossProerties.getBucketName(),key, new FileInputStream(file));
            //设置url过期时间
            Date expirationDate = DateUtils.addYears(new Date(), ossProerties.getExpiration());
            String url = ossClient.generatePresignedUrl(ossProerties.getBucketName(), key, expirationDate).toString();
            logger.info("阿里云上传文件结束：【"+file+"】=>【"+url+"】");
            map.put("objectName",key);
            map.put("url",url);
            return map;
        }catch(Exception e) {
            logger.error("阿里云上传文件异常【"+file+"】",e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
    /**
     * @Title: delete
     * @Description: 删除方法
     * @Author: lxt
     * @param: url 待删除对象url
     * @Date: 2019-02-13 15:54 
     * @throws: 
     */
    public  static void delete(String url) {
        if(StringUtils.isBlank(url)){
            logger.error("阿里云删除文件失败，对象url为空");
            return;
        }
        logger.info("阿里云删除文件开始：【"+url+"】");
        if(url.contains(ossProerties.getBucket())){
            //根据url获取对象名称
            url = getObjectNameByUrl(url);
        }
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            // 删除文件
            ossClient.deleteObject(ossProerties.getBucketName(), url);
            logger.info("阿里云删除文件结束：【"+url+"】");
        }catch(Exception e) {
            logger.error("阿里云删除文件异常【"+url+"】",e);
        }finally {
            ossClient.shutdown();
        }
    }
    /**
     * @Title: download
     * @Description: 下载文件到本地文件
     * @Author: lxt 
     * @param: url 待下载对象url
     * @param: filePath 下载到本地文件
     * @Date: 2019-02-13 15:56
     * @return: java.io.File
     * @throws: 
     */
    public static File download2File(String url, String filePath) {
        logger.info("阿里云下载文件开始：【"+url+"】");
        if(url.contains(ossProerties.getBucket())){
            //根据url获取对象名称
            url = getObjectNameByUrl(url);
        }
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            File file = new File(filePath);
            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            ossClient.getObject(new GetObjectRequest(ossProerties.getBucketName(), url),file);
            logger.info("阿里云下载文件结束：【"+url+"】");
            return file;
        }catch(Exception e) {
            logger.error("阿里云下载文件异常【"+url+"】",e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
    /**
     * @Title: download
     * @Description: 通过流下载文件
     * @Author: lxt
     * @param: url 待下载对象url
     * @param: filePath 下载到本地文件
     * @Date: 2019-02-13 15:56
     * @return: java.io.File
     * @throws:
     */
    public static void download2FileByStream(String url, String fileName, HttpServletResponse response) {
        BufferedInputStream inputStream = null;
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try(
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            if(url.contains(ossProerties.getBucket())){
                //根据url获取对象名称
                url = getObjectNameByUrl(url);
            }
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(StringUtils.isBlank(fileName) ? url : fileName, "UTF-8"));
            logger.info("阿里云下载文件开始：【"+url+"】");
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(ossProerties.getBucketName(), url);
            inputStream = new BufferedInputStream(ossObject.getObjectContent());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))){
                outputStream.write(buff, 0, bytesRead);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("下载异常！", e);
        }finally {
            logger.info("阿里云下载文件结束：【"+url+"】");
            ossClient.shutdown();
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @Title: download
     * @Description: 下载文件到指定目录,文件名称为阿里云文件对象名称
     * @Author: lxt
     * @param: url 待下载对象url
     * @param: dir 下载到本地目录
     * @Date: 2019-02-13 15:56
     * @return: java.io.File
     * @throws:
     */
    public static File download2Dir(String url, String dir) {
        logger.info("阿里云下载文件开始：【"+url+"】");
        if(url.contains(ossProerties.getBucket())){
            //根据url获取对象名称
            url = getObjectNameByUrl(url);
        }
        OSS ossClient = new OSSClient(ossProerties.getEndpoint(), ossProerties.getAccessKeyId(), ossProerties.getAccessKeySecret());
        try {
            File file = new File(dir+File.separator+url);
            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            ossClient.getObject(new GetObjectRequest(ossProerties.getBucketName(), url),file);
            logger.info("阿里云下载文件结束：【"+url+"】");
            return file;
        }catch(Exception e) {
            logger.error("阿里云下载文件异常【"+url+"】",e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
    /*
     * @Title: getObjectNameByUrl
     * @Description: 通过url获取对象名称
     * @Author: lxt 
     * @param: url
     * @Date: 2019-02-13 16:20 
     * @return: java.lang.String
     * @throws: 
     */
    public  static String getObjectNameByUrl(String url){
        if(StringUtils.isBlank(url)){
            return null;
        }
        return url.substring(url.indexOf(ossProerties.getBucket())+ossProerties.getBucket().length()+1,url.indexOf("?"));
    }
    public static String CHAR_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * @Title: getRandomStrByNum
     * @Description:  获取不同位数的随机字符串
     * @Author: lxt
     * @param: factor
     * @Date: 2019-02-13 15:25
     * @return: java.lang.String
     * @throws:
     */
    public static String getRandomStrByNum(int factor) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < factor; i++) {
            sb.append(CHAR_STR.charAt(random.nextInt(36)));
        }
        return sb.toString();
    }
}
