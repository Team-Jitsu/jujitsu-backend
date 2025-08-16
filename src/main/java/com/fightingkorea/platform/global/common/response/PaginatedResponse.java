package com.fightingkorea.platform.global.common.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaginatedResponse<T> {
    private List<T> data;
    private PaginationInfo pagination;
}
