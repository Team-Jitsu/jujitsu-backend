package com.fightingkorea.platform.domain.video.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VideoUploadUrlRequest {
    
    @NotBlank
    private String filename; // 업로드할 파일명
    
    @NotBlank
    private String contentType; // 파일 타입 (예: video/mp4)
}

