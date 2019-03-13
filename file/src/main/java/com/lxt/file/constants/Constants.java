package com.lxt.file.constants;

import java.io.File;

/**
 * @ClassName: Constants
 * @Description: 全局常量
 * @Version 1.0
 **/
public class Constants {
    /**
     * 当前页
     */
    public final static String PAGE_NO = "pageNo";
    /**
     * 每页显示记录数
     */
    public final static String PAGE_SIZE = "pageSize";
    /**
     * 每页显示记录数默认值
     */
    public final static Integer PAGE_SIZE_DEFAULT = 10;
    /**
     * 文件存储临时文件夹
     */
    public final static String TEMP_ROOT = "temp";
    /**
     * 下载暂存目录
     */
    public final static String DOWNLOAD = TEMP_ROOT+ File.separator+"download";
    /**
     * 后缀名相关常量(包含【.】)
     */
    public final static String SUFFIX_ZIP = ".zip";
    /**
     *  UTF-8
     */
    public final static String UTF_8 = "UTF-8";
    /**
     *  GBK
     */
    public final static String GBK = "GBK";
    /**
     *  ISO-8859-1
     */
    public final static String ISO_8859_1 = "ISO-8859-1";


}
