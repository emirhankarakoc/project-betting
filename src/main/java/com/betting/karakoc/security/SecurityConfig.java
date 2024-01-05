package com.betting.karakoc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("https://bettting.ey.r.appspot.com/")
                .allowedMethods("GET","POST","PUT","DELETE"); // Sadece GET isteğine izin ver}
    }
}