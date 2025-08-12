package com.fightingkorea.platform.domain.video.controller;

import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping // 카테고리 생성asdasdasdasdasd
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
}
