package com.mycroft.sbdj;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		// TODO Auto-generated method stub
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		log.info("resourcePath ================> " + resourcePath);
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
//	}
}
