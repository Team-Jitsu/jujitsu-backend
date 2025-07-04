package com.fightingkorea.platform.domain.trainer.entity;


import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class TrainerSpecialtyId implements Serializable {
    private Long specialtyId;
    private Long trainerId;
}
