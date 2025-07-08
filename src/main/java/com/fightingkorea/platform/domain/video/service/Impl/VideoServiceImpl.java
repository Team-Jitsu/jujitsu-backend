package com.fightingkorea.platform.domain.video.service.Impl;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.fightingkorea.platform.domain.video.exception.VideoNotExistsException;
import com.fightingkorea.platform.global.common.response.ResponseMapper;
import com.fightingkorea.platform.domain.video.dto.VideoRegisterRequest;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.VideoUpdateRequest;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.exception.VideoConflictException;
import com.fightingkorea.platform.domain.video.repository.VideoRepository;
import com.fightingkorea.platform.domain.video.service.VideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final TrainerRepository trainerRepository;


    @Override
    public VideoResponse registerVideo(VideoRegisterRequest req) {
        log.info("강의 등록 시도: title={}, trainerId={}", req.getTitle(), req.getTrainerId());

        Boolean isExist = videoRepository.existsByTitle(req.getTitle());

        if(isExist){
            throw new VideoConflictException();
        }

        Trainer trainer = trainerRepository.getReferenceById(req.getTrainerId());
        Video video = Video.createVideo(req, trainer);
        videoRepository.save(video);

        log.info("강의 등록 성공: title={}, trainerId={}", video.getTitle(), video.getTrainer());

        return ResponseMapper.toVideoResponse(video);
    }

    @Override
    public VideoResponse updateVideo(Long videoId, Long trainerId, VideoUpdateRequest req) {
        log.info("강의 업데이트 시도: title={}, trainerId={}", req.getTitle(), trainerId);

        if(Boolean.FALSE.equals(videoRepository.existsVideoByVideoId(videoId))){
            throw new VideoNotExistsException();
        }

        Video video = videoRepository.findVideoByVideoId(videoId);

        video.updateVideo(req.getTitle(), req.getDescription(), req.getPrice());

        log.info("강의 업데이트 성공: title={}, trainerId={}",video.getTitle(), video.getTrainer());

        return ResponseMapper.toVideoResponse(video);
    }

    @Override
    public void deleteVideo(Long videoId) {

        if(Boolean.FALSE.equals(videoRepository.existsVideoByVideoId(videoId))){
            throw new VideoNotExistsException();
        }

        Video video = videoRepository.findVideoByVideoId(videoId);

        log.info("강의 삭제 시도: title={}, trainerId={}", video.getTitle(), video.getTrainer().getTrainerId());

        videoRepository.delete(video);

        log.info("강의 삭제 성공");

    }
}
