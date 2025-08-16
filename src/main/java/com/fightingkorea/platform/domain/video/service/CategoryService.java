package com.fightingkorea.platform.domain.video.service;

import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.CategoryStatsResponse;

import java.util.List;

public interface CategoryService {

    // 전체 카테고리 목록 조회
    List<CategoryResponse> getAllCategory();

    // 특정 비디오의 카테고리 설정 (기존 데이터 삭제 후 재등록)
    void setVideoCategory(Long videoId, List<Long> categoryIds);

    // 카테고리 생성
    CategoryResponse createCategory(String categoryName);

    // 카테고리 삭제
    void deleteCategory(Long categoryId);

    // 카테고리 통계 조회
    CategoryStatsResponse getCategoryStats(Long categoryId);
}
