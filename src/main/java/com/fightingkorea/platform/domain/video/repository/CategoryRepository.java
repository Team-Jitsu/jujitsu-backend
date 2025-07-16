package com.fightingkorea.platform.domain.video.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fightingkorea.platform.domain.video.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Boolean existsByCategoryName(String categoryName);
}