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
public class TrainerResponse {
    private long trainerId;

    private String accountOwnerName;

    private String accountNumber;

    private String bio;

    private boolean automaticSettlement;

    private int charge;

    private List<SpecialtyResponse> specialties;

    private UserResponse user;

    public TrainerResponse(long trainerId, String accountOwnerName, String accountNumber,
                           String bio, boolean automaticSettlement, int charge, UserResponse user) {
        this.trainerId = trainerId;
        this.accountOwnerName = accountOwnerName;
        this.accountNumber = accountNumber;
        this.bio = bio;
        this.automaticSettlement = automaticSettlement;
        this.charge = charge;
        this.user = user;
    }
}
