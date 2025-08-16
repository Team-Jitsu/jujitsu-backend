package com.fightingkorea.platform.domain.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSearchRequest {
    private Long specialtyId;
    private String region;
    private String search;
    private String sortBy;
    private String sortOrder;
}
