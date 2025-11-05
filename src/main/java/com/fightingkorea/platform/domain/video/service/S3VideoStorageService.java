package com.fightingkorea.platform.domain.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3VideoStorageService {

    private final S3Client s3;
    private final S3Presigner presigner;

    @Value("${app.s3.bucket}")
    private String bucket;

    @Value("${app.s3.basePath:videos}")
    private String basePath;

    @Value("${app.s3.presignTtlSeconds:3600}")
    private long defaultTtl;

    /** 새 동영상 S3 키 생성 */
    public String newVideoKey(String originalFilename) {
        String ext = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf('.') + 1)
                : "mp4";
        return "%s/%s.%s".formatted(basePath, UUID.randomUUID(), ext.toLowerCase());
    }

    /** 서버에서 직접 업로드 (byte 배열 사용 - 작은 파일용) */
    public void putObject(String key, byte[] bytes, String contentType) {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket).key(key)
                .contentType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .build();
        s3.putObject(req, RequestBody.fromBytes(bytes));
    }

    /** 서버에서 직접 업로드 (InputStream 사용 - 대용량 파일 스트리밍) */
    public void putObject(String key, InputStream inputStream, long contentLength, String contentType) {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket).key(key)
                .contentType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .build();
        s3.putObject(req, RequestBody.fromInputStream(inputStream, contentLength));
    }

    /** 클라이언트 직업로드용 Pre-Signed PUT URL */
    public PresignedUpload createPresignedPut(String key, String contentType, Duration ttl) {
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(bucket).key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(ttl != null ? ttl : Duration.ofSeconds(defaultTtl))
                .putObjectRequest(put)
                .build();

        URL url = presigner.presignPutObject(presignReq).url();
        return new PresignedUpload(url.toString(), Instant.now().plus(presignReq.signatureDuration()));
    }

    /** 재생/다운로드용 Pre-Signed GET URL */
    public PresignedRead createPresignedGet(String key, Duration ttl) {
        GetObjectRequest get = GetObjectRequest.builder()
                .bucket(bucket).key(key).build();

        GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                .signatureDuration(ttl != null ? ttl : Duration.ofSeconds(defaultTtl))
                .getObjectRequest(get)
                .build();

        URL url = presigner.presignGetObject(presignReq).url();
        return new PresignedRead(url.toString(), Instant.now().plus(presignReq.signatureDuration()));
    }

    public record PresignedUpload(String url, Instant expiresAt) {}
    public record PresignedRead(String url, Instant expiresAt) {}
}
