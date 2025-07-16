package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.entity.Category;
import com.fightingkorea.platform.domain.video.entity.VideoCategory;
import com.fightingkorea.platform.domain.video.exception.CategoryConflictException;
import com.fightingkorea.platform.domain.video.exception.CategoryNotFoundException;
import com.fightingkorea.platform.domain.video.repository.CategoryRepository;
import com.fightingkorea.platform.domain.video.repository.VideoCategoryRepository;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final VideoCategoryRepository videoCategoryRepository;

    // 전체 카테고리 목록 조회
    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryName"));

        log.info("전체 비디오 카테고리 목록 조회 완료, 총 개수: {}", categories.size());

        return categories.stream()
                .map(ResponseMapper::toCategoryResponse)
                .toList();
    }

    // 특정 비디오의 카테고리 설정 (기존 데이터 삭제 후 재등록)
    @Override
    public void setVideoCategory(Long videoId, List<Long> categoryIds) {
        videoCategoryRepository.deleteByVideoId(videoId);
        log.info("기존 카테고리 삭제 완료, 비디오 ID: {}", videoId);

        List<VideoCategory> videoCategories = categoryIds.stream()
                .map(categoryId -> VideoCategory.createVideoCategory(
                        categoryId,
                        videoId
                ))
                .toList();

        videoCategoryRepository.saveAll(videoCategories);

        log.info("비디오의 카테고리 설정 완료, 비디오 ID: {}, 등록 개수 : {}", videoId, videoCategories.size());
    }

    // 카테고리 생성
    @Override
    public CategoryResponse createCategory(String categoryName) {
        if (Boolean.TRUE.equals(categoryRepository.existsByCategoryName(categoryName))) {
            throw new CategoryConflictException();
        }

        Category category = categoryRepository.save(new Category(categoryName));

        log.info("카테고리 등록 완료, ID: {}, 이름: {}", category.getCategoryId(), categoryName);

        return ResponseMapper.toCategoryResponse(category);
    }

    // 카테고리 삭제
    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }

        categoryRepository.deleteById(categoryId);
        log.info("카테고리 삭제 완료, ID: {}", categoryId);
    }
}
