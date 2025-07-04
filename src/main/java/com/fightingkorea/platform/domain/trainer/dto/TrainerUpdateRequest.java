package com.fightingkorea.platform.domain.trainer.dto;

import lombok.Getter;

@Getter
public class TrainerUpdateRequest {
    private Long trainerId;

    private String accountOwnerName;

    private String accountNumber;

    private String bio;

    private Boolean automaticSettlement;
}
