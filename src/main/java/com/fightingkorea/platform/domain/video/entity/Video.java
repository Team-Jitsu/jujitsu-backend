package com.fightingkorea.platform.domain.video.entity;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.video.dto.VideoRegisterRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "video_id")
    private Long videoId; // 비디오 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "trainer_id",
            referencedColumnName = "trainer_id", // 명시적 참조 컬럼 지정
            foreignKey = @ForeignKey(name = "videos_ibfk_1") // FK 이름 지정
    )
    private Trainer trainer; // 업로더

    @OneToMany(mappedBy = "video", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<UserVideo> userVideos = new ArrayList<>(); // 강의 구매자 목록

    @Column(length = 100, nullable = false)
    private String title; // 동영상 제목

    @Column(length = 100, nullable = false)
    private String url; // 영상 파일 경로

    @Column(columnDefinition = "text")
    private String description; // 설명

    @Column(name = "upload_time" , nullable = false)
    private LocalDateTime uploadTime; // 업로드 시간

    @Column(nullable = false)
    private Integer price; // 가격

    @Column(name = "likes_count")
    private Integer likesCount = 0 ; // 좋아요

    public static Video createVideo(VideoRegisterRequest req, Trainer trainer) {
        return Video
                .builder()
                .trainer(trainer)
                .title(req.getTitle())
                .url(req.getUrl())
                .description(req.getDescription())
                .price(req.getPrice())
                .likesCount(0)
                .build();
    }

    // 동영상 경로 변경
    public void updateUrl(String url){
        this.url = url;
    }

    // 동영상 가격 업데이트
    public void updatePrice(Integer price){
        this.price = price;
    }


    // 비디오 정보 업데이트
    public void updateVideo(String title, String description, Integer price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    // 엔티티 저장 전 업로드 시간 자동 설정
    @PrePersist
    public void prePersist(){
        uploadTime = LocalDateTime.now();
    }


}
