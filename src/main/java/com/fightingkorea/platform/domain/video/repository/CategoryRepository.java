package com.fightingkorea.platform.domain.video.repository;

import com.fightingkorea.platform.domain.video.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByCategoryName(String categoryName);

    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
}
