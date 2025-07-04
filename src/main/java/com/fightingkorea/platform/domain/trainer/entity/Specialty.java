package com.fightingkorea.platform.domain.trainer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "specialty")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    private String specialtyId;

    @Column(length = 100, nullable = false)
    private String specialtyName;

    public Specialty(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public static Specialty createSpecialty(String specialtyName) {
        return new Specialty(specialtyName);
    }
}

