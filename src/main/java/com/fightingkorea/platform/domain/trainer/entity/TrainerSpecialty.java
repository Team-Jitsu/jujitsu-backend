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
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Id
    @Column(name = "trainer_id")
    private Long trainerId;

    @ManyToOne
    @JoinColumn(name = "specialty_id", insertable = false, updatable = false)
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", insertable = false, updatable = false)
    private Trainer trainer;

    public static TrainerSpecialty createTrainerSpecialty(Long specialtyId, Long trainerId) {
        return new TrainerSpecialty(specialtyId, trainerId, null, null);
    }
}
