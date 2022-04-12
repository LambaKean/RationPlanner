package com.lambakean.RationPlanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithTokensDto {

    private UserDto user;
    private String accessToken;
    private String refreshToken;
}
