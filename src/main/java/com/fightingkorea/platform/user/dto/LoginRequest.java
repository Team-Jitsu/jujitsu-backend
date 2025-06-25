package com.fightingkorea.platform.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginRequest {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_pasword")
    private String userPassword;
}
