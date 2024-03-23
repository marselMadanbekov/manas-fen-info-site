package com.example.manasfen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {
    @Value("${custom.parameters.upload-photo-directory}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/image/**")
                .addResourceLocations("file://" + uploadDirectory);
        resourceHandlerRegistry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        resourceHandlerRegistry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");
        resourceHandlerRegistry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        resourceHandlerRegistry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        resourceHandlerRegistry.addResourceHandler("/js1/**")
                .addResourceLocations("classpath:/static/js1/");
        resourceHandlerRegistry.addResourceHandler("/plugins/**")
                .addResourceLocations("classpath:/static/plugins/");
        resourceHandlerRegistry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/static/styles/");
        resourceHandlerRegistry.addResourceHandler("/vendors/**")
                .addResourceLocations("classpath:/static/vendors/");
    }
}
