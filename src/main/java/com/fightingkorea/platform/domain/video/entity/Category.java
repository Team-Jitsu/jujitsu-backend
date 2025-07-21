package com.fightingkorea.platform.domain.video.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId; // 카테고리 아이디

    @Column
    private String categoryName; // 카테고리 이름

    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public static Category createCategory(String categoryName) {

        return new Category(categoryName);
    }

}
