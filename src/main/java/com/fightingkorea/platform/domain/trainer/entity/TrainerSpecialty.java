package com.fightingkorea.platform.domain.trainer.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainer_specialty")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(TrainerSpecialtyId.class)
public class TrainerSpecialty {
    @Id
    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
