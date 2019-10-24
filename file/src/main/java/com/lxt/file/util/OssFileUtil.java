package com.lxt.file.util;

import com.lxt.file.constants.FileConstants;
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
public class OssFileUtil {
    private final static Logger logger = LoggerFactory.getLogger(OssFileUtil.class);
    /**
     * @Title: uploadSingleFile
     * @Description:  单个文件上传
     * @Author: lxt 
     * @param: file
     * @Date: 2019-02-15 14:56 
     * @return: java.lang.String 成功返回 文件路径，失败返回null
     * @throws: 
     */
    public static String uploadSingleFile(MultipartFile file){
        if(file == null){
            logger.error("单文件上传失败，文件为空");
            return null;
        }
        try {
            return OssUtil.upload(OssUtil.generateKey(file.getOriginalFilename()),file.getBytes());
        } catch (Exception e) {
            logger.error("单文件上传异常【"+file+"】",e);
        }
        return null;
    }
    /**
     * @Title: uploadMultipartFile
     * @Description: 多文件文件上传
     * @Author: lxt 
     * @param: files
     * @Date: 2019-02-18 13:08 
     * @return: java.util.List<java.lang.String> 成功返回 文件路径集合，失败返回null
     * @throws: 
     */
    public static List<String> uploadMultipartFile(List<MultipartFile> fileList){
        List<String> filePaths = new ArrayList<>();
        Optional.ofNullable(fileList).ifPresent(fl->{
                        fl.stream().forEach(f->{
                            try {
                                filePaths.add(OssUtil.upload(OssUtil.generateKey(f.getOriginalFilename()),f.getBytes()));
                            } catch (IOException e) {
                                logger.error("多文件上传异常【"+f+"】",e);
                            }
                        });
                }
        );
        return filePaths;
    }
    /**
     * @Title: downloadSingleFileByOss
     * @Description: 下载阿里云文件到本地
     * @Author: lxt
     * @param: url 阿里云链接
     * @param: filePath 下载目录
     * @Date: 2019-02-18 13:13
     * @return: java.io.File
     * @throws:
     */
    public static File downloadSingleFile(String url,String filePath){
        try {
            return OssUtil.download2File(url,filePath);
        } catch (Exception e) {
            logger.error("单文件下载异常【"+url+"】",e);
        }
        return null;
    }
    /**
     * @Title: downloadMultipartFileByOss
     * @Description: 批量下载阿里云文件到本地
     * @Author: lxt
     * @param: urlList 阿里云链接集合
     * @param: dir 下载目录
     * @Date: 2019-02-18 13:19
     * @return: java.util.List<java.io.File>
     * @throws:
     */
    public static List<File> downloadMultipartFile(List<String> urlList,String dir){
        List<File> files = new ArrayList<>();
        Optional.ofNullable(urlList).ifPresent(fl->{
                    fl.stream().forEach(f->files.add(OssUtil.download2Dir(f,dir)));
                }
        );
        return files;
    }
    /**
     * @Title: downloadMultipartFileByOssWithZip
     * @Description: 批量下载，打包成一个zip包
     * @Author: lxt 
     * @param: urlList
     * @param: zipPath
     * @Date: 2019-02-18 15:41 
     * @return: java.io.File
     * @throws: 
     */
    public static File downloadMultipartFileWithZip(List<String> urlList,String zipName){
        try {
            //压缩路径不存在，先创建
            File zipDirFile = new File(FileConstants.DOWNLOAD);
            if(!zipDirFile.exists()){
                zipDirFile.mkdirs();
            }
            if(StringUtils.isNoneBlank(zipName) && !isFileBySuffix(zipName,FileConstants.SUFFIX_ZIP)){
                //文件名称存在 但后缀名不是zip
                zipName = zipName + FileConstants.SUFFIX_ZIP;
            }else{
                // 压缩包默认名称未6为随机字符串
                zipName = StringUtils.isBlank(zipName) ? OssUtil.getRandomStrByNum(6)+FileConstants.SUFFIX_ZIP : zipName;
            }
            // 批量下载文件到指定位置
            List<File> files = downloadMultipartFile(urlList,FileConstants.DOWNLOAD);
            // 将文件打包
            File zipFile = ZipFileUtil.compressFiles2Zip(files,FileConstants.DOWNLOAD+File.separator+zipName);
            // 删除打包之前的文件
            files.stream().forEach(f->f.delete());
            return zipFile;
        }catch (Exception e){
            logger.error("批量下载文件异常",e);
        }
        return null;
    }

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
