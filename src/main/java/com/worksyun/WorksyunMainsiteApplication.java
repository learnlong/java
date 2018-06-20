package com.worksyun;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorksyunMainsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorksyunMainsiteApplication.class, args);
	}
	
	
	/**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("3MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("10240KB");  
        return factory.createMultipartConfig();  
    }
    
   /* @Bean  
    public ServletRegistrationBean jersetServlet(){  
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/api/*");  
        // our rest resources will be available in the path /jersey/*  
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());  
        return registration;  
    } */
}
