package com.fightingkorea.platform.domain.trainer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TrainerUpdateRequest {
    private Long trainerId;

    private String accountOwnerName;

    private String accountNumber;

    private String bio;

    private Boolean automaticSettlement;

    private List<Long> specialtyIds;
}
