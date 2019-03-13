package com.lxt.file.config;

import ch.qos.logback.core.util.FileUtil;
import com.lxt.file.util.LocalFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${lxt.file.static}")
    private String staticDir;

    /**
     * @Title: addResourceHandlers
     * @Description:  映射本地磁盘为静态目录
     * @param: registry
     * @throws:
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        LocalFileUtil.staticDir = staticDir;
        registry.addResourceHandler(LocalFileUtil.UPLOAD_FILE_SUFFIX +"/**").addResourceLocations("file:"+staticDir);
    }
}
