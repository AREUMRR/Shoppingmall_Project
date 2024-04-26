package com.example.basic.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //파일업로드 변수 설정
    @Value("${uploadPath}")
    String uploadPath;

    //자원 추가
    //C:/salad/를 resources images폴더로 연결해서 사용
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
