package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.video.dto.UserVideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.dto.VideoUploadRequest;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.exception.NotAuthorizedTrainerException;
import com.fightingkorea.platform.domain.video.exception.UserVideoListNotFoundException;
import com.fightingkorea.platform.domain.video.exception.VideoConflictException;
import com.fightingkorea.platform.domain.video.exception.VideoNotExistsException;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.domain.video.repository.VideoRepository;
import com.fightingkorea.platform.domain.video.service.CategoryService;
import com.fightingkorea.platform.domain.video.service.VideoService;
import com.fightingkorea.platform.global.UserUtil;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final TrainerRepository trainerRepository;
    private final UserVideoRepository userVideoRepository;
    private final CategoryService categoryService;

    @Override
    public VideoResponse uploadVideo(VideoUploadRequest req) {

        log.info("강의 등록 시도: title={}, trainerId={}", req.getTitle(), req.getTrainerId());

        Boolean isExist = videoRepository.existsByTitle(req.getTitle());

        if (isExist) {
            throw new VideoConflictException();
        }

        Trainer trainer = trainerRepository.getReferenceById(req.getTrainerId());
        Video video = Video.createVideo(req, trainer);

        videoRepository.save(video);

        categoryService.setVideoCategory(video.getVideoId(), req.getCategoryIds());

        log.info("강의 등록 성공: title={}, trainerId={}", video.getTitle(), video.getTrainer());

        return ResponseMapper.toVideoResponse(video);
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
    public Page<UserVideoResponse> getPurchasedVideoList(Long userId, Pageable pageable) {

        log.info("강의 구매 목록 조회 시도: userId={}", UserUtil.getUserId());

        Page<UserVideoResponse> userVideoResponses = userVideoRepository.getPurchasedVideoList(userId, pageable);

        if (userVideoResponses.isEmpty()) {
            log.info("userId{} 가 구매한 강의 없음", userId);
            throw new UserVideoListNotFoundException();
        }

        log.info("강의 구매 목록 조회 성공: {}건", userVideoResponses.getTotalElements());

        return userVideoResponses;

    }


}
