package com.lxt.file.util;

import com.lxt.file.constants.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: LocalFileUtil
 * @Description: 文件上传工具类
 * @Version 1.0
 **/
public class LocalFileUtil {
    private final static Logger logger = LoggerFactory.getLogger(LocalFileUtil.class);
    /**
     * 文件上传路径前缀
     */
    public final static String UPLOAD_FILE_SUFFIX ="/fileDisk";
    /**
     * 本地磁盘目录
     */
    public static String staticDir;
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
        String filePath = getUploadFilePath(multipartFile.getOriginalFilename());
        logger.info("filePath："+filePath);
        File destFile = new File(staticDir + File.separator + filePath);
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(destFile);
            logger.info("文件【"+multipartFile.getOriginalFilename()+"】上传成功");
            return filePath;
        } catch (IOException e) {
            logger.error("文件上传异常："+multipartFile.getOriginalFilename(),e);
            return null;
        }
    }
    /**
     * @Title: downloadFile
     * @Description: 文件下载工具类
     * @param: filePath
     * @throws:
     */
    public static void downloadFile(String filePath,String fileName, HttpServletResponse response){
        try(
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(staticDir+File.separator+filePath));
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(StringUtils.isBlank(fileName) ? OssUtil.getRandomStrByNum(6) : fileName, "UTF-8"));
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
    /**
     * @Title: getUploadFilePath
     * @Description: 获取上传后的文件相对路径  --数据库存储该路径
     * @param: fileName
     * @return: java.lang.String
     * @throws:
     */
    public static String getUploadFilePath(String fileName){
        return new StringBuilder(LxtDateUtil.format(null,LxtDateUtil.PATTERN_yyyyMMdd))
                .append( File.separator)
                .append(LxtDateUtil.format(null,LxtDateUtil.PATTERN_yyyyMMddHHmmssSSS))
                .append("_").append(OssUtil.getRandomStrByNum(6))
                .append(".").append(FilenameUtils.getExtension(fileName))
                .toString();
    }
    /**
     * @Title: downloadMultipartFile
     * @Description: 批量下载文件爱你下载到本地
     * @param: urlList
     * @param: dir
     * @return: java.util.List<java.io.File>
     * @throws:
     */
    public static List<File> downloadMultipartFile(List<String> filePathList,String dir){
        List<File> files = new ArrayList<>();
        Optional.ofNullable(filePathList).ifPresent(fl->{
                    fl.stream().forEach(f->files.add(new File(staticDir+File.separator+f)));
                }
        );
        return files;
    }

    /**
     * @Title: downloadMultipartFileWithZip
     * @Description: 批量下载，打包成一个zip包
     * @param: filePathList
     * @param: zipName
     * @return: java.io.File
     * @throws:
     */
    public static File downloadMultipartFileWithZip(List<String> filePathList,String zipName,File excelFile){
        try {
            //压缩路径不存在，先创建
            File zipDirFile = new File(Constants.DOWNLOAD);
            if(!zipDirFile.exists()){
                zipDirFile.mkdirs();
            }
            if(StringUtils.isNoneBlank(zipName) && !isFileBySuffix(zipName,Constants.SUFFIX_ZIP)){
                //文件名称存在 但后缀名不是zip
                zipName = zipName + Constants.SUFFIX_ZIP;
            }else{
                // 压缩包默认名称未6为随机字符串
                zipName = StringUtils.isBlank(zipName) ? OssUtil.getRandomStrByNum(6)+Constants.SUFFIX_ZIP : zipName;
            }
            // 批量下载文件到指定位置
            List<File> files = downloadMultipartFile(filePathList,Constants.DOWNLOAD);
            files.add(excelFile);
            // 将文件打包
            File zipFile = ZipFileUtil.compressFiles2Zip(files,Constants.DOWNLOAD+File.separator+zipName);
            return zipFile;
        }catch (Exception e){
            logger.error("批量下载文件异常",e);
        }
        return null;
    }

    public static void downloadMultipartFileWithZip(List<String> filePathList, String zipName, HttpServletResponse response, File excelFile){
        // 下载阿里云文件到本地
        File zipFile = downloadMultipartFileWithZip(filePathList,zipName,excelFile);
        try(
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile));
        ){

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            if(StringUtils.isNoneBlank(zipName) && !isFileBySuffix(zipName,Constants.SUFFIX_ZIP)){
                //文件名称存在 但后缀名不是zip
                zipName = zipName + Constants.SUFFIX_ZIP;
            }else{
                // 压缩包默认名称6位随机字符串
                zipName = StringUtils.isBlank(zipName) ? OssUtil.getRandomStrByNum(6)+ Constants.SUFFIX_ZIP : zipName;
            }
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipName, "UTF-8"));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))){
                outputStream.write(buff, 0, bytesRead);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("下载异常！", e);
        }finally {
            //下载完成，删除临时文件
            zipFile.delete();
        }
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

}
