package com.worksyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvc配置
 *
 * @auth:mingfly
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@ConfigurationProperties(prefix = "fileupload")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePath = "file:" + path;
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/opt/worksyun/Files/");
        //registry.addResourceHandler("/upload/**").addResourceLocations(filePath);
      /*  registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");*/
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许跨域
        registry.addMapping("/**").allowedOrigins("*");
    }
    
    
 /*   @Override  
    public void addViewControllers(ViewControllerRegistry registry) {  
        registry.addViewController("/error").setViewName("error.html");  
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);  
    }
    
    @Override  
    public void configurePathMatch(PathMatchConfigurer configurer) {  
        super.configurePathMatch(configurer);  
        configurer.setUseSuffixPatternMatch(false);  
    }*/

}
