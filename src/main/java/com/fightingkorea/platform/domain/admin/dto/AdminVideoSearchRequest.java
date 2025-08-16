package com.fightingkorea.platform.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminVideoSearchRequest {
    private int page;
    private int perPage;
    private String searchTerm;
    private Long categoryId;
    private String status;
    private String sortBy;
    private String sortOrder;
}
