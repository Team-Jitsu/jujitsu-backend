package com.fightingkorea.platform.user.domain;

import com.fightingkorea.platform.user.converter.RoleConverter;
import com.fightingkorea.platform.user.converter.SexConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 회원 고유 ID

    @Column(length = 100, nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(length = 100, unique = true, nullable = false)
    private String email; // 이메일 (로그인 ID)

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role; // 회원 권한 (수련생, 선수, 관리자 등)

    @Column(nullable = false)
    private Integer age; // 나이

    @Convert(converter = SexConverter.class)
    @Column(nullable = false)
    private Sex sex; // 성별

    @Column(length = 100)
    private String nickname; // 닉네임 (선택)

    @Column(length = 100, nullable = false)
    private String region; // 거주 지역

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    private Boolean isActive; // 활성 상태 여부

    @Column(nullable = false)
    private LocalDateTime createdAt; // 가입 일자

    @Column(length = 30, nullable = false)
    private String mobileNumber; // 휴대폰 번호

    @Column(length = 100)
    private String gymLocation; // 수련중인 체육관

    // 일반 회원 생성 메서드
    public static User createTrainee(String email, String password, Integer age, Sex sex, String region, String mobileNumber) {
        return User.builder()
                .email(email)
                .password(password)
                .role(Role.TRAINEE)
                .age(age)
                .sex(sex)
                .region(region)
                .mobileNumber(mobileNumber)
                .isActive(true)
                .build();
    }

    // 비밀번호 변경
    public void updatePassword(String password) {
        this.password = password;
    }

    // 권한 변경
    public void updateRole(Role role) {
        this.role = role;
    }

    // 회원 정보 업데이트
    public void updateUser(Integer age, Sex sex, String nickname, String region, String mobileNumber, String gymLocation) {
        this.age = age;
        this.sex = sex;
        this.nickname = nickname;
        this.region = region;
        this.mobileNumber = mobileNumber;
        this.gymLocation = gymLocation;
    }


    // 엔티티 저장 전 생성 시간 자동 설정
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
