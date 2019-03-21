package com.lxt.file.controller;

import com.lxt.file.constants.FileConstants;
import com.lxt.file.model.FileModel;
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

/**
 * @ClassName: UploadController
 * @Description: 文件上传下载控制类
 * @Author: lxt
 * @Date: 2019-02-15 14:41
 * @Version 1.0
 **/
@Controller
@RequestMapping
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
    @GetMapping
    public String test(Model model){
        return "test";
    }

    @GetMapping("testLocal")
    public String testLocal(Model model){
        return "test-local";
    }

    @GetMapping("testOss")
    public String testOss(Model model){
        return "test-oss";
    }

}
