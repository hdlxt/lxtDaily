package com.mbyte.easy.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ClassName: FileUtil
 * @Description: 文件上传工具类
 * @Version 1.0
 **/
public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 文件上传路径前缀
     */
    public static String uploadSuffixPath;
    /**
     * 本地磁盘目录
     */
    public static String uploadLocalPath;
    /**
     * @Title: uploadFile
     * @Description: 单文件上传到本地磁盘
     * @param: multipartFile
     * @return: java.lang.String
     * @throws:
     */
    public static String uploadFile(MultipartFile multipartFile){
        if(multipartFile == null){
            return null;
        }
        //获取文件相对路径
        String fileName = getUploadFileName(multipartFile.getOriginalFilename());
        String dateDir = DateUtil.format(null,DateUtil.PATTERN_yyyyMMdd);
        File destFileDir = new File(uploadLocalPath + File.separator + dateDir);
        if(!destFileDir.exists()){
            destFileDir.mkdirs();
        }
        try {
            File destFile = new File(destFileDir.getAbsoluteFile()+File.separator+fileName);
            multipartFile.transferTo(destFile);
            logger.info("文件【"+multipartFile.getOriginalFilename()+"】上传成功");
            return dateDir+"/"+fileName;
        } catch (IOException e) {
            logger.error("文件上传异常："+multipartFile.getOriginalFilename(),e);
            return null;
        }
    }
    /**
     * @Title: getUploadFilePath
     * @Description: 获取上传后的文件相对路径  --数据库存储该路径
     * @param: fileName
     * @return: java.lang.String
     * @throws:
     */
    public static String getUploadFileName(String fileName){
        return new StringBuilder()
                .append(DateUtil.format(null, DateUtil.PATTERN_yyyyMMddHHmmssSSS))
                .append("_").append(Utility.getRandomStrByNum(6))
                .append(".").append(FilenameUtils.getExtension(fileName))
                .toString();
    }

    /**
     * @Title: isFileBySuffix
     * @Description: 通过后缀名判断是否是某种文件
     * @param: fileName 文件名称
     * @param: suffix 后缀名
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
     * 获取后缀名
     * @param fileName
     * @param suffix
     * @return
     */
    public static String getSuffix(String fileName){
        if(StringUtils.isNoneBlank(fileName)){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
    /**
     * @Title: downloadFile
     * @Description: 文件下载工具类
     * @param: filePath
     * @throws:
     */
    public static void downloadFile(String filePath,String fileName, HttpServletResponse response){
        try(
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(uploadLocalPath+File.separator+filePath));
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(StringUtils.isBlank(fileName) ? Utility.getRandomStrByNum(6) : fileName, "UTF-8"));
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))){
                outputStream.write(buff, 0, bytesRead);
            }
            outputStream.flush();
            logger.info("文件【"+filePath+"】下载成功");
        } catch (Exception e) {
            logger.error("下载异常！", e);
        }
    }

}
