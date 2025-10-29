package com.fightingkorea.platform.global.common.response;

import com.fightingkorea.platform.domain.earning.dto.EarningBufferResponse;
import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.trainer.dto.SpecialtyResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.entity.Specialty;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialty;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.dto.TrainerSummaryResponse;
import com.fightingkorea.platform.domain.video.entity.Category;
import com.fightingkorea.platform.domain.video.entity.Video;

import java.util.List;

public class ResponseMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getNickname(),
                user.getRole(), user.getCreatedAt());
    }

    public static SpecialtyResponse toSpecialtyResponse(Specialty specialty) {
        return new SpecialtyResponse(specialty.getSpecialtyId(), specialty.getSpecialtyName());
    }

    public static TrainerResponse toTrainerResponse(Trainer trainer, List<TrainerSpecialty> trainerSpecialtyList) {
        return new TrainerResponse(
                trainer.getTrainerId(),
                trainer.getAccountOwnerName(),
                trainer.getAccountNumber(),
                trainer.getBio(),
                trainer.getAutomaticSettlement(),
                trainer.getCharge(),
                trainerSpecialtyList.stream()
                        .map(ts -> toSpecialtyResponse(ts.getSpecialty()))
                        .toList(),
                ResponseMapper.toUserResponse(trainer.getUser()),
                trainer.getUser().getEmail()
        );
    }

    public static TrainerSummaryResponse toTrainerSummaryResponse(Trainer trainer) {
        return TrainerSummaryResponse.builder()
                .trainerId(trainer.getTrainerId())
                .nickname(trainer.getUser().getNickname())
                .bio(trainer.getBio())
                .build();
    }

    public static VideoResponse toVideoResponse(Video video) {
        return VideoResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .description(video.getDescription())
                .price(video.getPrice())
                .s3Key(video.getS3Key())
                .trainer(toTrainerSummaryResponse(video.getTrainer()))
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }

    public static EarningResponse toEarningResponse(Earning earning) {
        return new EarningResponse(
                earning.getEarningId(),
                earning.getTrainer().getTrainerId(),
                earning.getTotalAmount(),
                earning.getIsSettled(),
                earning.getRequestSettlement()
        );

    }

    public static EarningBufferResponse toEarningBufferResponse(EarningBuffer earningBuffer){
        return new EarningBufferResponse(
                earningBuffer.getBufferId(),
                earningBuffer.getTrainer().getTrainerId(),
                earningBuffer.getUserVideo().getUserVideoId(),
                earningBuffer.getEarning().getEarningId(),
                earningBuffer.getAmount(),
                earningBuffer.getCreatedAt()
        );
    }
}
