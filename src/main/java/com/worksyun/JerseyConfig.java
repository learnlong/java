package com.worksyun;

import java.util.stream.Collectors;


import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import com.worksyun.api.jersey.MifiNotController;

public class JerseyConfig{ //extends ResourceConfig{
	
	 public JerseyConfig() {  
		 
		/* ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		    // add more annotation filters if you need
		    scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
		    scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
		    this.registerClasses(scanner.findCandidateComponents("com.worksyun.api.jersey").stream()
		        .map(beanDefinition -> ClassUtils
		            .resolveClassName(beanDefinition.getBeanClassName(), this.getClassLoader()))
		        .collect(Collectors.toSet()));
*/
         
         //配置restful package.  
         //packages("com.worksyun.api.jersey");  
      }  
}
