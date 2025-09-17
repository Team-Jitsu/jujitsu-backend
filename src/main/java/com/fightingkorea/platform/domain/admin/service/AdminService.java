package com.fightingkorea.platform.domain.admin.service;

import com.fightingkorea.platform.domain.admin.dto.*;
import com.fightingkorea.platform.global.common.response.PaginatedResponse;

import java.util.Map;

public interface AdminService {
    PaginatedResponse<AdminVideoResponse> getAdminVideos(AdminVideoSearchRequest request);
    PaginatedResponse<AdminEarningResponse> getAdminEarnings(AdminEarningSearchRequest request);
    void updateAdminVideo(Long videoId, Map<String, Object> updateData);
    void deleteAdminVideo(Long videoId);
}
