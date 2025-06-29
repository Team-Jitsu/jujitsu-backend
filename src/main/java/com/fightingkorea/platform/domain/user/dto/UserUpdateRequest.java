package com.fightingkorea.platform.domain.user.dto;

import com.fightingkorea.platform.domain.user.entity.type.Role;
import com.fightingkorea.platform.domain.user.entity.type.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  유저 정보를 수정하는 dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserUpdateRequest {


    /**
     * 이메일
     */
    private String email;

    /**
     * 회원 권한
     */
    private Role role;

    /**
     * 나이
     */
    private Integer age;

    /**
     * 성별
     */
    private Sex sex;

    /**
     * 닉네임
     */
    private String nickname;

    /**
     * 거주 지역
     */
    private String region;

    /**
     * 활성 상태 여부
     */
    private String isActive;

    /**
     * 휴대폰 번호
     */
    private String mobileNumber;

    /**
     * 수련중인 체육관
     */
    private String gymLocation;

}
