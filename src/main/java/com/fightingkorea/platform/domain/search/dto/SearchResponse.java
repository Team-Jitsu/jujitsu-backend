package com.fightingkorea.platform.domain.search.dto;

import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {
    private List<VideoResponse> videos;
    private List<TrainerResponse> trainers;
    private List<CategoryResponse> categories;
}
