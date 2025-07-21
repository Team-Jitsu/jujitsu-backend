package com.fightingkorea.platform.domain.trainer.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "trainer_specialty")
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

    public Long getSpecialtyId(){
        return specialtyId;
    }

    public Long getTrainerId(){
        return trainerId;
    }

    public Specialty getSpecialty(){
        return specialty;
    }

    public Trainer getTrainer(){
        return trainer;
    }
    
    //기본 생성자
    protected TrainerSpecialty(){}

    private TrainerSpecialty(Long specialtyId, Long trainerId, Specialty specialty, Trainer trainer){
        this.specialtyId = specialtyId;
        this.trainerId = trainerId;
        this.specialty = specialty;
        this.trainer = trainer;
    }


    public static TrainerSpecialty createTrainerSpecialty(Long specialtyId, Long trainerId) {
        return new TrainerSpecialty(specialtyId, trainerId, null, null);
    }
}
