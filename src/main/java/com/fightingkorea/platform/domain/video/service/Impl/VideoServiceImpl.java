package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.earning.repository.EarningBufferRepository;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.video.dto.*;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.entity.VideoCategory;
import com.fightingkorea.platform.domain.video.exception.*;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.domain.video.repository.VideoRepository;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import com.fightingkorea.platform.domain.video.service.S3VideoStorageService;
import com.fightingkorea.platform.domain.video.service.VideoService;
import com.fightingkorea.platform.global.UserUtil;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final TrainerRepository trainerRepository;
    private final UserVideoRepository userVideoRepository;
    private final CategoryService categoryService;
    private final EarningBufferRepository earningBufferRepository;
    private final S3VideoStorageService s3;

    

    @Override
    @Transactional(readOnly = true)
    public Page<VideoResponse> getVideos(VideoSearchRequest request, Pageable pageable) {
        Specification<Video> spec = Specification.where(null);

        if (request.getTrainerId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("trainer").get("trainerId"), request.getTrainerId()));
        }

        if (request.getSearch() != null && !request.getSearch().isBlank()) {
            String like = "%" + request.getSearch() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(root.get("title"), like),
                    cb.like(root.get("description"), like)
            ));
        }

        if (request.getMinPrice() != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
        }

        if (request.getMaxPrice() != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
        }

        if (request.getCategoryId() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Video, VideoCategory> join = root.join("videoCategories", JoinType.INNER);
                return cb.equal(join.get("categoryId"), request.getCategoryId());
            });
        }

        return videoRepository.findAll(spec, pageable).map(this::toDtoWithNoLink);
    }

    @Override
    public VideoResponse updateVideo(Long videoId, VideoUpdateRequest req) {
        Video video = videoRepository.findVideoByVideoId(videoId)
                .orElseThrow(VideoNotExistsException::new);

        Long currentTrainerId = UserUtil.getTrainerId();
        Long videoTrainerId = video.getTrainer().getTrainerId();

        // video는 업로더만 수정할 수 있게. 그러면 jwt에 있는 trainerId를 가지고 와서 video.trainerId를 비교.
        if (!currentTrainerId.equals(videoTrainerId)) {
            throw new NotAuthorizedTrainerException();
        }

        video.updateVideo(req.getTitle(), req.getDescription());

        categoryService.setVideoCategory(video.getVideoId(), req.getCategoryIds());

        log.info("강의 업데이트 성공: title={}, trainerId={}", video.getTitle(), currentTrainerId);

        return ResponseMapper.toVideoResponse(video);
    }

    @Override
    public void deleteVideo(Long videoId) {

        Video video = videoRepository.findVideoByVideoId(videoId)
                .orElseThrow(VideoNotExistsException::new);

        // 해당 업로더가 맞는지 확인
        Long currentTrainerId = UserUtil.getTrainerId();

        // 비디오 업로더와 현재 로그인한 사용자가 일치하는지 확인
        Boolean isEqual = currentTrainerId.equals(video.getTrainer().getTrainerId());
        if (!isEqual) {
            throw new NotAuthorizedTrainerException();
        }

        log.info("강의 삭제 시도: title={}, trainerId={}", video.getTitle(), currentTrainerId);

        videoRepository.delete(video);

        log.info("강의 삭제 성공");

    }

    // 비디오 목록, 유저의 비디오 소유 목록
    @Transactional(readOnly = true)
    @Override
    public Page<UserVideoResponse> getPurchasedVideoList(Long userId, PurchaseSearchRequest request, Pageable pageable) {

        log.info("강의 구매 목록 조회 시도: userId={}", UserUtil.getUserId());

        Page<UserVideoResponse> userVideoResponses = userVideoRepository.getPurchasedVideoList(userId, request, pageable);

        if (userVideoResponses.isEmpty()) {
            log.info("userId{} 가 구매한 강의 없음", userId);
            throw new UserVideoListNotFoundException();
        }

        log.info("강의 구매 목록 조회 성공: {}건", userVideoResponses.getTotalElements());

        return userVideoResponses;

    }

    // 구매 내역 삭제(환불)
    public void deletePurchasedContent(Long userVideoId) {

        Long currentUserId = UserUtil.getUserId();
        log.info("강의 구매 취소 시도: userVideoId={}, userId={}", userVideoId, UserUtil.getUserId());

        UserVideo userVideo = userVideoRepository.findById(userVideoId)
                .orElseThrow(UserVideoNotFoundException::new);

        log.info("취소 하려는 구매 내역이 존재하지 않습니다.");

        Boolean isEqual = userVideo.getUser().getUserId().equals(currentUserId);

        if (!isEqual) {
            throw new NotAuthorizedUserException();
        }

        userVideoRepository.delete(userVideo);
        earningBufferRepository.deleteByUserVideo(userVideo);

        log.info("강의 구매 취소 완료");

    }

    @Override
    public VideoResponse uploadVideoMultipart(VideoUploadMultipartRequest req, MultipartFile file) {
        validateTitle(req.getTitle());
        Trainer trainer = trainerRepository.getReferenceById(req.getTrainerId());

        String key = s3.newVideoKey(file.getOriginalFilename());
        try {
            s3.putObject(key, file.getBytes(), file.getContentType());
        } catch (Exception e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }

        Video video = Video.createVideoFromMultipart(req, trainer, key);
        videoRepository.save(video);
        categoryService.setVideoCategory(video.getVideoId(), req.getCategoryIds());

        var read = s3.createPresignedGet(key, Duration.ofHours(1));
        return VideoResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .description(video.getDescription())
                .price(video.getPrice())
                .s3Key(key)
                .playUrl(read.url())
                .urlExpires(read.expiresAt())
                .trainer(ResponseMapper.toTrainerSummaryResponse(video.getTrainer()))
                .build();
    }

    

    @Override
    @Transactional(readOnly = true)
    public VideoResponse getPlayUrl(Long videoId) {
        Long currentUserId = UserUtil.getUserId();
        Video video = videoRepository.findById(videoId)
                .orElseThrow(VideoNotExistsException::new);

        boolean isPurchased = userVideoRepository.existsByUser_UserIdAndVideo_VideoId(currentUserId, videoId);
        boolean isUploader = video.getTrainer().getUser().getUserId().equals(currentUserId);

        if (!isPurchased && !isUploader) {
            throw new UnauthorizedAccessException("이 영상에 접근할 권한이 없습니다.");
        }

        var read = s3.createPresignedGet(video.getS3Key(), Duration.ofHours(1));
        return VideoResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .description(video.getDescription())
                .price(video.getPrice())
                .s3Key(video.getS3Key())
                .playUrl(read.url())
                .urlExpires(read.expiresAt())
                .trainer(ResponseMapper.toTrainerSummaryResponse(video.getTrainer()))
                .build();
    }

    private void validateTitle(String title) {
        if (videoRepository.existsByTitle(title)) throw new VideoConflictException();
    }

    private VideoResponse toDtoWithNoLink(Video video) {
        return VideoResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .description(video.getDescription())
                .price(video.getPrice())
                .s3Key(video.getS3Key())
                .trainer(ResponseMapper.toTrainerSummaryResponse(video.getTrainer()))
                .build();
    }

    @Override
    public VideoResponse getVideo(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(VideoNotExistsException::new);
        return toDtoWithNoLink(video);
    }


}
