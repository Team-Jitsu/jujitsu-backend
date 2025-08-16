package com.fightingkorea.platform.domain.search.service;

import com.fightingkorea.platform.domain.search.dto.SearchRequest;
import com.fightingkorea.platform.domain.search.dto.SearchResponse;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    SearchResponse search(SearchRequest request, Pageable pageable);
}
