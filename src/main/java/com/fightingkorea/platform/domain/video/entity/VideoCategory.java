package com.fightingkorea.platform.domain.video.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "video_categories")
@IdClass(VideoCategoryId.class)
public class VideoCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId; // 동영상 아이디

    @Column
    private Long categoryId; // 카테고리 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static VideoCategory createVideoCategory(Long videoId, Long categoryId){
        return new VideoCategory(videoId, categoryId, null, null);
    }

}
