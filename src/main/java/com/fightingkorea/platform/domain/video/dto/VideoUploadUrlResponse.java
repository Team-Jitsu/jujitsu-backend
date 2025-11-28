package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VideoUploadUrlResponse {
    
    private String uploadUrl; // S3 Presigned PUT URL
    private String s3Key; // S3 키 (업로드 완료 후 사용)
    private Instant expiresAt; // URL 만료 시간
}

