package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoSearchRequest;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import com.fightingkorea.platform.domain.video.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final VideoService videoService;

    @PostMapping // 카테고리 생성asdasdasdasdasd
    public CategoryResponse createCategory(@RequestBody String categoryName) {
       return categoryService.createCategory(categoryName);
    }

    @GetMapping // 전체 카테고리 목록 조회
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{categoryId}/videos")
    public Page<VideoResponse> getCategoryVideos(
            @PathVariable Long categoryId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false, defaultValue = "latest") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
    ) {
        int p = (page == null || page < 1) ? 0 : page - 1;
        int size = (perPage == null) ? 20 : Math.min(perPage, 100);

        String sortField;
        switch (sortBy) {
            case "price":
                sortField = "price";
                break;
            case "popularity":
                sortField = "likesCount";
                break;
            default:
                sortField = "uploadTime";
        }

        Sort sort = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(p, size, sort);

        VideoSearchRequest request = VideoSearchRequest.builder()
                .categoryId(categoryId)
                .trainerId(trainerId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        return videoService.getVideos(request, pageable);
    }

    @DeleteMapping("/{category-id}") // 카테고리 삭제
    public void deleteCategory(@PathVariable("category-id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
