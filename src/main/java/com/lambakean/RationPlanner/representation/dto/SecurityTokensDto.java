package com.lambakean.RationPlanner.representation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityTokensDto {

    private String accessToken;
    private String refreshToken;
}
