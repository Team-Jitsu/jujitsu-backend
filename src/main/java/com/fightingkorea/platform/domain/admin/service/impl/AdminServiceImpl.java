package com.fightingkorea.platform.domain.admin.service.impl;

import com.fightingkorea.platform.domain.admin.dto.*;
import com.fightingkorea.platform.domain.admin.service.AdminService;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.repository.EarningQueryRepository;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.domain.video.repository.VideoQueryRepository;
import com.fightingkorea.platform.domain.video.repository.VideoRepository;
import com.fightingkorea.platform.global.common.response.PaginatedResponse;
import com.fightingkorea.platform.global.common.response.PaginationInfo;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final VideoQueryRepository videoQueryRepository;
    private final UserVideoRepository userVideoRepository;
    private final EarningQueryRepository earningQueryRepository;
    private final VideoRepository videoRepository;

    @Override
    public PaginatedResponse<AdminVideoResponse> getAdminVideos(AdminVideoSearchRequest request) {
        int offset = (request.getPage() - 1) * request.getPerPage();
        JPAQuery<Video> query = videoQueryRepository.createAdminVideoQuery(request);
        long totalCount = query.fetchCount();
        List<Video> videos = query.offset(offset).limit(request.getPerPage()).fetch();
        List<AdminVideoResponse> responses = videos.stream()
                .map(video -> {
                    long viewCount = userVideoRepository.countByVideo_VideoId(video.getVideoId());
                    String categoryName = video.getVideoCategories().stream()
                            .map(vc -> vc.getCategory().getCategoryName())
                            .findFirst()
                            .orElse("미분류");
                    return AdminVideoResponse.builder()
                            .videoId(video.getVideoId())
                            .title(video.getTitle())
                            .description(video.getDescription())
                            .price(video.getPrice())
                            .s3Key(video.getS3Key())
                            .viewCount(viewCount)
                            .likeCount(video.getLikesCount())
                            .uploadTime(video.getUploadTime())
                            .trainerName(video.getTrainer().getUser().getNickname())
                            .categoryName(categoryName)
                            .status(viewCount > 0 ? "active" : "inactive")
                            .build();
                })
                .collect(Collectors.toList());
        int totalPages = (int) Math.ceil((double) totalCount / request.getPerPage());
        return PaginatedResponse.<AdminVideoResponse>builder()
                .data(responses)
                .pagination(PaginationInfo.builder()
                        .currentPage(request.getPage())
                        .totalPages(totalPages)
                        .perPage(request.getPerPage())
                        .totalCount(totalCount)
                        .hasNext(request.getPage() < totalPages)
                        .hasPrevious(request.getPage() > 1)
                        .build())
                .build();
    }

    @Override
    public PaginatedResponse<AdminEarningResponse> getAdminEarnings(AdminEarningSearchRequest request) {
        int offset = (request.getPage() - 1) * request.getPerPage();
        JPAQuery<Earning> query = earningQueryRepository.createAdminEarningQuery(request);
        long totalCount = query.fetchCount();
        List<Earning> earnings = query.offset(offset).limit(request.getPerPage()).fetch();
        List<AdminEarningResponse> responses = earnings.stream()
                .map(this::convertToAdminEarningResponse)
                .collect(Collectors.toList());
        int totalPages = (int) Math.ceil((double) totalCount / request.getPerPage());
        return PaginatedResponse.<AdminEarningResponse>builder()
                .data(responses)
                .pagination(PaginationInfo.builder()
                        .currentPage(request.getPage())
                        .totalPages(totalPages)
                        .perPage(request.getPerPage())
                        .totalCount(totalCount)
                        .hasNext(request.getPage() < totalPages)
                        .hasPrevious(request.getPage() > 1)
                        .build())
                .build();
    }

    private AdminEarningResponse convertToAdminEarningResponse(Earning earning) {
        return AdminEarningResponse.builder()
                .earningId(earning.getEarningId())
                .trainerId(earning.getTrainer().getTrainerId())
                .trainerName(earning.getTrainer().getUser().getNickname())
                .totalAmount(earning.getTotalAmount())
                .isSettled(Boolean.TRUE.equals(earning.getIsSettled()))
                .requestSettlement(Boolean.TRUE.equals(earning.getRequestSettlement()))
                .requestSettlementAt(earning.getRequestSettlementAt())
                .createdAt(earning.getRequestSettlementAt())
                .earningDetails(earning.getEarningBuffers().stream()
                        .map(buffer -> EarningDetailResponse.builder()
                                .bufferId(buffer.getBufferId())
                                .amount(buffer.getAmount() == null ? 0L : buffer.getAmount().longValue())
                                .createdAt(buffer.getCreatedAt())
                                .video(VideoSummaryResponse.builder()
                                        .videoId(buffer.getUserVideo().getVideo().getVideoId())
                                        .title(buffer.getUserVideo().getVideo().getTitle())
                                        .build())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void updateAdminVideo(Long videoId, Map<String, Object> updateData) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        if (updateData.containsKey("title")) {
            video.updateVideo(
                    updateData.get("title").toString(),
                    updateData.get("description") != null ? updateData.get("description").toString() : video.getDescription()
            );
        }

        if (updateData.containsKey("price")) {
            video.updatePrice((Integer) updateData.get("price"));
        }

        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void deleteAdminVideo(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        videoRepository.delete(video);
    }
}
