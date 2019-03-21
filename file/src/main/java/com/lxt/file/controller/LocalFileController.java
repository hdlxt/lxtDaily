package com.lxt.file.controller;

import com.lxt.file.util.LocalFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FileController
 * @Description: 文件操作和测试工具类
 * @Version 1.0
 **/
@Controller
@RequestMapping("localFile")
public class LocalFileController {
    private final static Logger logger = LoggerFactory.getLogger(LocalFileController.class);
    private String base = "cd/test/";
    @GetMapping()
    public String file(){
        return base+"test-file";
    }
    /**
     * @Title: uploadFile
     * @Description: 上传文件
     * @param: multipartFile
     * @return: java.lang.String
     * @throws: 
     */
    @PostMapping("uploadFile")
    public String uploadFile(MultipartFile multipartFile){
        LocalFileUtil.uploadFile(multipartFile);
        return base+"test-file";
    }
    /**
     * @Title: downloadFile
     * @Description: 下载文件
     * @param: filePath
     * @param: fileName
     * @param: response
     * @return: java.lang.String
     * @throws:
     */
    @PostMapping("downloadFile")
    public void downloadFile(String filePath,String fileName, HttpServletResponse response){
        LocalFileUtil.downloadFile(filePath,fileName,response);
    }
    @PostMapping("downloadMultipartFileWithZip")
    public void downloadMultipartFileWithZip(String zipName, HttpServletResponse response){
        List<String> filePathList = new ArrayList<>();
        filePathList.add("20190308/20190308131018881_5P4CGU.png");
        filePathList.add("20190308/20190308130337092_W9HT1G.png");
        LocalFileUtil.downloadMultipartFileWithZip(filePathList,zipName,response);
    }

}
