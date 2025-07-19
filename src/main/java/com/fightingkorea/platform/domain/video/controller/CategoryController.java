package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import com.fightingkorea.platform.domain.video.dto.VideoCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final VideoCategoryService videoCategoryService;

    @PostMapping // 카테고리 생성
    public CategoryResponse createCategory(@RequestBody String categoryName) {
       return categoryService.createCategory(categoryName);
    }

    @GetMapping // 전체 카테고리 목록 조회
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @DeleteMapping("/{category-id}") // 카테고리 삭제
    public void deleteCategory(@PathVariable("category-id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping // 특정 비디오의 카테고리 목록 조회
    public List<VideoCategoryResponse> getAllVideoCategory(){
        return videoCategoryService.getAllVideoCategory();
    }
}
