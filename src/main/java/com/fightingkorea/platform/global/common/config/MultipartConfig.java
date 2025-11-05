package com.fightingkorea.platform.global.common.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

/**
 * Multipart 파일 업로드 설정
 * 모든 파일을 디스크에 저장하여 메모리 사용량을 최소화합니다.
 */
@Configuration
public class MultipartConfig {

    @Value("${spring.servlet.multipart.max-file-size:2GB}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size:2GB}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        
        // 파일 크기 제한
        factory.setMaxFileSize(DataSize.parse(maxFileSize));
        factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
        
        // 0으로 설정하여 모든 파일을 디스크에 저장 (메모리에 저장하지 않음)
        // 이렇게 하면 대용량 파일도 메모리 부족 없이 처리 가능
        factory.setFileSizeThreshold(DataSize.ofBytes(0));
        
        // 임시 파일 저장 위치 (시스템 임시 디렉토리)
        factory.setLocation(System.getProperty("java.io.tmpdir"));
        
        return factory.createMultipartConfig();
    }
}

