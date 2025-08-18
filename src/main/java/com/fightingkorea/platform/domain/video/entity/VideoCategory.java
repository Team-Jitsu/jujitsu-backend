package com.fightingkorea.platform.domain.video.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "video_categories")
@IdClass(VideoCategoryId.class)
public class VideoCategory {

    @Id
    @Column(name = "video_id")
    private Long videoId; // 동영상 아이디

    @Id
    @Column(name = "category_id")
    private Long categoryId; // 카테고리 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    public VideoCategory(Long videoId, Long categoryId) {
        this.videoId = videoId;
        this.categoryId = categoryId;
    }


    public static VideoCategory createVideoCategory(Long videoId, Long categoryId){
        return new VideoCategory(videoId, categoryId);
    }

}
