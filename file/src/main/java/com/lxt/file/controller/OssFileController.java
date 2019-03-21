package com.lxt.file.controller;

import com.lxt.file.constants.FileConstants;
import com.lxt.file.model.FileModel;
import com.lxt.file.util.LocalFileUtil;
import com.lxt.file.util.OssFileUtil;
import com.lxt.file.util.OssUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FileController
 * @Description: 文件操作和测试工具类
 * @Version 1.0
 **/
@Controller
@RequestMapping("ossFile")
public class OssFileController {
    private final static Logger logger = LoggerFactory.getLogger(OssFileController.class);
    private String base = "cd/test/";
    @GetMapping()
    public String file(){
        return base+"test-file";
    }


    /**
     * @Title: uploadSingleFile
     * @Description: 单文件上传
     * @Author: lxt
     * @param: file
     * @Date: 2019-02-15 14:50
     * @return: java.lang.String 成功返回 文件路径，失败返回null
     * @throws:
     */
    @PostMapping("/uploadSingleFile")
    @ResponseBody
    public String uploadSingleFile(MultipartFile file){
        return OssFileUtil.uploadSingleFile(file);
    }

    @PostMapping("/uploadSingleFile2Pro")
    public String uploadSingleFile2Pro(Model model, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "test-file";
        }

        String fileName = file.getOriginalFilename();
        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/upload/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            model.addAttribute("result","上传成功！");
            return "test-file";
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("result","上传失败！");
        return "test-file";
    }

    /**
     * @Title: downloadSingleFile
     * @Description: 下载单个文件到本地
     * @Author: lxt
     * @param: url
     * @param: fileName
     * @param: request
     * @param: response
     * @Date: 2019-02-18 19:24
     * @throws:
     */
    @PostMapping("/downloadSingleFile")
    public void downloadSingleFile(String url, String fileName, HttpServletRequest request,
                                   HttpServletResponse response){
        // 浏览器下载阿里云文件到本地
        OssUtil.download2FileByStream(url,fileName,response);
    }
    /**
     * @Title: downloadMultipartFileWithZip
     * @Description: 批量下载，将文件打包成zip格式压缩包
     * @Author: lxt
     * @param: urlList
     * @param: zipName
     * @param: request
     * @param: response
     * @Date: 2019-02-18 23:24
     * @throws:
     */
    @PostMapping("downloadMultipartFileWithZip")
    public void downloadMultipartFileWithZip(FileModel fileModel, String zipName, HttpServletRequest request,
                                             HttpServletResponse response){
        // 下载阿里云文件到本地
        File zipFile = OssFileUtil.downloadMultipartFileWithZip(fileModel.getUrlList(),zipName);
        try(
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile));
        ){

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            if(StringUtils.isNoneBlank(zipName) && !OssFileUtil.isFileBySuffix(zipName, FileConstants.SUFFIX_ZIP)){
                //文件名称存在 但后缀名不是zip
                zipName = zipName + FileConstants.SUFFIX_ZIP;
            }else{
                // 压缩包默认名称6位随机字符串
                zipName = StringUtils.isBlank(zipName) ? OssUtil.getRandomStrByNum(6)+ FileConstants.SUFFIX_ZIP : zipName;
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


//    /**
//     * @Title: uploadFile
//     * @Description: 上传文件
//     * @param: multipartFile
//     * @return: java.lang.String
//     * @throws:
//     */
//    @PostMapping("uploadFile")
//    public String uploadFile(MultipartFile multipartFile){
//        LocalFileUtil.uploadFile(multipartFile);
//        return base+"test-file";
//    }
//    /**
//     * @Title: downloadFile
//     * @Description: 下载文件
//     * @param: filePath
//     * @param: fileName
//     * @param: response
//     * @return: java.lang.String
//     * @throws:
//     */
//    @PostMapping("downloadFile")
//    public void downloadFile(String filePath,String fileName, HttpServletResponse response){
//        LocalFileUtil.downloadFile(filePath,fileName,response);
//    }
//    @PostMapping("downloadMultipartFileWithZip")
//    public void downloadMultipartFileWithZip(String zipName, HttpServletResponse response){
//        List<String> filePathList = new ArrayList<>();
//        filePathList.add("20190308/20190308131018881_5P4CGU.png");
//        filePathList.add("20190308/20190308130337092_W9HT1G.png");
//        LocalFileUtil.downloadMultipartFileWithZip(filePathList,zipName,response,new File(""));
//    }

}
