package com.fightingkorea.platform.global.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginationInfo {
    private int currentPage;
    private int totalPages;
    private int perPage;
    private long totalCount;
    private boolean hasNext;
    private boolean hasPrevious;
}
