package com.fightingkorea.platform.domain.trainer.dto;

import com.fightingkorea.platform.domain.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    private Long trainerId;

    private String accountOwnerName;

    private String accountNumber;

    private String bio;

    private Boolean automaticSettlement;

    private Integer charge;

    private List<SpecialtyResponse> specialties;

    private UserResponse user;
}
