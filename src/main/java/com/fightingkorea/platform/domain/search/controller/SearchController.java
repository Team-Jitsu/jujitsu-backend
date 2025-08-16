package com.fightingkorea.platform.domain.search.controller;

import com.fightingkorea.platform.domain.search.dto.SearchRequest;
import com.fightingkorea.platform.domain.search.dto.SearchResponse;
import com.fightingkorea.platform.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SearchResponse search(
            @RequestParam String q,
            @RequestParam(required = false, defaultValue = "all") String type,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        int p = (page == null || page < 1) ? 0 : page - 1;
        int size = (perPage == null) ? 20 : Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(p, size);

        SearchRequest request = SearchRequest.builder()
                .q(q)
                .type(type)
                .categoryId(categoryId)
                .specialtyId(specialtyId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        return searchService.search(request, pageable);
    }
}
