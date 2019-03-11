package com.lxt.lxtpay.utill;

import com.lxt.lxtpay.common.FileConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: DesignFileUtils
 * @Description: 文件操作工具类
 * @Author: lxt
 * @Date: 2019-02-15 14:50
 * @Version 1.0
 **/
public class DesignFileUtils {
    private final static Logger logger = LoggerFactory.getLogger(DesignFileUtils.class);

    /**
     * @Title: isFileBySuffix
     * @Description: 通过后缀名判断是否是某种文件
     * @Author: lxt
     * @param: fileName 文件名称
     * @param: suffix 后缀名
     * @Date: 2019-02-19 10:09
     * @return: boolean
     * @throws:
     */
    public static boolean isFileBySuffix(String fileName,String suffix){
        if(StringUtils.isNoneBlank(fileName) && StringUtils.isNoneBlank(suffix)){
            return fileName.endsWith(suffix.toLowerCase()) || fileName.endsWith(suffix.toUpperCase());
        }
        return false;
    }
    /**
     * @Title: downloadByUrlPath
     * @Description: 下载网络文件
     * @Author: lxt
     * @param: urlPath
     * @param: saveDir
     * @param: fileName
     * @Date: 2019-02-23 16:26
     * @return: java.io.File
     * @throws:
     */
    public static File downloadByUrlPath(String urlPath,String saveDir,String fileName){
        if(StringUtils.isBlank(urlPath)){
            logger.error("下载网络文件失败，链接为空");
            return null;
        }
        try (InputStream ins = new URL(urlPath).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
            return new File(saveDir+File.separator+fileName);
        } catch (IOException e) {
            logger.error("下载网络文件异常",e);
        }
        return null;
    }
    /**
     * @Title: getQrCodePath
     * @Description: 获取二维码临时存储目录
     * @Author: lxt
     * @Date: 2019-02-25 22:41
     * @return: java.lang.String
     * @throws:
     */
    public static String getQrCodePath(){
        // 格式：static/qrcode
        File file = new File(FileConstants.QRCODE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    /**
     * @Title: getBillPath
     * @Description: 获取对账单临时存储目录
     * @Author: lxt 
     * @Date: 2019-02-27 17:41 
     * @return: java.lang.String
     * @throws: 
     */
    public static String getBillPath(){
        File file = new File(FileConstants.BILL);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
