package com.fightingkorea.platform.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UploadInitResponse {
    private String key;            // S3 object key
    private String uploadUrl;      // PUT presigned URL
    private Instant expiresAt;     // 만료시각
}
