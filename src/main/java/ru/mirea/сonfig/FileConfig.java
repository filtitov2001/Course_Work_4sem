package ru.mirea.сonfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;


@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    private String truePath;


    @PostConstruct
    public void init() {
        String path = System.getProperty("user.dir")
                .replace('\\', '/') + "/src/main/resources/";
        truePath = '/' + path + uploadPath + "/images/";

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file://" + truePath);


    }
}