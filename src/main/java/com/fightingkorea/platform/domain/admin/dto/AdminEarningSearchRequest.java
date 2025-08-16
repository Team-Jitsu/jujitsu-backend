package com.fightingkorea.platform.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminEarningSearchRequest {
    private int page;
    private int perPage;
    private String searchTerm;
    private String status;
    private String sortBy;
    private String sortOrder;
}
