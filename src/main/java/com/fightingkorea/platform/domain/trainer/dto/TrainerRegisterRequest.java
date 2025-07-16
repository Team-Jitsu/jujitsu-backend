package com.fightingkorea.platform.domain.trainer.dto;

import com.fightingkorea.platform.domain.user.dto.UserRegisterRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class TrainerRegisterRequest {

    private UserRegisterRequest userRegisterRequest;

    private String accountOwnerName;

    private String accountNumber;

    private String bio;

    private Boolean automaticSettlement;

    private List<Long> specialtyIds;
}
