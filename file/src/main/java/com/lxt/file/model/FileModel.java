package com.lxt.file.model;

import java.util.List;

/**
 * @ClassName: FileModel
 * @Description: 文件操作模型类
 * @Author: lxt
 * @Date: 2019-02-19 09:43
 * @Version 1.0
 **/
//@Data
public class FileModel {
    /**
     * oss链接
     */
    private List<String> urlList;

    public List<String> getUrlList() {
        return urlList;
    }
    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
}
