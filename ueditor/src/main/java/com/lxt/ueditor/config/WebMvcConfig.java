package com.lxt.ueditor.config;

import com.lxt.ueditor.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring mvc 配置
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	/**
	 * 文件上传路径前缀
	 */
	@Value("${file.upload.suffix.path}")
	public String uploadSuffixPath;
	/**
	 * 本地磁盘目录
	 */
	@Value("${file.upload.local.path}")
	public String uploadLocalPath;
	/**
	 * @Title: addResourceHandlers
	 * @Description:  映射本地磁盘为静态目录
	 * @param: registry
	 * @throws:
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		FileUtil.uploadSuffixPath = uploadSuffixPath;
		FileUtil.uploadLocalPath = uploadLocalPath;
		registry.addResourceHandler(uploadSuffixPath +"/**").addResourceLocations("file:"+uploadLocalPath);
	}
}
