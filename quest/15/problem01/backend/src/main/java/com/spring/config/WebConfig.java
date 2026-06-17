package com.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO backend-0: React 개발 서버에서 API를 호출할 수 있도록 CORS를 설정하세요.
        // 힌트:
        //   registry.addMapping("/api/**")          → /api/ 경로 전체에 CORS 허용
        //     .allowedOrigins("http://localhost:5173") → React 개발서버 주소
        //     .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") → 허용할 HTTP 메서드
    }
}
