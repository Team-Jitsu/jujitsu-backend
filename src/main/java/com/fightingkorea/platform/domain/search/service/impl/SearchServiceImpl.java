package com.fightingkorea.platform.domain.search.service.impl;

import com.fightingkorea.platform.domain.search.dto.SearchRequest;
import com.fightingkorea.platform.domain.search.dto.SearchResponse;
import com.fightingkorea.platform.domain.search.service.SearchService;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerSearchRequest;
import com.fightingkorea.platform.domain.trainer.service.TrainerService;
import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoSearchRequest;
import com.fightingkorea.platform.domain.video.entity.Category;
import com.fightingkorea.platform.domain.video.repository.CategoryRepository;
import com.fightingkorea.platform.domain.video.service.VideoService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final VideoService videoService;
    private final TrainerService trainerService;
    private final CategoryRepository categoryRepository;

    @Override
    public SearchResponse search(SearchRequest request, Pageable pageable) {
        List<VideoResponse> videos = Collections.emptyList();
        List<TrainerResponse> trainers = Collections.emptyList();
        List<CategoryResponse> categories = Collections.emptyList();

        String type = request.getType() == null ? "all" : request.getType().toLowerCase();

        if ("all".equals(type) || "videos".equals(type)) {
            VideoSearchRequest videoReq = VideoSearchRequest.builder()
                    .search(request.getQ())
                    .categoryId(request.getCategoryId())
                    .minPrice(request.getMinPrice())
                    .maxPrice(request.getMaxPrice())
                    .build();
            videos = videoService.getVideos(videoReq, pageable).getContent();
        }

        if ("all".equals(type) || "trainers".equals(type)) {
            TrainerSearchRequest trainerReq = TrainerSearchRequest.builder()
                    .search(request.getQ())
                    .specialtyId(request.getSpecialtyId())
                    .build();
            trainers = trainerService.getTrainers(trainerReq, pageable).getContent();
        }

        if ("all".equals(type) || "categories".equals(type)) {
            List<Category> found = categoryRepository.findByCategoryNameContainingIgnoreCase(request.getQ());
            categories = found.stream()
                    .map(c -> new CategoryResponse(c.getCategoryId(), c.getCategoryName()))
                    .collect(Collectors.toList());
        }

        return SearchResponse.builder()
                .videos(videos)
                .trainers(trainers)
                .categories(categories)
                .build();
    }
}
