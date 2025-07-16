package com.fightingkorea.platform.global.common.response;

import com.fightingkorea.platform.domain.earning.dto.EarningResponse;
import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.trainer.dto.SpecialtyResponse;
import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.entity.Specialty;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.entity.TrainerSpecialty;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.video.dto.CategoryResponse;
import com.fightingkorea.platform.domain.video.dto.VideoResponse;
import com.fightingkorea.platform.domain.video.entity.Category;
import com.fightingkorea.platform.domain.video.entity.Video;

import java.util.List;

public class ResponseMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getNickname(),
                user.getRole().getLabel(), user.getCreatedAt());
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
                ResponseMapper.toUserResponse(trainer.getUser())
        );
    }

    public static VideoResponse toVideoResponse(Video video) {
        return new VideoResponse(
                video.getVideoId(),
                video.getTrainer().getTrainerId(),
                video.getTitle(),
                video.getUploadTime()
        );
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
                earning.isSettled(),
                earning.isRequestSettlement()
        );

    }
}
