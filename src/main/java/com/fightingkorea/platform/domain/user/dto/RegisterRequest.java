package com.fightingkorea.platform.domain.user.dto;

import com.fightingkorea.platform.domain.user.entity.type.Sex;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+]{8,20}$",
            message = "비밀번호는 8~20자, 영문과 숫자를 포함해야 합니다."
    )
    private String password;

    @NotNull(message = "나이는 필수입니다.")
    @Min(value = 1, message = "나이는 1 이상이어야 합니다.")
    private Integer age;

    @NotNull(message = "성별은 필수입니다.")
    private Sex sex;

    @NotBlank(message = "지역은 필수입니다.")
    private String region;

    private String nickname;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String mobileNumber;

    private String gymLocation;
}
