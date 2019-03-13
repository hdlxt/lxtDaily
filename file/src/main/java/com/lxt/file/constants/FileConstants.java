package com.lxt.file.constants;

import org.springframework.util.ClassUtils;

import java.io.File;

/**
 * @ClassName: FileConstants
 * @Description: 文件常量
 * @Author: lxt
 * @Date: 2019-02-19 09:59
 * @Version 1.0
 **/
public class FileConstants {
    /**
     * 文件存储临时文件夹
     */
    public final static String TEMP_ROOT = "temp";
    /**
     * 下载暂存目录
     */
    public final static String DOWNLOAD = TEMP_ROOT+ File.separator+"download";
    /**
     * 下载对账单暂存目录
     */
    public final static String BILL = TEMP_ROOT+ File.separator+"bill";
    /**
     * 后缀名相关常量(包含【.】)
     */
    public final static String SUFFIX_ZIP = ".zip";
    /**
     * png图片后缀
     */
    public final static String SUFFIX_png = ".png";
    /**
     * 二维码暂存路径 eg：微信支付二维码
     */
    public static final String QRCODE = "qrcode";
    public static final String QRCODE_PATH = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+File.separator+QRCODE;

}
