package com.fightingkorea.platform.domain.trainer.dto;

import com.fightingkorea.platform.domain.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerRegisterResponse {
    private Long trainerId;

    private Boolean automaticSettlement;

    private List<Long> specialtyIds;

    private UserResponse user;
}
