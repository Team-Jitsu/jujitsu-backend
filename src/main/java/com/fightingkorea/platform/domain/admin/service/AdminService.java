package com.fightingkorea.platform.domain.admin.service;

import com.fightingkorea.platform.domain.admin.dto.*;
import com.fightingkorea.platform.global.common.response.PaginatedResponse;

public interface AdminService {
    PaginatedResponse<AdminVideoResponse> getAdminVideos(AdminVideoSearchRequest request);
    PaginatedResponse<AdminEarningResponse> getAdminEarnings(AdminEarningSearchRequest request);
}
